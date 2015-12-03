package com.example.alibaba;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

public class MenuActivity extends Activity {
	
	private Button streambtn;
	private Button storagebtn;
	private ToggleButton alarmbtn;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_view);
		boolean alarm = PropertyManager.getInstance().getIsAlram();

	    streambtn = (Button)findViewById(R.id.streambtn);
	    storagebtn = (Button)findViewById(R.id.storagebtn);
	    alarmbtn = (ToggleButton)findViewById(R.id.alrambtn);
	    
	    streambtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MenuActivity.this, WebViewActivity.class);
				startActivity(intent);
			}
		});
	    
	    storagebtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MenuActivity.this, LogListActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
		});
	    
	    alarmbtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				//�븣�엺 �꽭�똿 媛� 蹂�寃�
				PropertyManager.getInstance().setIsAlram(isChecked);
			}
		});
	    alarmbtn.setChecked(alarm);
	}
}
