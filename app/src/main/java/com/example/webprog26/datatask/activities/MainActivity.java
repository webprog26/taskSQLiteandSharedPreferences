package com.example.webprog26.datatask.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.webprog26.datatask.R;
import com.example.webprog26.datatask.interfaces.IsUserLoggedInListener;
import com.example.webprog26.datatask.interfaces.IsUserRegisteredListener;
import com.example.webprog26.datatask.interfaces.OnUserIdReadListener;
import com.example.webprog26.datatask.models.User;
import com.example.webprog26.datatask.providers.DBProvider;
import com.example.webprog26.datatask.threads.AssetsReaderThread;
import com.example.webprog26.datatask.threads.LogInReaderThread;
import com.example.webprog26.datatask.threads.LogInWriterThread;
import com.example.webprog26.datatask.threads.RegisteredCheckerThread;
import com.example.webprog26.datatask.threads.UserIdReaderThread;
import com.example.webprog26.datatask.threads.UserIdWriterThread;
import com.example.webprog26.datatask.managers.FieldsTextChecker;
import com.example.webprog26.datatask.managers.LoginChecker;
import com.example.webprog26.datatask.managers.SharedPreferencesManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        IsUserLoggedInListener, IsUserRegisteredListener, OnUserIdReadListener {

    private static final String TAG = "MainActivity_TAG";

    private static final String COOK_ISLANDS_NAMES_FILE = "cook_islands.txt";

    private EditText mEtLoginName, mEtLoginPswd;
    private SharedPreferencesManager mSharedPreferencesManager;
    private LoginChecker mLoginChecker;
    private DBProvider mDbProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mSharedPreferencesManager = new SharedPreferencesManager(mSharedPreferences);

        mDbProvider = new DBProvider(this);

//        if(!mDbProvider.isTableIslandsAlreadyFilled()){
//            new AssetsReaderThread(this, COOK_ISLANDS_NAMES_FILE, this).start();
//        }

        mLoginChecker = new LoginChecker(mDbProvider);

        //Checking user login state. Redirecting user to LoggedInActivity if already logged in
        new LogInReaderThread(mSharedPreferencesManager, this).start();

        mEtLoginName  = (EditText) findViewById(R.id.etLoginName);
        mEtLoginPswd  = (EditText) findViewById(R.id.etLoginPswd);

        Button btnLogin = (Button) findViewById(R.id.btnLogLogin);
        Button btnRegister = (Button) findViewById(R.id.btnLogRegister);

        btnLogin.setOnClickListener(this);

        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogLogin:
                //EditText fields shouldn't be empty
                if(!FieldsTextChecker.fiedlsArentEmpty(mEtLoginName, mEtLoginPswd)) return;
                //Forming new User with inputted data
                User user = new User();
                user.setUserName(mEtLoginName.getText().toString());
                user.setUserPswd(mEtLoginPswd.getText().toString());
                //Checking data base for user with inputted name adn password.
                //If found, redirecting him to LoggedInActivity
                new RegisteredCheckerThread(this, mLoginChecker, user).start();
                break;
            case R.id.btnLogRegister:
                //Starting RegisterActivity to register new User
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                break;
        }
    }

    @Override
    public void isUserLoggedIn(boolean isLoggedIn) {
       if(isLoggedIn){
           //User is already logged in. Redirecting him to LoggedInActivity immediately
           startActivity(new Intent(MainActivity.this, LoggedInActivity.class));
           finish();
       }
    }

    @Override
    public void isUserRegistered(boolean isRegistered) {
//            DBProvider dbProvider = new DBProvider(MainActivity.this);

            new LogInWriterThread(mSharedPreferencesManager, isRegistered).start();
            new UserIdReaderThread(mDbProvider, mEtLoginName.getText().toString(), this).start();
            startActivity(new Intent(MainActivity.this, LoggedInActivity.class));
            finish();
    }

    @Override
    public void onUserIdFound(long userId) {
        new UserIdWriterThread(userId, mSharedPreferencesManager).start();
    }
}
