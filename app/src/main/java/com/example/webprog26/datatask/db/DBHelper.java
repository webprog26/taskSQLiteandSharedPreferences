package com.example.webprog26.datatask.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by webprog26 on 15.11.2016.
 */

public class DBHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "data_task_db";
    private static final int DB_BERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_BERSION);
    }

    public static final String TABLE_COOK_ISLANDS = "table_cook_islands";
    public static final String COOK_ISLAND_ID = "_id";
    public static final String COOK_ISLAND_NAME = "cook_island_name";

    public static final String TABLE_USERS = "table_users";
    public static final String USER_ID = "_id";
    public static final String USERS_COOK_ISLAND_ID = "users_cook_island_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_PSWD = "user_pswd";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_COOK_ISLANDS + "("
        + COOK_ISLAND_ID + " integer primary key autoincrement, "
        + COOK_ISLAND_NAME + " varchar(100))");

        sqLiteDatabase.execSQL("create table " + TABLE_USERS + "("
                + USER_ID + " integer primary key autoincrement, "
                + USERS_COOK_ISLAND_ID + " integer, "
                + USER_NAME + " varchar(100), "
                + USER_PSWD + " varchar(100))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //not needed yet
    }
}
