package com.example.alibaba;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {
	private static final String TAG="GcmIntentService";
	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	private boolean isAlram;
	NotificationCompat.Builder builder;
	private String time;

	@Override
	public void onReceive(Context context, Intent intent) {
		
		Log.i("GcmBroadcastReceiver.java | onReceive", "|" + "================="+"|");
		Bundle bundle = intent.getExtras();
		for (String key : bundle.keySet())
		{
			Object value = bundle.get(key);
			Log.i("GcmBroadcastReceiver.java | onReceive", "|" + String.format("%s : %s (%s)", key, value.toString(), value.getClass().getName()) + "|");
		}
		Log.i("GcmBroadcastReceiver.java | onReceive", "|" + "================="+"|");

		ComponentName comp = new ComponentName(context.getPackageName(),
				GcmIntentService.class.getName());
		startWakefulService(context, (intent.setComponent(comp)));
		setResultCode(Activity.RESULT_OK);
		
	    GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
	    String messageType = gcm.getMessageType(intent);
	    
		if (GoogleCloudMessaging.
                    MESSAGE_TYPE_MESSAGE.equals(messageType)) {
        		sendNotification(context, "방문자가 나타났습니다.");
            }
	    GcmBroadcastReceiver.completeWakefulIntent(intent);
	}
	
	//�끂�떚 �쓣�슱 �븣 context �븘�슂�븯誘�濡� 由ъ떆踰꾩뿉�꽌 context瑜� �뙆�씪誘명꽣濡� 媛��졇�삤�룄濡� 蹂�寃�
    public void sendNotification(Context context, String msg) {
    	isAlram = PropertyManager.getInstance().getIsAlram();
    	//�븣�엺�꽕�젙�씠 �릺�뼱�엳�뒗 寃쎌슦�뿉留� �끂�떚瑜� 蹂대깂
    	if(isAlram == true) {
    		mNotificationManager = (NotificationManager)
                    ForGcmService.gcmContext.getSystemService(Context.NOTIFICATION_SERVICE);
    		
    		//�끂�떚 �겢由� �떆 濡쒓렇�씤 �븸�떚鍮꾪떚濡� �씠�룞
    		Intent i = new Intent(context, LoginActivity.class);
    		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP 
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
    		PendingIntent pi = PendingIntent.getActivity(context, 0,
    				i, PendingIntent.FLAG_UPDATE_CURRENT);

            PendingIntent contentIntent = PendingIntent.getActivity(ForGcmService.gcmContext, 0,
                    new Intent(ForGcmService.gcmContext, ForGcmService.class), 0);

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(ForGcmService.gcmContext)
            .setSmallIcon(R.drawable.ic_launcher)
            .setContentTitle("Alibaba")
            .setStyle(new NotificationCompat.BigTextStyle()
            .bigText(msg))
            .setAutoCancel(true)
            .setContentText(msg);
            
            mBuilder.setContentIntent(contentIntent);
            mBuilder.setContentIntent(pi); // �븸�떚鍮꾪떚濡� �씠�룞
            mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    	}
    }

}
