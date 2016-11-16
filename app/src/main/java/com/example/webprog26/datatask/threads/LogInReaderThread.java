package com.example.webprog26.datatask.threads;

import com.example.webprog26.datatask.utils.SharedPreferencesUtils;
import com.example.webprog26.datatask.interfaces.InterruptChecker;
import com.example.webprog26.datatask.interfaces.IsUserLoggedInListener;

/**
 * Created by webprog26 on 16.11.2016.
 */

public class LogInReaderThread extends Thread implements InterruptChecker {

    private SharedPreferencesUtils mSharedPreferencesUtils;
    private IsUserLoggedInListener mIsUserLoggedIn;

    public LogInReaderThread(SharedPreferencesUtils mSharedPreferencesUtils, IsUserLoggedInListener mIsUserLoggedIn) {
        this.mSharedPreferencesUtils = mSharedPreferencesUtils;
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

    private synchronized void setIsUserLoggedIn(){
        mIsUserLoggedIn.setIsLoggedIn(mSharedPreferencesUtils.readUserLoginState());
    }

    @Override
    public boolean isThreadInterrupted() {
        return isInterrupted();
    }
}
