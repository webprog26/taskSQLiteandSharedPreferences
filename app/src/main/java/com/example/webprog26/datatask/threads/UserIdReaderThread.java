package com.example.webprog26.datatask.threads;

import com.example.webprog26.datatask.interfaces.InterruptChecker;
import com.example.webprog26.datatask.interfaces.OnUserIdReadListener;
import com.example.webprog26.datatask.providers.DBProvider;

/**
 * Created by webprog26 on 16.11.2016.
 */

public class UserIdReaderThread extends Thread implements InterruptChecker {

    private DBProvider mDbProvider;
    private String mUserName;
    private OnUserIdReadListener mUserIdReadListener;

    public UserIdReaderThread(DBProvider mDbProvider, String mUserName, OnUserIdReadListener onUserIdReadListener) {
        this.mDbProvider = mDbProvider;
        this.mUserName = mUserName;
        this.mUserIdReadListener = onUserIdReadListener;
    }

    @Override
    public void run() {
        super.run();
        getUserByName(mUserName);
        if(!isThreadInterrupted()){
            interrupt();
        }
    }

    /**
     * Provides data base search for {@link com.example.webprog26.datatask.models.User} with given userName
     * @param userName {@link String}
     */
    private void getUserByName(String userName){
        mUserIdReadListener.onUserIdFound(mDbProvider.getUserByName(userName).getUserId());
    }

    @Override
    public boolean isThreadInterrupted() {
        return isInterrupted();
    }
}
