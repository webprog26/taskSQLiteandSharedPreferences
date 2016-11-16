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
import com.example.webprog26.datatask.models.User;
import com.example.webprog26.datatask.providers.DBProvider;
import com.example.webprog26.datatask.threads.LogInWriterThread;
import com.example.webprog26.datatask.threads.UserIdWriterThread;
import com.example.webprog26.datatask.utils.SharedPreferencesUtils;

public class LoggedInActivity extends AppCompatActivity {

    private SharedPreferencesUtils mSharedPreferencesUtils;
    private TextView mTvUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        mSharedPreferencesUtils = new SharedPreferencesUtils(PreferenceManager.getDefaultSharedPreferences(this));
        mTvUserInfo = (TextView) findViewById(R.id.tvUserInfo);

        new UserInfoAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


        Button btnLogOut = (Button) findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LogInWriterThread(mSharedPreferencesUtils, false).start();
                new UserIdWriterThread(SharedPreferencesUtils.USER_SEARCH_ERROR, mSharedPreferencesUtils).start();
                startActivity(new Intent(LoggedInActivity.this, MainActivity.class));
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
            long userId = mSharedPreferencesUtils.readUserId();

            if(userId != SharedPreferencesUtils.USER_SEARCH_ERROR){
                user = mDbProvider.getUserById(userId);
            }
            return user;
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            if(user == null) return;
            String userWelcome = LoggedInActivity.this.getResources().getString(R.string.user_welcome, user.getUserName());

            if(user.getUserIslandId() != SharedPreferencesUtils.USER_SEARCH_ERROR){
                userWelcome = userWelcome + " " + LoggedInActivity.this.getResources().getString(R.string.island_id, user.getUserIslandId());
            } else userWelcome = userWelcome + " " + LoggedInActivity.this.getResources().getString(R.string.no_island);

            mTvUserInfo.setText(userWelcome);
        }
    }
}
