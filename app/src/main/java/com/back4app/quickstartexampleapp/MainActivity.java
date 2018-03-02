package com.back4app.quickstartexampleapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.ParseInstallation;
import com.parse.SaveCallback;

public class MainActivity extends AppCompatActivity {


    public void redirectActivity() {

        if (ParseUser.getCurrentUser().getString("riderOrDriver").equals("rider")) {


            Intent intent = new Intent(getApplicationContext(), RiderActivity.class);
            startActivity(intent);
        } else {


            Intent intent = new Intent(getApplicationContext(), ViewRequestActivity.class);
            startActivity(intent);
        }

    }


    public void getStarted(View view) {

        Switch userSwitchType = (Switch) findViewById(R.id.userSwhitchType);

        Log.i("Switch value", String.valueOf(userSwitchType.isChecked()));


        String userType = "rider";

        if (userSwitchType.isChecked()) {

            userType = "driver";
        }

        ParseUser.getCurrentUser().put("riderOrDriver", userType);

        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                redirectActivity();
            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportActionBar().hide();


        if (ParseUser.getCurrentUser() == null) {

            ParseAnonymousUtils.logIn(new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {

                    if ( e == null) {

                        Log.i("Info", "Annonymous user is login successful");
                    } else {

                        Log.i("Info", "Login failed");
                    }
                }
            });



        } else {

            if (ParseUser.getCurrentUser().get("riderOrDriver") != null)  {

                Log.i("Info", "Redirecting as " + ParseUser.getCurrentUser().get("riderOrDriver"));

                redirectActivity();
            }


        }

        // Save the current Installation to Back4App
        ParseInstallation.getCurrentInstallation().saveInBackground();

    }

}
