package com.example.presignup;

import android.app.Application;

import com.parse.Parse;

public class Starter extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("")                                                                      //FILL THE CREDENTIALS
                .clientKey("")
                .server("")
                .build()
        );
    }
}
