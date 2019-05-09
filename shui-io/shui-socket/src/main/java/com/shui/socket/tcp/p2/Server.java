package com.shui.socket.tcp.p2;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws Exception {
		// 初始化服务端socket连接，并监听8888端口的socket请求
		ServerSocket serverSocket =new ServerSocket(8888);
		Socket socket=null;
		int count=0;
		System.out.println("****** I am Server, now begin to wait the client ******");
		while(true){
			socket=serverSocket.accept();
			ServerThread serverThread=new ServerThread(socket);
			serverThread.start();
			count++;//ͳ�ƿͻ��˵�����
            System.out.println("now client count is:"+count);
            InetAddress address=socket.getInetAddress();
            System.out.println("client host address is:"+address.getHostAddress());
		}
	}
}
