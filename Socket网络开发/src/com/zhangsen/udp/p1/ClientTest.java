package com.zhangsen.udp.p1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;


public class ClientTest {
	 private static final int MAXRECEIVED = 255;  
	 public static void main(String[] args) throws Exception {
		   byte[] msg = new String("connect test successfully!!!").getBytes();  
		DatagramSocket client =new DatagramSocket();
		InetAddress inetAddr = InetAddress.getLocalHost();
		SocketAddress socketAddr = new InetSocketAddress(inetAddr,8888);
		DatagramPacket sendPacket=new DatagramPacket(msg, msg.length,socketAddr);
		client.send(sendPacket);
		client.close();
	}
}
