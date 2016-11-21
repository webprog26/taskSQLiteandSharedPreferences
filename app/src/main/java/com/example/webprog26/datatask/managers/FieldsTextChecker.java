package com.example.webprog26.datatask.managers;

import android.widget.EditText;

/**
 * Created by webprog26 on 15.11.2016.
 */

public class FieldsTextChecker {

    /**
     * Chaecks that username & user password has any number of symbols, greater than 0
     * @param name {@link EditText}
     * @param pswd {@link EditText}
     * @return boolean
     */
    public static boolean fieldsArentEmpty(EditText name, EditText pswd){
        return name.getText().toString().length() > 0 && pswd.getText().toString().length() > 0;
    }
}
