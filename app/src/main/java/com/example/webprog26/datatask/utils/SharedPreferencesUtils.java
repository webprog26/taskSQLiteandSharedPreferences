package com.example.webprog26.datatask.utils;

import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by webprog26 on 16.11.2016.
 */

public class SharedPreferencesUtils {

    private SharedPreferences mSharedPreferences;

    private static final String TAG = "SharedPreferencesUtils";

    private static final String USER_LOGIN_STATE = "user_login_state";
    private static final String USER_ID = "user_id";
    public static final int USER_SEARCH_ERROR = 0;

    public SharedPreferencesUtils(SharedPreferences sharedPreferences) {
        this.mSharedPreferences = sharedPreferences;
    }

    public void writeLoginState(boolean isLoggedIn){
        Log.i(TAG, "writing in login state" + Thread.currentThread().getName());
        mSharedPreferences.edit().putBoolean(USER_LOGIN_STATE, isLoggedIn).apply();
    }

    public boolean readUserLoginState(){
        return mSharedPreferences.getBoolean(USER_LOGIN_STATE, false);
    }

    public void writeUserId(long userId){
        mSharedPreferences.edit().putLong(USER_ID, userId).apply();
    }

    public long readUserId(){
        return mSharedPreferences.getLong(USER_ID, USER_SEARCH_ERROR);
    }
}
