package com.example.alibaba;


import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GcmIntentService extends IntentService {
	private static final String TAG="GcmIntentService";
	public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    private boolean isAlram;
    NotificationCompat.Builder builder;
	private String time;
	public static Context gcmContext;
//    @Override
//    public void onCreate() {
//
//    }
	@Override
	public void onCreate(){
        super.onCreate();
		this.gcmContext = this;
		
	}
	
	
    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();        
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);
        try {
            Log.d("msg", messageType);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that GCM
             * will be extended in the future with new message types, just ignore
             * any message types you're not interested in, or that you don't
             * recognize.
             */
            if (GoogleCloudMessaging.
                    MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
//                sendNotification("Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_DELETED.equals(messageType)) {
//                sendNotification("Deleted messages on server: " +
//                        extras.toString());
            // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_MESSAGE.equals(messageType)) {
//              time = intent.getStringExtra("time");
//              sendNotification("Received: " + extras.toString());
            	String msg = intent.getStringExtra("msg");
                // Post notification of received message.
//                sendNotification("Received: " + extras.toString());
                sendNotification("Received: " + msg);
                Log.i("GcmIntentService.java | onHandleIntent", "Received: " + extras.toString());
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }
    
//    @Override
//    public void onStart(Intent intent, int startId) {
//    	Message msg = mServiceHandler.obtainMessage();
//    	msg.arg1 = startId;
//    	msg.obj = intent;
//    	mServiceHandler.sendMessage(msg);
//    }
//    
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//    onStart(intent, startId);
//    return mRedelivery ? START_REDELIVER_INTENT : START_NOT_STICKY;
//    }

    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    public void sendNotification(String msg) {
    	isAlram = PropertyManager.getInstance().getIsAlram();
    	//알람설정이 되어있는 경우에만 노티를 보냄
    	if(isAlram == true) {
    		mNotificationManager = (NotificationManager)
                    this.getSystemService(Context.NOTIFICATION_SERVICE);

            PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                    new Intent(this, LoginActivity.class), 0);

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.ic_launcher)
            .setContentTitle("Alibaba noti")
            .setStyle(new NotificationCompat.BigTextStyle()
            .bigText(msg))
            .setContentText(msg);

            mBuilder.setContentIntent(contentIntent);
            mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    	}
    }
}
