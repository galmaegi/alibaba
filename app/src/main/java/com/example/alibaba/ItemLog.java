package com.example.alibaba;

public class ItemLog {
	private String id;
	private String picname;
	private String time;
	public ItemLog(String id,String picname,String time){
		this.id = id;
		this.picname = picname;
		this.time = time;
	}
	public String getid(){
		return id;
	}
	public String getpicname(){
		return picname;
	}
	public String gettime(){
		return time;
	}
	

}
