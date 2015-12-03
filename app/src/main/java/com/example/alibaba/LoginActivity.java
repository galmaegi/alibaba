package com.example.alibaba;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
//import com.google.android.gcm.GCMRegistrar;


public class LoginActivity extends Activity {

	private Socket socket;
	private Connect c;

	private Button b_new;
	private TextView textView_ip, textView_port;
	private EditText e_ip, e_port;
	private Button b_ip;
	private String regId;
	//자동로그인
	private boolean isAutoLogin;
	private String userIP;
	private int userPort;
	
	//gcm
	GoogleCloudMessaging gcm;
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	private static final String SENDER_ID = "770008474439";

	public static Context gcmContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
//		c = new Connect();
		
		gcmContext = this;
//		c.start();

		textView_ip = (TextView) findViewById(R.id.textView_ip);
		textView_port = (TextView) findViewById(R.id.textView_port);

		e_ip = (EditText) findViewById(R.id.e_ip);
		e_port = (EditText) findViewById(R.id.e_port);
		b_ip = (Button) findViewById(R.id.b_ip);
		e_ip.setText(Connection.serverIP);
		e_port.setText(Connection.serverPort+"");
		
		isAutoLogin = PropertyManager.getInstance().getAutoLogin();
		//처음일경우
		if(isAutoLogin == false) {
			PropertyManager.getInstance().setIsAlram(true);
			PropertyManager.getInstance().setAutoLogin(true);
			PropertyManager.getInstance().setUserIP(Connection.serverIP);
			PropertyManager.getInstance().setUserPort(Connection.serverPort);
			autoLogin();
		} else {
			autoLogin();
		}
		
		b_ip.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Connection.serverIP = e_ip.getText().toString();
				Connection.serverPort = Integer.parseInt(e_port.getText().toString());
				c = new Connect();
				c.start();
				Toast.makeText(getApplicationContext(), "로그인 되었습니다!", Toast.LENGTH_LONG).show();

				Intent intent = new Intent(LoginActivity.this,
						MenuActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP 
		                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
			}
		});
		
		Intent intent = new Intent(this,ForGcmService.class);
		startService(intent);
//		finish();
	}

	@Override
	protected void onResume() {
		super.onResume();
		//regid 없으면 발급
		getRegId();
	}
	
	private void autoLogin() {
		Connection.serverIP = PropertyManager.getInstance().getUserIP();
		Connection.serverPort = PropertyManager.getInstance().getUserPort();
		c = new Connect();
		c.start();
		Toast.makeText(getApplicationContext(), "자동로그인 되었습니다!", Toast.LENGTH_LONG).show();

		Intent intent = new Intent(LoginActivity.this,
				MenuActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP 
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
	}

	@Override
	protected void onPause() {
	super.onPause();
//	GCMRegistrar.unregister(this);
	}
	
	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {
				AlertDialog.Builder alert = new AlertDialog.Builder(
						LoginActivity.this);
				alert.setPositiveButton("확인",
						new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}

				});

				alert.setMessage("로그인실패!");
				alert.show();
			}
		};
	};

	public class Connect extends Thread {

		public void run() {
			Connection.connect();
		}

	}// end of Client

//	private class Login extends Thread {
//
//		public void run() {
//
//			try {
//
//				try {
//					socket = Connection.getSocket();
//
//					OutputStream out = socket.getOutputStream();
//					out.write(100);
//					out.flush();
//				} catch (Exception e) {
//					Log.e("TCP", "C: Error1", e);
//				}
//			} catch (Exception e) {
//				Log.e("TCP", "C: Error2", e);
//			}
//		}// end of run
//
//	}// end of Login

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:

			new AlertDialog.Builder(this)
			.setTitle("종료")
			.setMessage("종료하시겠습니까?")
			.setPositiveButton(
					"예",
					new android.content.DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog,
								int which) {
							// TODO Auto-generated method stub
							if (socket != null) {
								try {
									socket.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

							android.os.Process
							.killProcess(android.os.Process
									.myPid());
							System.exit(0);
							finish();
						}
					}).setNegativeButton("아니오", null).show();
			return false;
		default:
			return false;
		}
	}
	
	private void getRegId() {
		// gcm 사용가능한 플레이 서비스 버전인지 체크
		if (checkPlayServices()) {
			// property manager에 저장
			registerInBackground();
			String regId = PropertyManager.getInstance().getRegId();
			Log.d("regid", regId+"");
//			if (!regId.equals("")) {
//				registerInBackground();
//			} else {
				// regid 발급
				
//			}
		} else {
		}
	}
	
	private void registerInBackground() {
		new AsyncTask<Void, Integer, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging
								.getInstance(LoginActivity.this);
					}
					String regid = gcm.register(SENDER_ID);
					PropertyManager.getInstance().setRegId(regid);
					setRegId(); //서버로전송

				} catch (IOException ex) {
				}
				return msg;
			}

			@Override
			protected void onPostExecute(String msg) {
				// runOnUiThread(nextAction);
			}
		}.execute(null, null, null);
	}

	private void setRegId() {
		String regId = PropertyManager.getInstance().getRegId();
		if (regId != null && !regId.equals("")) {
			//TODO regid 서버로전송
			Log.d("RegId===========", regId);
			HttpClient httpClient = new DefaultHttpClient();  
			String url = "http://minshu.iptime.org:2510/alibaba/gcm2.php?shareRegId="+ regId;
			HttpGet httpGet = new HttpGet(url);
			try {
			    HttpResponse response = httpClient.execute(httpGet);
			    StatusLine statusLine = response.getStatusLine();
			    if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
			        HttpEntity entity = response.getEntity();
			        ByteArrayOutputStream out = new ByteArrayOutputStream();
			        entity.writeTo(out);
			        out.close();
			        // do something with response 
			    } else {
			        // handle bad response
			    }
			} catch (ClientProtocolException e) {
			    // handle exception
			} catch (IOException e) {
			    // handle exception
			}
		}
	}

	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				Dialog dialog = GooglePlayServicesUtil.getErrorDialog(
						resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST);
				dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {

					}
				});
				dialog.show();
			} else {
				// To Do...
				finish();
			}
			return false;
		}
		return true;
	}
}
