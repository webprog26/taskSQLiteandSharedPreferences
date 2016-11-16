package com.example.webprog26.datatask.activities;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.webprog26.datatask.threads.LogInReaderThread;
import com.example.webprog26.datatask.threads.LogInWriterThread;
import com.example.webprog26.datatask.threads.RegisteredCheckerThread;
import com.example.webprog26.datatask.threads.UserIdReaderThread;
import com.example.webprog26.datatask.threads.UserIdWriterThread;
import com.example.webprog26.datatask.utils.FieldsTextChecker;
import com.example.webprog26.datatask.utils.LoginChecker;
import com.example.webprog26.datatask.utils.SharedPreferencesUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        IsUserLoggedInListener, IsUserRegisteredListener, OnUserIdReadListener {

    private static final String TAG = "MainActivity_TAG";
    private EditText mEtLoginName, mEtLoginPswd;
    private SharedPreferencesUtils mSharedPreferencesUtils;
    private LoginChecker mLoginChecker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mSharedPreferencesUtils = new SharedPreferencesUtils(mSharedPreferences);

        new LogInReaderThread(mSharedPreferencesUtils, this).start();

        DBProvider mDbProvider = new DBProvider(this);
        mLoginChecker = new LoginChecker(mDbProvider);

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
                if(!FieldsTextChecker.fiedlsArentEmpty(mEtLoginName, mEtLoginPswd)) return;
                User user = new User();
                user.setUserName(mEtLoginName.getText().toString());
                user.setUserPswd(mEtLoginPswd.getText().toString());
                new RegisteredCheckerThread(this, mLoginChecker, user).start();
                break;
            case R.id.btnLogRegister:
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                break;
        }
    }

    @Override
    public void setIsLoggedIn(boolean isLoggedIn) {
       if(isLoggedIn){
           startActivity(new Intent(MainActivity.this, LoggedInActivity.class));
           finish();
       }
    }

    @Override
    public void isUserRegistered(boolean isRegistered) {
            DBProvider dbProvider = new DBProvider(MainActivity.this);

            new LogInWriterThread(mSharedPreferencesUtils, isRegistered).start();
            new UserIdReaderThread(dbProvider, mEtLoginName.getText().toString(), this).start();
            startActivity(new Intent(MainActivity.this, LoggedInActivity.class));
            finish();
    }

    @Override
    public void onUserIdFound(long userId) {
        new UserIdWriterThread(userId, mSharedPreferencesUtils).start();
    }
}
