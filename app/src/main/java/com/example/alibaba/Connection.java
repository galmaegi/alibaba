package com.example.alibaba;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import android.util.Log;

public class Connection 
{
	private static Socket socket;
	public static String serverIP = "192.168.229.106";
	public static int serverPort = 9998;
	public static int webserverPort = 8080;
	
	public static void connect()
	{
		InetAddress serverAddr;
		try {
			serverAddr = InetAddress.getByName(serverIP);
			Log.d("TCP", "C: Connecting...");
			socket = new Socket(serverAddr, serverPort);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static Socket getSocket()
	{
		return socket;
	}
}
