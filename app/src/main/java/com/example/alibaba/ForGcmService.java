package com.example.alibaba;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ForGcmService extends Service{

	public static Context gcmContext;
	@Override
	public void onCreate(){
		this.gcmContext = this;
		Log.d("gcmservice","created");
		
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		this.gcmContext = this;		
		Log.d("gcmservice","started");
		return 1;
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param args
	 */

}
