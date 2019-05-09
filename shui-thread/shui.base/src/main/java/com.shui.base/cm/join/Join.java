package com.shui;

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

//由于 wait() 与  notify/notifyAll() 是放在同步代码块中的，因此线程在执行它们时，肯定是进入了临界区中的，即该线程肯定是获得了锁的。
//
//当线程执行wait()时，会把当前的锁释放，然后让出CPU，进入等待状态。
//
// 当执行notify/notifyAll方法时，会唤醒一个处于等待该 对象锁 的线程，然后继续往下执行，直到执行完退出对象锁锁住的区域（synchronized修饰的代码块）后再释放锁。
//多线程中测试某个条件的变化用 if 还是用 while？因为可能有多个线程await在这里，一个notifyAll，全部唤醒，又要重新竞争，先得到时间片的线程向下运行了，其他线程又需要回到await上。如果不是while，而是if，所有的线程都会走下去了