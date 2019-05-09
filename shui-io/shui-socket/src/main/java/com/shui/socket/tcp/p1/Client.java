package com.shui.socket.tcp.p1;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;



public class Client {
	public static void main(String[] args)   {
		try {
			final int length=100;
			String host="localhost";
			int port=8082;
			 Socket[] socket = new Socket[length];
			for(int i=0;i<length;i++){
				socket[i]=new Socket(host,port);
				System.out.println("第"+(i+1)+"次连接成功！");
			}
			Thread.sleep(3000);
			for(int i=0;i<length;i++){
				socket[i].close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	

}
