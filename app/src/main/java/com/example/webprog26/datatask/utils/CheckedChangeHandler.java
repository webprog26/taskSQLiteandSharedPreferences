package com.example.webprog26.datatask.utils;

import android.widget.CompoundButton;
import android.widget.Spinner;

/**
 * Created by webprog26 on 16.11.2016.
 */

public class CheckedChangeHandler implements CompoundButton.OnCheckedChangeListener {

    private Spinner mSpinner;

    public CheckedChangeHandler(Spinner mSpinner) {
        this.mSpinner = mSpinner;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        mSpinner.setEnabled(b);
    }
}
