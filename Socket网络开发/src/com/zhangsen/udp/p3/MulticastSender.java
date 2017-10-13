package com.zhangsen.udp.p3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastSender {
 public static void main(String[] args)throws IOException   {
	  int port = 8888;  
      byte[] msg = "Connection successfully!!!".getBytes();  

      InetAddress inetRemoteAddr = InetAddress.getByName("224.0.0.5");  
      /* 
       * Java UDP�鲥Ӧ�ó�����Ҫͨ��MulticastSocketʵ������ͨ��,����DatagramSocket����һ������, 
       * ���а�����һЩ����Ŀ��Կ��ƶಥ������. 
       *  
       * ע�⣺ 
       *  
       * �ಥ���ݱ���ʵ���Ͽ���ͨ��DatagramSocket����,ֻ��Ҫ�򵥵�ָ��һ���ಥ��ַ�� 
       * ��������ʹ��MulticastSocket,����Ϊ������DatagramSocketû�е����� 
       */  
      MulticastSocket client = new MulticastSocket();  
      
      DatagramPacket sendPack = new DatagramPacket(msg, msg.length,  
              inetRemoteAddr, port);  

      client.send(sendPack);  

      System.out.println("Client send msg complete");  

      client.close();  
}
}
