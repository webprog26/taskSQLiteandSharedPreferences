package com.example.webprog26.datatask.managers;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Spinner;

/**
 * Created by webprog26 on 16.11.2016.
 */

public class CheckedChangeHandler implements CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "CheckedChangeHandler";

    private Spinner mSpinner;

    public CheckedChangeHandler(Spinner mSpinner) {
        this.mSpinner = mSpinner;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        mSpinner.setEnabled(b);
    }
}
