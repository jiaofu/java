package com.zhangsen.udp.p2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class BroadcastSender {
  
	public static void main(String[] args) throws IOException {
		byte [] msg =new String("connection successfully").getBytes();
		   /* 
         * ��Java UDP�е�����㲥�Ĵ�������ͬ��,Ҫʵ�־��й㲥���ܵĳ���ֻ��Ҫʹ�ù㲥��ַ����, ���磺����ʹ���˱��صĹ㲥��ַ 
         */  
		InetAddress inetAddr=InetAddress.getByName("255.255.255.255");
		DatagramSocket client =  new  DatagramSocket();
		DatagramPacket sendPacket = new DatagramPacket(msg, msg.length,inetAddr,8888);
		client.send(sendPacket);
		System.out.println("Client send msg complete");
		client.close();
	}
}
