package com.example.alibaba;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class AdpLog extends ArrayAdapter<ItemLog>{

	private ArrayList<ItemLog> arrayItem;
	private LayoutInflater mInflater;
	private BitmapFactory.Options mBitmapOptions;
	ViewHolder viewHolder=null;

	public interface onButtonClickListener{
		void onButton1Click(ItemLog item);
	}
	private onButtonClickListener adpItemClick = null;
	public void setonButtonClickListener(onButtonClickListener ItemClick){
		adpItemClick = ItemClick;
	}

	public AdpLog(Context context, int resource, ArrayList<ItemLog> objects) {
		super(context, resource, objects);
		this.arrayItem = objects;
		this.mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// TODO Auto-generated constructor stub
	}
	static class ViewHolder{
		protected TextView TextTime;
	}

	public View getView(int position,View convertView,ViewGroup parent){
		View v = convertView;

		if(v==null||viewHolder==null){

			v = mInflater.inflate(R.layout.item_log_list,parent,false);
			viewHolder = new ViewHolder();
			viewHolder.TextTime = (TextView)v.findViewById(R.id.TextTime);

			v.setTag(viewHolder);
			v.setTag(R.id.TextTime,viewHolder.TextTime);

		}
		else
			viewHolder=(ViewHolder)v.getTag();

		final ItemLog item = arrayItem.get(position);

		if(item!=null){

			viewHolder.TextTime.setText(item.getpicname());
			
		}
		return v;
	}
		
}
