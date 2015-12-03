package com.example.alibaba;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class MyApplication extends Application {

	public static Context mContext;

	@Override
	public void onCreate() {
		super.onCreate();
		mContext = this;
		mInstance = this;
	}

	public static Context getContext(){
		return mContext;
	}

	public static final String TAG = MyApplication.class.getSimpleName();

	private RequestQueue mRequestQueue;

	//to loading image using volley lib
	private ImageLoader mImageLoader;

	private static MyApplication mInstance;


	public static synchronized MyApplication getInstance() {
		return mInstance;
	}


	//for volley
	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}

		return mRequestQueue;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}

	//to loading image using volley lib
	public ImageLoader getImageLoader() {
		getRequestQueue();
		if (mImageLoader == null) {
			mImageLoader = new ImageLoader(this.mRequestQueue,
					new LruBitmapCache(LruBitmapCache.getDefaultLruCacheSize()));
		}
		return this.mImageLoader;
	}
}
