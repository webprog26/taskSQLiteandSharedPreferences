package com.example.webprog26.datatask.threads;

import android.util.Log;

import com.example.webprog26.datatask.utils.SharedPreferencesUtils;
import com.example.webprog26.datatask.interfaces.InterruptChecker;

/**
 * Created by webprog26 on 16.11.2016.
 */

public class LogInWriterThread extends Thread implements InterruptChecker {

    private static final String TAG = "LogInWriterThread";

    private SharedPreferencesUtils mSharedPreferencesUtils;
    private boolean mIsLoggedIn;

    public LogInWriterThread(SharedPreferencesUtils mSharedPreferencesUtils, boolean isLoggedIn) {
        this.mSharedPreferencesUtils = mSharedPreferencesUtils;
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
        mSharedPreferencesUtils.writeLoginState(isLoggedIn);
    }

    @Override
    public boolean isThreadInterrupted() {
        return isInterrupted();
    }
}
