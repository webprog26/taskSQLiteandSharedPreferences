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
        User user = mDbProvider.getUserByName(userName);
        if(user.getUserName() == null || user.getUserPswd() == null){
            return false;
        }

        return user.getUserName().equals(userName) && user.getUserPswd().equals(userPswd);
    }
}
