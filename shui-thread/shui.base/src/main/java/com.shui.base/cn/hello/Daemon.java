package com.shui.base.cn.hello;

public class Daemon {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
     Thread thread =new Thread(new DaemonRunner(),"DamonRuner");
     thread.setDaemon(true);
     thread.start();
     System.out.println(thread.getName());
     
	}


}
 class DaemonRunner implements Runnable{

	@Override
	public void run() {
		 
	  try {
		Thread.sleep(1000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally{
		System.out.println("DaemonTHread funally run");
	}
	  
	}
	
}

 // �� DaemonRunner �� ��������finally �е�������ȷ��ִ�йرջ�������Դ���߼�
 // ��Ϊ����ִ��