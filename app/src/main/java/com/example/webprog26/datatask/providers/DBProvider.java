package com.example.webprog26.datatask.providers;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.example.webprog26.datatask.activities.RegisterActivity;
import com.example.webprog26.datatask.db.DBHelper;
import com.example.webprog26.datatask.managers.CursorManager;
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

    /**
     * Inserts given {@link Island} to database
     * @param island {@link Island}
     * @return long
     */
    public long insertIslandToDb(Island island){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.COOK_ISLAND_NAME, island.getIslandName());
        return mDbHelper.getWritableDatabase().insert(DBHelper.TABLE_COOK_ISLANDS, null, contentValues);
    }

    /**
     * Returns all the islands, previously added to database
     * @return ArrayList<Island>
     */
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

    /**
     * Returns all the registered users
     * @return {@link ArrayList<{User}>}
     */
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

    /**
     * Returns single {@link User} found in database by given id
     * @param userId long
     * @return {@link User}
     */
    public User getUserById(long userId){
        User user;

        String selection = DBHelper.USER_ID + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(userId)};
        Cursor cursor = mDbHelper.getReadableDatabase().query(DBHelper.TABLE_USERS, null, selection, selectionArgs, null, null, null);
        user = CursorManager.getUserFromDatabase(cursor);
        cursor.close();
        return user;
    }

    /**
     * Returns single {@link User} found in database by given username
     * @param userName {@link String}
     * @return {@link User}
     */
    public User getUserByName(String userName){
        User user;

        String selection = DBHelper.USER_NAME + " = ?";
        String[] selectionArgs = new String[]{userName};
        Cursor cursor = mDbHelper.getReadableDatabase().query(DBHelper.TABLE_USERS, null, selection, selectionArgs, null, null, null);
        user = CursorManager.getUserFromDatabase(cursor);
        cursor.close();
        return user;
    }

    /**
     * Inserts to data base user with chosen {@link Island}
     * @param user {@link User}
     * @return long
     */
    public long registerUserWithIsland(User user){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.USER_NAME, user.getUserName());
        contentValues.put(DBHelper.USER_PSWD, user.getUserPswd());
        contentValues.put(DBHelper.USERS_COOK_ISLAND_ID, user.getUserIslandId());

        return mDbHelper.getWritableDatabase().insert(DBHelper.TABLE_USERS, null, contentValues);
    }

    /**
     * Checks is database already filled with islands, so it doesn't need to lopad them anymore
     * @return boolean
     */
    public boolean isTableIslandsAlreadyFilled(){
        return getCookIslandsFromDB().size() >= COOK_ISLANDS_NUMBER;
    }
}
