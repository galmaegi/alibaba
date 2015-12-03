package com.example.alibaba;

import android.content.Context;
import android.content.SharedPreferences;

public class PropertyManager {
	public static PropertyManager instance;

	public static PropertyManager getInstance() {
		if (instance == null) {
			instance = new PropertyManager();
		}
		return instance;
	}

	private static final String PREF_NAME = "my_prefs";
	SharedPreferences mPrefs;
	SharedPreferences.Editor mEditor;

	private PropertyManager() {
		mPrefs = MyApplication.getContext().getSharedPreferences(PREF_NAME,
				Context.MODE_PRIVATE);
		mEditor = mPrefs.edit();
	}

	// 자동로그인여부
	private static final String FIELD_IS_AUTOLOGIN = "isAutoLogin";
	private boolean isAutoLogin;

	public boolean getAutoLogin() {
		return mPrefs.getBoolean(FIELD_IS_AUTOLOGIN, false);
	}

	public void setAutoLogin(boolean isAutoLogin) {
		this.isAutoLogin = isAutoLogin;
		mEditor.putBoolean(FIELD_IS_AUTOLOGIN, isAutoLogin);
		mEditor.commit();
	}

	private static final String FIELD_USER_IP = "userIP";
	private String userIP;

	public String getUserIP() {
		if (userIP == null) {
			userIP = mPrefs.getString(FIELD_USER_IP, "");
		}
		return userIP;
	}

	public void setUserIP(String userIP) {
		this.userIP = userIP;
		mEditor.putString(FIELD_USER_IP, userIP);
		mEditor.commit();
	}

	// port : 9998
	private static final String FIELD_USER_PORT = "userPort";
	private int port;

	public int getUserPort() {
		return mPrefs.getInt(FIELD_USER_PORT, 0);
	}

	public void setUserPort(int port) {
		this.port = port;
		mEditor.putInt(FIELD_USER_PORT, port);
		mEditor.commit();
	}

	// 알람여부
	public boolean isAlram;
	public static final String FIELD_IS_ALRAM = "isAlram";

	public boolean getIsAlram() {
		return mPrefs.getBoolean(FIELD_IS_ALRAM, true);
	}

	public void setIsAlram(boolean isAlram) {
		this.isAlram = isAlram;
		mEditor.putBoolean(FIELD_IS_ALRAM, isAlram);
		mEditor.commit();
	}

	// gcm regid
	private static final String FIELD_REGID = "regId";
	private String regId;

	public String getRegId() {
		if (regId == null) {
			regId = mPrefs.getString(FIELD_REGID, "");
		}
		return mPrefs.getString(FIELD_REGID, "");
	}

	public void setRegId(String regId) {
		this.regId = regId;
		mEditor.putString(FIELD_REGID, regId);
		mEditor.commit();
	}

}
