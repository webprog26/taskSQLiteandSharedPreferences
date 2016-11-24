package com.example.webprog26.datatask.threads;

import com.example.webprog26.datatask.managers.SharedPreferencesManager;
import com.example.webprog26.datatask.interfaces.InterruptChecker;
import com.example.webprog26.datatask.interfaces.IsUserLoggedInListener;

/**
 * Created by webprog26 on 16.11.2016.
 */

public class LogInReaderThread extends Thread implements InterruptChecker {

    private SharedPreferencesManager mSharedPreferencesManager;
    private IsUserLoggedInListener mIsUserLoggedIn;

    public LogInReaderThread(SharedPreferencesManager mSharedPreferencesManager, IsUserLoggedInListener mIsUserLoggedIn) {
        this.mSharedPreferencesManager = mSharedPreferencesManager;
        this.mIsUserLoggedIn = mIsUserLoggedIn;
    }

    @Override
    public void run() {
        super.run();
        setIsUserLoggedIn();
        if(!isThreadInterrupted()){
            interrupt();
        }

    }

    /**
     * Provides reading user login state from {@link android.content.SharedPreferences}
     */
    private synchronized void setIsUserLoggedIn(){
        mIsUserLoggedIn.isUserLoggedIn(mSharedPreferencesManager.isUserLoggedIn());
    }

    @Override
    public boolean isThreadInterrupted() {
        return isInterrupted();
    }
}
