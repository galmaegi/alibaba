package com.example.alibaba;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class LogListActivity extends Activity {


	private ListView ListLog;
	private AdpLog adapterLog;
	private ArrayList<ItemLog> arrayItemLog = new ArrayList<ItemLog>();

	public Context logContext;
	public static String picname;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_list);

		ListLog = (ListView)findViewById(R.id.ListLog);

		
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				
//				Log.d("listview","clicked");
//				arrayItemLog.get(position).getpicname();
//				Intent intent = new Intent(logContext,WebviewSelect.class);
//				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				startActivity(intent);
//				// TODO Auto-generated method stub
//				
//			}
//		});
		
		DownloadLogDB dld = new DownloadLogDB();
		dld.execute();
		
		logContext = this;

	}
	private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                long l_position) {
        	
        	Log.d("listview","clicked");
        	picname = arrayItemLog.get(position).getpicname();
			
			Intent intent = new Intent(logContext,WebviewSelect.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			// TODO Auto-generated method stub
        }
    };
     
	public void add2adp(){

		if(arrayItemLog.size()!=0){
			arrayItemLog.clear();

		}
		for(int i=0;i<DBfileList.filedb.size();i++){
			ItemLog il = new ItemLog(DBfileList.filedb.get(i)[0], DBfileList.filedb.get(i)[1], DBfileList.filedb.get(i)[2]);
			arrayItemLog.add(il);
		}
		adapterLog = new AdpLog(this,R.layout.item_log_list,arrayItemLog);
		ListLog.setAdapter(adapterLog);
		ListLog.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
//				Intent intent = new Intent(this,)
				
			}
		});

	}
	

	private class DownloadLogDB extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			try
			{
				String result = null;
				InputStream inputStream = null;
				try {
					

					HttpPost Downloadrequest = new HttpPost("http://minshu.iptime.org:2510/alibaba/getLogList.php");
//					Vector<NameValuePair> DownloadInfo = new Vector<NameValuePair>();
//
//					DownloadInfo.add(new BasicNameValuePair("_listno","0"));
//
//					HttpEntity httpEnty = new UrlEncodedFormEntity(DownloadInfo,HTTP.UTF_8);
//					Downloadrequest.setEntity(httpEnty);
					HttpClient client = new DefaultHttpClient();
					HttpResponse httpRes = client.execute(Downloadrequest);


					////////////////서버로부터 echo를 받아옴/////////////////

					HttpEntity entity = httpRes.getEntity();
					inputStream = entity.getContent();
					// json is UTF-8 by default//
					BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
					StringBuilder sb = new StringBuilder();

					String line = null;
					while ((line = reader.readLine()) != null)
					{
						sb.append(line + "\n");
					}
					DBfileList.filedb.clear();
					result = sb.toString();
					if(result.contains("failtofetch"))
						return null;
					JSONArray jArray = new JSONArray(result);					//					String result = getXmlData("dupCheckresult.xml", "result"); //입력 성공여부


					for (int i=0; i < jArray.length(); i++)
					{
						try {
							JSONObject oneObject = jArray.getJSONObject(i);
							// Pulling items from the array
							String[] temp = new String[3];
							temp[0]=oneObject.getString("id");
							temp[1]=oneObject.getString("videoname");
							temp[2]=oneObject.getString("time");
							DBfileList.filedb.add(temp);
							temp=null;
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}


				} catch(Exception e) {
					e.printStackTrace();
					Log.i("Error", e.getStackTrace().toString());
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			return null;

		}

		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			add2adp();
			ListLog.setOnItemClickListener(mItemClickListener);
		}
	}
}
