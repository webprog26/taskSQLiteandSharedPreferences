package com.example.webprog26.datatask.managers;

import android.util.Log;

import com.example.webprog26.datatask.models.User;
import com.example.webprog26.datatask.providers.DBProvider;

/**
 * Created by webprog26 on 16.11.2016.
 */

public class LoginChecker {

    private DBProvider mDbProvider;
    private static String TAG = "LoginChecker";

    public LoginChecker(DBProvider mDbProvider) {
        this.mDbProvider = mDbProvider;
    }

    /**
     * Checks is user data already exists in the database
     * @param userName {@link String}
     * @param userPswd {@link String}
     * @return boolean
     */
    public boolean isUserRegisteredAlready(String userName, String userPswd){
        for(User user: mDbProvider.getUsersFromDB()){
            if(user.getUserName().equals(userName) && userPswd.equals(user.getUserPswd())){
                Log.i(TAG, "Found user " + user.getUserName() + ", with password" + user.getUserPswd());
                return true;
            }
        }
        Log.i(TAG, "User not found" + userName + ", with password" + userPswd);
        return false;
    }
}
