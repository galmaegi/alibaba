package com.example.alibaba;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebviewSelect extends Activity{


	public WebView webView_log;
    class WebClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_web);
		
		webView_log = (WebView)findViewById(R.id.webView_log);
		webView_log.setWebViewClient(new WebClient()); // 응룡프로그램에서 직접 url 처리
        WebSettings set = webView_log.getSettings();
        set.setJavaScriptEnabled(true);
        set.setBuiltInZoomControls(true);
        webView_log.loadUrl("http://minshu.iptime.org:2510/alibaba/videosingle.php?video="+LogListActivity.picname);
//		WebSettings set = webView_log.getSettings();
//		set.setJavaScriptEnabled(true);
//		String postData = "test1.mjpg";
//		webView_log.postUrl("http://galmaegi.iptime.org:9080/alibaba/video.php", EncodingUtils.getAsciiBytes(postData));
		
	}

//	private class ProcessInsertfolderTask extends AsyncTask<String, Void, Void>{
//
//		@Override
//		protected Void doInBackground(String... params) {
//
//			String folder_name = params[0];
//			try
//			{
//				try {
//					HttpPost insertfolderrequest = new HttpPost("http://galmaegi.iptime.org:9080/alibaba/folderdbcreate.php");
//					Vector<NameValuePair> insertfolderInfo = new Vector<NameValuePair>();
//
//					insertfolderInfo.add(new BasicNameValuePair("user_no",MainActivity.userno));
//					insertfolderInfo.add(new BasicNameValuePair("folder_name",folder_name));
//
//					HttpEntity httpEnty = new UrlEncodedFormEntity(insertfolderInfo,HTTP.UTF_8);
//					insertfolderrequest.setEntity(httpEnty);
//					HttpClient client = new DefaultHttpClient();
//					HttpResponse httpRes = client.execute(insertfolderrequest);
//					///////////////////////echo메세지를 가져옴/////////////////////
//					HttpEntity afterentity = httpRes.getEntity();
//					final String htmlResponse = EntityUtils.toString(afterentity);
//					/////////////////////////////////////////////////////////
//					if(htmlResponse.contains("Success")){
//						MainActivity.this.runOnUiThread(new Runnable() {
//							public void run() {
//								ProcessDownloadfolderdbTask pdownfolder = new ProcessDownloadfolderdbTask();
//								pdownfolder.execute();
//
//								Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
//
//							}
//						});
//
//					}
//					else if(htmlResponse.contains("Already")){ 
//						MainActivity.this.runOnUiThread(new Runnable() {
//							public void run() {
//								Toast.makeText(MainActivity.this, "이미 존재하는 폴더명입니다.", Toast.LENGTH_SHORT).show();
//
//							}
//						});
//					}
//					else { 
//						MainActivity.this.runOnUiThread(new Runnable() {
//							public void run() {
//								Toast.makeText(MainActivity.this, "실패."+htmlResponse, Toast.LENGTH_SHORT).show();
//
//							}
//						});
//					}
//
//				} catch(Exception e) {
//					e.printStackTrace();
//					Log.i("Error", e.getStackTrace().toString());
//				}
//			}
//			catch (Exception e)
//			{
//				e.printStackTrace();
//			}
//			return null;
//		}
//
//	}

}
