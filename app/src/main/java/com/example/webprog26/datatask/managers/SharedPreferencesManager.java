package com.example.webprog26.datatask.managers;

import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by webprog26 on 16.11.2016.
 */

public class SharedPreferencesManager {

    private SharedPreferences mSharedPreferences;

    private static final String TAG = "SharedPreferencesMan";

    private static final String USER_LOGIN_STATE = "user_login_state";
    private static final String USER_ID = "user_id";
    public static final int USER_SEARCH_ERROR = 0;

    public SharedPreferencesManager(SharedPreferences sharedPreferences) {
        this.mSharedPreferences = sharedPreferences;
    }

    /**
     * Writes user login state to {@link SharedPreferences}
     * @param isLoggedIn boolean
     */
    public void writeLoginState(boolean isLoggedIn){
        Log.i(TAG, "writing in login state" + Thread.currentThread().getName());
        mSharedPreferences.edit().putBoolean(USER_LOGIN_STATE, isLoggedIn).apply();
    }

    /**
     * Reads user login state from {@link SharedPreferences}
     * @return boolean
     */
    public boolean readUserLoginState(){
        return mSharedPreferences.getBoolean(USER_LOGIN_STATE, false);
    }

    /**
     * Writes user id to {@link SharedPreferences}
      * @param userId long
     */
    public void writeUserId(long userId){
        mSharedPreferences.edit().putLong(USER_ID, userId).apply();
    }

    /**
     * Reads user id from {@link SharedPreferences}
     * Returns current user id, otherwise return 0
     * @return long
     */
    public long readUserId(){
        return mSharedPreferences.getLong(USER_ID, USER_SEARCH_ERROR);
    }
}
