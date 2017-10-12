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

//是JDK中Thread.join()方法的源码（进行了部分调整）。代码清单4-14　Thread.java
// 加锁当前线程对象public final synchronized void join() throws InterruptedException {       
// 条件不满足，继续等待       while (isAlive()) {        
//						wait(0);       }      
// 条件符合，方法返回
//}当线程终止时，会调用线程自身的notifyAll()方法，会通知所有等待在该线程对象上的线程。
//可以看到join()方法的逻辑结构与4.3.3节中描述的等待/通知经典范式一致，即加锁、循环和处理逻辑3个步骤。