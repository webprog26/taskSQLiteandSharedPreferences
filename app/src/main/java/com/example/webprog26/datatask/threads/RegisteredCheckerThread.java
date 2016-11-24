package com.example.webprog26.datatask.threads;

import android.util.Log;

import com.example.webprog26.datatask.managers.LoginChecker;
import com.example.webprog26.datatask.interfaces.InterruptChecker;
import com.example.webprog26.datatask.interfaces.IsUserRegisteredListener;
import com.example.webprog26.datatask.models.User;

/**
 * Created by webprog26 on 16.11.2016.
 */

public class RegisteredCheckerThread extends Thread implements InterruptChecker {

    private static final String TAG = "RegisteredCheckerThread";

    private IsUserRegisteredListener mIsUserRegistered;
    private LoginChecker mLoginChecker;
    private User mUser;

    public RegisteredCheckerThread(IsUserRegisteredListener mIsUserRegistered, LoginChecker mLoginChecker, User user) {
        this.mIsUserRegistered = mIsUserRegistered;
        this.mLoginChecker = mLoginChecker;
        this.mUser = user;
        this.setName(TAG);
    }

    @Override
    public void run() {
        super.run();
        checkUserForRegistration(mUser);
        if(!isThreadInterrupted()){
            interrupt();
        }
        Log.i(TAG, Thread.currentThread().getName() + " isInterrupted() " + isInterrupted());
    }

    /**
     * Provides checking is user already registered
     * @param user {@link User}
     */
    private synchronized void checkUserForRegistration(User user){
        Log.i(TAG, "checkUserForRegistration " + user.getUserName() + ", with password " + user.getUserPswd());
        boolean isUserRegistered = mLoginChecker.isUserRegisteredAlready(user.getUserName(), user.getUserPswd());
        Log.i(TAG, "isUserRegistered: " + isUserRegistered);
        mIsUserRegistered.isUserRegistered(isUserRegistered);
    }

        @Override
        public boolean isThreadInterrupted() {
            return isInterrupted();
        }
}


