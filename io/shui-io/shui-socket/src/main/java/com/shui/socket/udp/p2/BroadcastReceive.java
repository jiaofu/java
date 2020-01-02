package com.shui.socket.udp.p2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

/**
 * 2.广播：单台主机与网络中所有主机的通信；
 */
public class BroadcastReceive {
 public static void main(String[] args)  throws IOException  {
	DatagramPacket receive = new DatagramPacket(new byte[1024], 1024);
	DatagramSocket server=new DatagramSocket(8888);
	System.out.println("---------------------------------");  
    System.out.println("Server current start......");  
    System.out.println("---------------------------------");  
    while(true){
    	server.receive(receive);
    	byte[]  recvByte= Arrays.copyOfRange(receive.getData(),0 , receive.getLength());
    	System.out.println("Server receive msg:" + new String(recvByte));
    }
	 
}
}
