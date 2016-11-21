package com.example.webprog26.datatask.threads;

import android.util.Log;

import com.example.webprog26.datatask.managers.SharedPreferencesManager;
import com.example.webprog26.datatask.interfaces.InterruptChecker;

/**
 * Created by webprog26 on 16.11.2016.
 */

public class LogInWriterThread extends Thread implements InterruptChecker {

    private static final String TAG = "LogInWriterThread";

    private SharedPreferencesManager mSharedPreferencesManager;
    private boolean mIsLoggedIn;

    public LogInWriterThread(SharedPreferencesManager mSharedPreferencesManager, boolean isLoggedIn) {
        this.mSharedPreferencesManager = mSharedPreferencesManager;
        this.mIsLoggedIn = isLoggedIn;
    }

    @Override
    public void run() {
        super.run();
        writeUserLoginState(mIsLoggedIn);
        if(!isThreadInterrupted()){
            interrupt();
        }
    }

    private synchronized void writeUserLoginState(boolean isLoggedIn){
        Log.i(TAG, "Writing login state to " + String.valueOf(isLoggedIn));
        mSharedPreferencesManager.writeLoginState(isLoggedIn);
    }

    @Override
    public boolean isThreadInterrupted() {
        return isInterrupted();
    }
}
