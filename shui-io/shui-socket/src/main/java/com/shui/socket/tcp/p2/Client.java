package com.shui.socket.tcp.p2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	public static void main(String[] args) throws Exception {
		Socket socket =new Socket("localhost",8888);
		OutputStream osOutputStream=socket.getOutputStream();
		PrintWriter pwPrintWriter =new PrintWriter(osOutputStream);
		pwPrintWriter.write("[name:jim, pwd:123]");
		pwPrintWriter.flush();
		socket.shutdownOutput();
		InputStream isInputStream=socket.getInputStream();
		BufferedReader brBufferedReader=new BufferedReader(new InputStreamReader(isInputStream));
		String info =null;
		while((info=brBufferedReader.readLine())!=null){
			System.out.println("I am Client, now get message from Server:"+info);
		}
		brBufferedReader.close();
		isInputStream.close();
		pwPrintWriter.close();
		osOutputStream.close();
		socket.close();
	}
}
