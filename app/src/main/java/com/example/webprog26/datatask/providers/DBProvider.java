package com.example.webprog26.datatask.providers;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.example.webprog26.datatask.activities.RegisterActivity;
import com.example.webprog26.datatask.db.DBHelper;
import com.example.webprog26.datatask.models.Island;
import com.example.webprog26.datatask.models.User;

import java.util.ArrayList;

/**
 * Created by webprog26 on 15.11.2016.
 */

public class DBProvider {

    private DBHelper mDbHelper;

    private static final String TAG = "DBProvider";
    private static final int COOK_ISLANDS_NUMBER = 15;


    public DBProvider(Activity activity) {
        this.mDbHelper = new DBHelper(activity);
    }

    public long insertIslandToDb(Island island){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.COOK_ISLAND_NAME, island.getIslandName());
        return mDbHelper.getWritableDatabase().insert(DBHelper.TABLE_COOK_ISLANDS, null, contentValues);
    }

    public ArrayList<Island> getCookIslandsFromDB(){
        ArrayList<Island> islands = new ArrayList<>();

        Cursor cursor = mDbHelper.getReadableDatabase().query(DBHelper.TABLE_COOK_ISLANDS, null, null, null, null, null, DBHelper.COOK_ISLAND_ID);

        while (cursor.moveToNext()){
            Island island = new Island();
                   island.setIslandId(cursor.getLong(cursor.getColumnIndex(DBHelper.COOK_ISLAND_ID)));
                   island.setIslandName(cursor.getString(cursor.getColumnIndex(DBHelper.COOK_ISLAND_NAME)));
            islands.add(island);
        }
        cursor.close();
        return islands;
    }

    public ArrayList<User> getUsersFromDB(){
        ArrayList<User> users = new ArrayList<>();

        Cursor cursor = mDbHelper.getReadableDatabase().query(DBHelper.TABLE_USERS, null, null, null, null, null, DBHelper.USER_ID);

        while (cursor.moveToNext()){
            User user = new User();
            user.setUserId(cursor.getLong(cursor.getColumnIndex(DBHelper.USER_ID)));
            user.setUserName(cursor.getString(cursor.getColumnIndex(DBHelper.USER_NAME)));
            user.setUserPswd(cursor.getString(cursor.getColumnIndex(DBHelper.USER_PSWD)));
            user.setUserIslandId(cursor.getLong(cursor.getColumnIndex(DBHelper.USERS_COOK_ISLAND_ID)));
            users.add(user);
        }
        cursor.close();
        return users;
    }

    public User getUserById(long userId){
        User user = new User();

        String selection = DBHelper.USER_ID + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(userId)};
        Cursor cursor = mDbHelper.getReadableDatabase().query(DBHelper.TABLE_USERS, null, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()){
            user.setUserId(userId);
            user.setUserName(cursor.getString(cursor.getColumnIndex(DBHelper.USER_NAME)));
            user.setUserIslandId(cursor.getLong(cursor.getColumnIndex(DBHelper.USERS_COOK_ISLAND_ID)));
        }
        cursor.close();
        return user;
    }

    public User getUserByName(String userName){
        User user = new User();

        String selection = DBHelper.USER_NAME + " = ?";
        String[] selectionArgs = new String[]{userName};
        Cursor cursor = mDbHelper.getReadableDatabase().query(DBHelper.TABLE_USERS, null, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()){
            user.setUserId(cursor.getLong(cursor.getColumnIndex(DBHelper.USER_ID)));
            user.setUserName(userName);
            user.setUserIslandId(cursor.getLong(cursor.getColumnIndex(DBHelper.USERS_COOK_ISLAND_ID)));
        }
        cursor.close();
        return user;
    }

    public long registerUserWithIsland(User user){
        Log.i(TAG, "writing registering user with island to DB " + Thread.currentThread().getName());
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.USER_NAME, user.getUserName());
        contentValues.put(DBHelper.USER_PSWD, user.getUserPswd());
        contentValues.put(DBHelper.USERS_COOK_ISLAND_ID, user.getUserIslandId());

        return mDbHelper.getWritableDatabase().insert(DBHelper.TABLE_USERS, null, contentValues);
    }

    public boolean isTableIslandsAlreadyFilled(){
        return getCookIslandsFromDB().size() >= COOK_ISLANDS_NUMBER;
    }
}