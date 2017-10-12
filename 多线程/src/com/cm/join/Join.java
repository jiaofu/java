package com.cm.join;

public class Join {
 public static void main(String[] args) throws InterruptedException {
		System.out.println("start");
	Thread previousThread =Thread.currentThread();

	for(int i=0;i<10;i++){
	  Thread thread=new Thread(new Domino(previousThread),String.valueOf(i));
	  thread.start();

	  previousThread=thread;
	}
	Thread.sleep(1000);
}
}
class Domino implements Runnable{

	private Thread thread;
	public Domino(Thread thread){
		this.thread=thread;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(thread.getName() +"terminate");
	}
	
}

//��JDK��Thread.join()������Դ�루�����˲��ֵ������������嵥4-14��Thread.java
// ������ǰ�̶߳���public final synchronized void join() throws InterruptedException {       
// ���������㣬�����ȴ�       while (isAlive()) {        
//						wait(0);       }      
// �������ϣ���������
//}���߳���ֹʱ��������߳������notifyAll()��������֪ͨ���еȴ��ڸ��̶߳����ϵ��̡߳�
//���Կ���join()�������߼��ṹ��4.3.3���������ĵȴ�/֪ͨ���䷶ʽһ�£���������ѭ���ʹ����߼�3�����衣