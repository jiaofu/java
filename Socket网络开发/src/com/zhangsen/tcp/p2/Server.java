package com.zhangsen.tcp.p2;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws Exception {
		ServerSocket serverSocket =new ServerSocket(8888);
		Socket socket=null;
		int count=0;
		System.out.println("�����������������ȴ��ͻ��˵�����***");
		while(true){
			socket=serverSocket.accept();
			ServerThread serverThread=new ServerThread(socket);
			serverThread.start();
			count++;//ͳ�ƿͻ��˵�����
            System.out.println("�ͻ��˵�������"+count);
            InetAddress address=socket.getInetAddress();
            System.out.println("��ǰ�ͻ��˵�IP��"+address.getHostAddress());
		}
	}
}
