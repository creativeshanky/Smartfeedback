package com.verizon.smartfeedback;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;


public class MasterActivity extends FragmentActivity {
  public static final String TAG = "SmartFeedback";
  
  public static final String EXTRA_MESSAGE = "message";
  public static final String PROPERTY_REG_ID = "registration_id";
  private static final String PROPERTY_APP_VERSION = "appVersion";
  private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
  
  
  String SENDER_ID = "60135901266";
  String regId;
  
  
  GoogleCloudMessaging gcm;
  AtomicInteger msgId = new AtomicInteger();
  SharedPreferences prefs;
  Context context;
  
		  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.master_activity);
        boolean paybill = false;
        Intent intent = getIntent();
        
        String payBill = PreferenceManager.getInstance(this).getStringValue("paybill");
        
    	if(payBill!=null && payBill.equalsIgnoreCase("true"))
    		getSupportFragmentManager().beginTransaction().add(android.R.id.content,new QuickPayFragment()).commit();
        else
        	getSupportFragmentManager().beginTransaction().add(android.R.id.content,new LikeFragment()).commit();
        	
        
        context = getApplicationContext();
        
        // Check device for Play Services APK.
        if (checkPlayServices()) {
            // If this check succeeds, proceed with normal processing.
            // Otherwise, prompt user to get valid Play Services APK.
        	gcm = GoogleCloudMessaging.getInstance(this);
            regId = getRegistrationId(context);
            Log.d(TAG,regId);
            if (regId == null || regId.equals("")) {
                registerInBackground();
            }
        }
        
        
        
       
    }
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();            	 
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
  // You need to do the Play Services APK check here too.
    @Override
    protected void onResume() {
        super.onResume();
        checkPlayServices();
    }

    
    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if ( registrationId == null || registrationId.equals("")) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }
    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }
    
    /* Registers the application with GCM servers asynchronously.
    * <p>
    * Stores the registration ID and app versionCode in the application's
    * shared preferences.
    */
   private void registerInBackground() {
       (new AsyncTask<String,String,String>() {
           @Override
           protected String doInBackground(String... params) {
               String msg = "";
               try {
                   if (gcm == null) {
                       gcm = GoogleCloudMessaging.getInstance(context);
                   }
                   regId = gcm.register(SENDER_ID);
                   msg = "Device registered, registration ID=" + regId;

                   // You should send the registration ID to your server over HTTP,
                   // so it can use GCM/HTTP or CCS to send messages to your app.
                   // The request to your server should be authenticated if your app
                   // is using accounts.
                   sendRegistrationIdToBackend(regId);

                   // For this demo: we don't need to send it because the device
                   // will send upstream messages to a server that echo back the
                   // message using the 'from' address in the message.

                   // Persist the regID - no need to register again.
                   storeRegistrationId(context, regId);
               } catch (IOException ex) {
                   msg = "Error :" + ex.getMessage();
                   // If there is an error, don't just keep trying to register.
                   // Require the user to click a button again, or perform
                   // exponential back-off.
               }
               return msg;
           }

           @Override
           protected void onPostExecute(String msg) {
               
           }
       }).execute(null, null, null);       
   }
    
   
   private void storeRegistrationId(Context context, String regId) {
	    final SharedPreferences prefs = getGCMPreferences(context);
	    int appVersion = getAppVersion(context);
	    Log.i(TAG, "Saving regId on app version " + appVersion);
	    SharedPreferences.Editor editor = prefs.edit();
	    editor.putString(PROPERTY_REG_ID, regId);
	    editor.putInt(PROPERTY_APP_VERSION, appVersion);
	    editor.commit();
	}
   private void sendRegistrationIdToBackend(String regId) {
	   Log.d(TAG, "Registered successfully" + regId);
	}
    /**
     * @return Application's {@code SharedPreferences}.
     */
    private SharedPreferences getGCMPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the regID in your app is up to you.
        return getSharedPreferences(MasterActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }
}
