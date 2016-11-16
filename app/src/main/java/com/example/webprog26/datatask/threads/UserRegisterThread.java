package com.example.webprog26.datatask.threads;

import android.util.Log;

import com.example.webprog26.datatask.activities.RegisterActivity;
import com.example.webprog26.datatask.interfaces.InterruptChecker;
import com.example.webprog26.datatask.interfaces.OnUserSuccessfullyRegisterListener;
import com.example.webprog26.datatask.models.User;
import com.example.webprog26.datatask.providers.DBProvider;

/**
 * Created by webprog26 on 16.11.2016.
 */

public class UserRegisterThread extends Thread implements InterruptChecker {

    private static final String TAG = "UserRegisterThread";

    private User mUser;
    private DBProvider mDbProvider;
    private OnUserSuccessfullyRegisterListener mSuccessfullyRegisterListener;

    public UserRegisterThread(User mUser, DBProvider dbProvider, OnUserSuccessfullyRegisterListener successfullyRegisterListener) {
        this.mUser = mUser;
        this.mDbProvider = dbProvider;
        this.mSuccessfullyRegisterListener = successfullyRegisterListener;
    }

    @Override
    public void run() {
        super.run();
        addUserToDB(mUser);
        if(!isThreadInterrupted()){
            interrupt();
        }
    }

    private synchronized void addUserToDB(User user){
        mSuccessfullyRegisterListener.onUserSuccessfullyRegister(mDbProvider.registerUserWithIsland(user));
    }

    @Override
    public boolean isThreadInterrupted() {
        return isInterrupted();
    }
}
