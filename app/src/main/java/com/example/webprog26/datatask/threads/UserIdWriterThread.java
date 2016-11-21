package com.example.webprog26.datatask.threads;

import com.example.webprog26.datatask.interfaces.InterruptChecker;
import com.example.webprog26.datatask.managers.SharedPreferencesManager;

/**
 * Created by webprog26 on 16.11.2016.
 */

public class UserIdWriterThread extends Thread implements InterruptChecker{

    private static final String TAG = "UserIdWriterThread";

    private long mUserId;
    private SharedPreferencesManager mSharedPreferencesManager;

    public UserIdWriterThread(long mUserId, SharedPreferencesManager mSharedPreferencesManager) {
        this.mUserId = mUserId;
        this.mSharedPreferencesManager = mSharedPreferencesManager;
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
        mSharedPreferencesManager.writeUserId(userId);
    }

    @Override
    public boolean isThreadInterrupted() {
        return isInterrupted();
    }
}
