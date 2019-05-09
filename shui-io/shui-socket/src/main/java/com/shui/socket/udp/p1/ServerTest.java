package com.shui.socket.udp.p1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class ServerTest {
	 private static final int MAXREV = 255; 
	 public static void main(String[] args) throws IOException{
		 DatagramSocket server= new DatagramSocket(8888);
		 DatagramPacket recepacket = new DatagramPacket(new byte[MAXREV], MAXREV);
		 while(true){
			 server.receive(recepacket);
			 byte[] receiveMsg = Arrays.copyOfRange(recepacket.getData(),recepacket.getOffset(),recepacket.getOffset()+recepacket.getLength());
			 System.out.println("Handing at client"+ recepacket.getAddress().getHostName() +" ip " + recepacket.getAddress().getHostAddress());
			 System.out.println("Server Receive Data:" + new String(receiveMsg));
			 server.send(recepacket);  
			 
		 }
	 }
}
