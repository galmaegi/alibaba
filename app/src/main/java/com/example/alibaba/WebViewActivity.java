package com.example.alibaba;

import java.io.OutputStream;
import java.net.Socket;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class WebViewActivity extends Activity {
	
	private Socket socket;
//	private Connect c;

	private WebView mWebView;
	private Button openbtn;
	private Button ignorebtn;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.web_view);
		
		setLayout();

		WebSettings settings =  mWebView.getSettings();
		settings.setLoadWithOverviewMode(true);
		settings.setUseWideViewPort(true);
		mWebView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		mWebView.getSettings().setJavaScriptEnabled(true);
		
		mWebView.getSettings().setBuiltInZoomControls(true);
		mWebView.getSettings().setSupportZoom(true);
		mWebView.getSettings().setUseWideViewPort(true);
		mWebView.getSettings().setLoadWithOverviewMode(true);
		
	    mWebView.loadUrl("http://"+Connection.serverIP+":"+Connection.webserverPort+"/stream_simple.html");
	    mWebView.setWebViewClient(new WebViewClientClass());  
	    
	    
	    openbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Login login = new Login();
				login.start();
			}
		});
	    
	    ignorebtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	    
	}
	
	private class Login extends Thread{
		
		public void run() 
		{
			
			
			try {   

				try {
					Connection.connect();
					socket = Connection.getSocket();
					
					OutputStream out = socket.getOutputStream();
					out.write(100);
					out.flush();
				} catch(Exception e) {
					 Log.e("TCP", "C: Error1", e);
				}
		    } catch (Exception e) {
		        Log.e("TCP", "C: Error2", e);
		    }
		}//end of run

		  
	}//end of Login
	@Override 
    public boolean onKeyDown(int keyCode, KeyEvent event) { 
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) { 
            mWebView.goBack(); 
            return true; 
        } 
        return super.onKeyDown(keyCode, event);
    }
    
    private class WebViewClientClass extends WebViewClient { 
        @Override 
        public boolean shouldOverrideUrlLoading(WebView view, String url) { 
            view.loadUrl(url); 
            return true; 
        } 
    }

	private void setLayout(){
		mWebView = (WebView) findViewById(R.id.webview);
		openbtn = (Button)findViewById(R.id.openbtn);
		ignorebtn = (Button)findViewById(R.id.ignorebtn);
	}
}
