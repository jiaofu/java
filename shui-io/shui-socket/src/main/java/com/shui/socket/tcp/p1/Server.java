package com.shui.socket.tcp.p1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
 private int port=8082;
 private ServerSocket serverSocket;
 public Server() throws Exception{
	 serverSocket =new ServerSocket(port,15);
	 System.out.println("服务器启动!");
 }
 public void service(){
	 while(true){
		 Socket socket =null;
		 try{
			 socket=serverSocket.accept();
			 System.out.println("Now connection accepted"+socket.getInetAddress()+" :"+ socket.getPort());
		 }catch(IOException e){
			 e.printStackTrace();
		 }
		 finally{
			 if(socket !=null){
				 try{
					 socket.close();
				 }catch(IOException e){
					 e.printStackTrace();
				 }
			 }
		 }
	 }
 }
 public static void main(String[] args) throws Exception {
	Server server =new Server();
	Thread.sleep(60000);
	server.service();
}
	
}
