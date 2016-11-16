package com.example.webprog26.datatask.threads;

import com.example.webprog26.datatask.interfaces.InterruptChecker;
import com.example.webprog26.datatask.utils.SharedPreferencesUtils;

/**
 * Created by webprog26 on 16.11.2016.
 */

public class UserIdWriterThread extends Thread implements InterruptChecker{

    private static final String TAG = "UserIdWriterThread";

    private long mUserId;
    private SharedPreferencesUtils mSharedPreferencesUtils;

    public UserIdWriterThread(long mUserId, SharedPreferencesUtils mSharedPreferencesUtils) {
        this.mUserId = mUserId;
        this.mSharedPreferencesUtils = mSharedPreferencesUtils;
    }

    @Override
    public void run() {
        super.run();
        writeUserIdToSharedPreferences(mUserId);
        if(!isThreadInterrupted()){
            interrupt();
        }
    }

    private synchronized void writeUserIdToSharedPreferences(long userId){
        mSharedPreferencesUtils.writeUserId(userId);
    }

    @Override
    public boolean isThreadInterrupted() {
        return isInterrupted();
    }
}
