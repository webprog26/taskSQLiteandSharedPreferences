package com.example.webprog26.datatask.utils;

import android.widget.EditText;

/**
 * Created by webprog26 on 15.11.2016.
 */

public class FieldsTextChecker {

    public static boolean fiedlsArentEmpty(EditText name, EditText pswd){
        return name.getText().toString().length() > 0 && pswd.getText().toString().length() > 0;
    }
}
