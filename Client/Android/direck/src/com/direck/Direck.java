package com.direck;

import android.app.Application; 

import com.direck.activities.Home;
import com.direck.sync.RegisterGCM;
import com.direck.utils.util;
import com.google.android.gcm.GCMRegistrar;
import com.parse.Parse; 
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.PushService;

public class Direck extends Application { 
	 RegisterGCM GCM;
    @Override public void onCreate() { 
        super.onCreate();

       /*
        String AccountID = util.getPrefAccountID(this);
        if (!(AccountID.equals("0"))){
	        GCM = new RegisterGCM();
			GCM.registerGCM(getApplicationContext(), AccountID);
        }
*/        
    }
   
    
} 