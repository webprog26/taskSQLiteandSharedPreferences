package com.example.webprog26.datatask.activities;

import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.webprog26.datatask.R;
import com.example.webprog26.datatask.adapters.IslandsAdapter;
import com.example.webprog26.datatask.interfaces.OnIslandsUploadedListener;
import com.example.webprog26.datatask.interfaces.OnUserSuccessfullyRegisterListener;
import com.example.webprog26.datatask.interfaces.Reader;
import com.example.webprog26.datatask.models.Island;
import com.example.webprog26.datatask.models.User;
import com.example.webprog26.datatask.providers.DBProvider;
import com.example.webprog26.datatask.threads.AssetsReaderThread;
import com.example.webprog26.datatask.threads.IslandsAdditionalThread;
import com.example.webprog26.datatask.threads.UserIdWriterThread;
import com.example.webprog26.datatask.threads.UserRegisterThread;
import com.example.webprog26.datatask.utils.CheckedChangeHandler;
import com.example.webprog26.datatask.utils.FieldsTextChecker;
import com.example.webprog26.datatask.utils.SharedPreferencesUtils;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener,
        Reader, AdapterView.OnItemSelectedListener, OnIslandsUploadedListener, OnUserSuccessfullyRegisterListener {

    private static final String TAG = "RegisterActivity";
    public static final String COOK_ISLANDS_NAMES_FILE = "cook_islands.txt";

    public static final long NO_ISLAND_SELECTED = 0;

    private EditText mEtRegisterName, mEtRegisterPswd;
    private Spinner mIslandsSpinner;
    private CheckBox mChbSelectIsland;
    private DBProvider mDbProvider;
    private SharedPreferencesUtils mSharedPreferencesUtils;
    private ArrayList<Island> mIslands;
    private long mUserIslandId = NO_ISLAND_SELECTED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mSharedPreferencesUtils = new SharedPreferencesUtils(PreferenceManager.getDefaultSharedPreferences(this));

        mDbProvider = new DBProvider(this);
        if(!mDbProvider.isTableIslandsAlreadyFilled()){
            new AssetsReaderThread(this, COOK_ISLANDS_NAMES_FILE, this).start();
        } else {
            new IslandsAsyncLoader().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
        }

        mEtRegisterName  = (EditText) findViewById(R.id.etRegisterName);
        mEtRegisterPswd  = (EditText) findViewById(R.id.etRegisterPswd);

        mChbSelectIsland = (CheckBox) findViewById(R.id.chbEnableIslandsSpinner);

        mIslandsSpinner = (Spinner) findViewById(R.id.spinnerIslands);
        mIslandsSpinner.setEnabled(mChbSelectIsland.isChecked());
        mIslandsSpinner.setOnItemSelectedListener(this);

        mChbSelectIsland.setOnCheckedChangeListener(new CheckedChangeHandler(mIslandsSpinner));

        Button btnRegister = (Button) findViewById(R.id.btnRegRegister);
        Button btnCancel = (Button) findViewById(R.id.btnRegCancel);

        btnRegister.setOnClickListener(this);
        btnCancel.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnRegRegister:
                if(!FieldsTextChecker.fiedlsArentEmpty(mEtRegisterName, mEtRegisterPswd)) return;
                User user = new User();
                user.setUserName(mEtRegisterName.getText().toString());
                user.setUserPswd(mEtRegisterPswd.getText().toString());
                user.setUserIslandId(mUserIslandId);
                new UserRegisterThread(user, mDbProvider, this).start();
                finish();
                break;
            case R.id.btnRegCancel:
                finish();
                break;
        }
    }

    @Override
    public void onRead(ArrayList<Island> islands) {
        new IslandsAdditionalThread(mDbProvider, islands, this).start();
    }

    private class IslandsAsyncLoader extends AsyncTask<Void, Void, ArrayList<Island>>{
        @Override
        protected ArrayList<Island> doInBackground(Void... voids) {
            return mDbProvider.getCookIslandsFromDB();
        }

        @Override
        protected void onPostExecute(ArrayList<Island> islands) {
            super.onPostExecute(islands);
            if(islands != null){
                mIslands = islands;
                for(Island island: mIslands){
                    Log.i(TAG, island.getIslandName());
                }
                IslandsAdapter mIslandsAdapter = new IslandsAdapter(RegisterActivity.this, mIslands);
                mIslandsSpinner.setAdapter(mIslandsAdapter);
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.i(TAG, "Selected an island " + mIslands.get(i).getIslandName() + ", with id " + mIslands.get(i).getIslandId());
        if(mChbSelectIsland.isChecked()){
            mUserIslandId = mIslands.get(i).getIslandId();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //
    }

    @Override
    public void onIslandsUploaded() {
        new IslandsAsyncLoader().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }

    @Override
    public void onUserSuccessfullyRegister(long userId) {
        new UserIdWriterThread(userId, mSharedPreferencesUtils).start();
    }
}
