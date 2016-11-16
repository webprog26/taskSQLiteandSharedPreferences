package com.example.webprog26.datatask.models;

/**
 * Created by webprog26 on 15.11.2016.
 */

public class User {

    private long mUserId;
    private long mUserIslandId;
    private String mUserName;
    private String mUserPswd;

    public long getUserId() {
        return mUserId;
    }

    public void setUserId(long userId) {
        this.mUserId = userId;
    }

    public long getUserIslandId() {
        return mUserIslandId;
    }

    public void setUserIslandId(long userIslandId) {
        this.mUserIslandId = userIslandId;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        this.mUserName = userName;
    }

    public String getUserPswd() {
        return mUserPswd;
    }

    public void setUserPswd(String userPswd) {
        this.mUserPswd = userPswd;
    }
}
