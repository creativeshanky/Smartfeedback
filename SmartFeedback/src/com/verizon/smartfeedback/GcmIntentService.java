package com.verizon.smartfeedback;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GcmIntentService extends IntentService {
	public static final int NOTIFICATION_ID = 1;
	private static final String TAG = "shankar";
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    
    public GcmIntentService() {
        super("GcmIntentService");
    }
    
	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);
        Log.i("shankar", "Received: " + extras.toString());
        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that GCM
             * will be extended in the future with new message types, just ignore
             * any message types you're not interested in, or that you don't
             * recognize.
             */
            if (GoogleCloudMessaging.
                    MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                sendNotification("Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_DELETED.equals(messageType)) {
                sendNotification("Deleted messages on server: " +
                        extras.toString());
            // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                // Post notification of received message.  
            	//Toast.makeText(getApplicationContext(), extras.getString("message"), 3000).show();
                sendNotification(extras.getString("message"));
            	//sendMyNotification(extras.getString("message"));
                Log.i("shankar", "Received: " + extras.toString());
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
	}
	// Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);
        
        
        
        int notificationDrawable;
        String title,tag;
        
        Intent notificationIntent = new Intent(getApplicationContext(), MasterActivity.class);
        
        
        if(msg.trim().equalsIgnoreCase("pay your bills")){
        	notificationDrawable = R.drawable.notification_bill;
        	title = "Pay your bills instantly";
        	tag = "Pay your bills quickly with one tap right from your mobile";
        	PreferenceManager.getInstance(this).putStringValue("paybill", "true");
        }else{
        	notificationDrawable = R.drawable.notification;
        	title = "Verizon Feedback Survey";
        	tag = "Voice your feedback and save 2$ on your next bill";
        	PreferenceManager.getInstance(this).putStringValue("paybill", "false");
        }
        
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,notificationIntent, 0);
        
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),notificationDrawable);
        NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle();
        style.setBigContentTitle(title);
        style.bigPicture(bitmap);
        
        
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
        .setContentTitle(title)
        .setStyle(style)
        .setAutoCancel(true)
        .setSmallIcon(R.drawable.ic_launcher)
        .setContentText(tag);

        mBuilder.setContentIntent(contentIntent);
        
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());        
    }
}
