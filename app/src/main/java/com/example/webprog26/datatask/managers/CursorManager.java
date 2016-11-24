package com.example.webprog26.datatask.managers;

import android.database.Cursor;

import com.example.webprog26.datatask.db.DBHelper;
import com.example.webprog26.datatask.models.User;

/**
 * Created by webprog26 on 24.11.2016.
 */

public class CursorManager {

    /**
     * Gets {@link User} from database via @NotNull {@link Cursor}
     * @param cursor {@link Cursor}
     * @return {@link User}
     */
    public static User getUserFromDatabase(Cursor cursor){
        if(cursor == null) return null;
        User user = new User();

        while (cursor.moveToNext()){
            user.setUserId(cursor.getLong(cursor.getColumnIndex(DBHelper.USER_ID)));
            user.setUserName((cursor.getString(cursor.getColumnIndex(DBHelper.USER_NAME))));
            user.setUserPswd(cursor.getString(cursor.getColumnIndex(DBHelper.USER_PSWD)));
            user.setUserIslandId(cursor.getLong(cursor.getColumnIndex(DBHelper.USERS_COOK_ISLAND_ID)));
        }

        return user;
    }
}
