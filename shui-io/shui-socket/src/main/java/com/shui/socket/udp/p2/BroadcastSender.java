package com.shui.socket.udp.p2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class BroadcastSender {
  
	public static void main(String[] args) throws IOException {
		byte [] msg =new String("connection successfully").getBytes();
		/*
		 * 在Java UDP中单播与广播的代码是相同的,要实现具有广播功能的程序只需要使用广播地址即可, 例如：这里使用了本地的广播地址
		 */
		InetAddress inetAddr=InetAddress.getByName("255.255.255.255");
		DatagramSocket client =  new  DatagramSocket();
		DatagramPacket sendPacket = new DatagramPacket(msg, msg.length,inetAddr,8888);
		client.send(sendPacket);
		System.out.println("Client send msg complete");
		client.close();
	}
}
