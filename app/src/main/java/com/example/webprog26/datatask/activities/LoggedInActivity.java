package com.example.webprog26.datatask.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.webprog26.datatask.R;
import com.example.webprog26.datatask.managers.SharedPreferencesManager;
import com.example.webprog26.datatask.models.User;
import com.example.webprog26.datatask.providers.DBProvider;

public class LoggedInActivity extends AppCompatActivity {

    private SharedPreferencesManager mSharedPreferencesManager;
    private TextView mTvUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        mSharedPreferencesManager = new SharedPreferencesManager(PreferenceManager.getDefaultSharedPreferences(this));
        mTvUserInfo = (TextView) findViewById(R.id.tvUserInfo);

        //Loading Islands List to Spinner, setting up SpinnerAdapter
        new UserInfoAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


        Button btnLogOut = (Button) findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSharedPreferencesManager.writeLoginState(false);//Since user is logging out, his login state is false. Writing it to SharedPreferences
                mSharedPreferencesManager.writeUserId(SharedPreferencesManager.USER_SEARCH_ERROR);//Since user is logging out, his id becomes 0
                startActivity(new Intent(LoggedInActivity.this, MainActivity.class));// returning to the MainActivity, so any user can login or register
                finish();
            }
        });
    }

    private class UserInfoAsyncTask extends AsyncTask<Void, Void, User>{

        private DBProvider mDbProvider;

        public UserInfoAsyncTask() {
            this.mDbProvider = new DBProvider(LoggedInActivity.this);
        }

        @Override
        protected User doInBackground(Void... voids) {
            User user = new User();
            long userId = mSharedPreferencesManager.readUserId();

            if(userId != SharedPreferencesManager.USER_SEARCH_ERROR){
                user = mDbProvider.getUserById(userId);
            }
            return user;
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            if(user == null) return;
            String userWelcome = LoggedInActivity.this.getResources().getString(R.string.user_welcome, user.getUserName());

            if(user.getUserIslandId() != SharedPreferencesManager.USER_SEARCH_ERROR){
                userWelcome = userWelcome + " " + LoggedInActivity.this.getResources().getString(R.string.island_id, user.getUserIslandId());
            } else userWelcome = userWelcome + " " + LoggedInActivity.this.getResources().getString(R.string.no_island);

            mTvUserInfo.setText(userWelcome);
        }
    }
}
