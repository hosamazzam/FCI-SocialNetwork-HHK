package com.FCI.HHK.SocialNetwork;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.FCI.HHK.SocialNetwork.R;

public class MainActivity extends Activity {


    Button login, signUp;
    boolean flage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (Button) findViewById(R.id.login);
        signUp = (Button) findViewById(R.id.signUp);

        flage = isNetworkAvailable();

        login.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                flage = isNetworkAvailable();
                // TODO Auto-generated method stub
                if (flage == false) {
                    Toast.makeText(getApplication(), "No network connection", Toast.LENGTH_LONG).show();
                } else {
                    Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(loginIntent);
                }
            }
        });
        signUp.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                flage = isNetworkAvailable();
                // TODO Auto-generated method stub
                if (flage == false) {
                    Toast.makeText(getApplication(), "No network connection", Toast.LENGTH_LONG).show();
                } else {
                    Intent registerationIntent = new Intent(getApplicationContext(), activity_timeline.class);
                    startActivity(registerationIntent);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(getApplication(), "your settig", Toast.LENGTH_LONG).show();
                setContentView(R.layout.activity_home);
                return true;
            case R.id.action_help:
                Toast.makeText(getApplication(), "your help", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_about:
                setContentView(R.layout.activity_about);
                return true;
            case R.id.action_exit:
                Toast.makeText(getApplication(), "your exit", Toast.LENGTH_LONG).show();
                finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }


}
