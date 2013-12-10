package com.direck;

import android.app.Application; 

import com.direck.activities.Home;
import com.parse.Parse; 
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.PushService;

public class Direck extends Application { 

    @Override public void onCreate() { 
        super.onCreate();

        Parse.initialize(this, "JMwnSUNDBaFf4OczIvJGG6ck7P8kursqm0EbEmVl", "nXS6iGVA18yR4xyfju53hF1FEQcYunXpv0dzFoZF"); // Your Application ID and Client Key are defined elsewhere
        
        PushService.setDefaultPushCallback(this, Home.class);
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
} 