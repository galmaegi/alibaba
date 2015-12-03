package com.example.alibaba;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class VideoListView extends ActionBarActivity {

	ListView listView;
	TextView messageView;
	EditText keywordView;

	ArrayAdapter<String> mAdapter;
	ArrayList<String> mData = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_view);

		listView = (ListView) findViewById(R.id.listView1);
		messageView = (TextView) findViewById(R.id.messageView);
		keywordView = (EditText) findViewById(R.id.editKeyowrd);

		makeData();

		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		mAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_multiple_choice, mData);

		listView.setAdapter(mAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String text = (String) listView.getItemAtPosition(position);
				messageView.setText(text);
			}
		});

		Button btn = (Button) findViewById(R.id.btnAdd);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mAdapter.add(keywordView.getText().toString());
			}
		});

		btn = (Button) findViewById(R.id.btnChoice);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (listView.getChoiceMode() == ListView.CHOICE_MODE_SINGLE) {
					int position = listView.getCheckedItemPosition();
					String text = (String) listView.getItemAtPosition(position);
					Toast.makeText(VideoListView.this, "choice item : " + text,
							Toast.LENGTH_SHORT).show();
				} else if (listView.getChoiceMode() == ListView.CHOICE_MODE_MULTIPLE) {
					SparseBooleanArray selectedList = listView
							.getCheckedItemPositions();
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < selectedList.size(); i++) {
						int position = selectedList.keyAt(i);
						if (selectedList.get(position)) {
							String text = (String) listView
									.getItemAtPosition(position);
							sb.append(text);
							sb.append(",");
						}
					}
					Toast.makeText(VideoListView.this,
							"selected Items : " + sb.toString(),
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private void makeData() {
		String[] array = getResources().getStringArray(R.array.listItem);
		Collections.addAll(mData, array);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
