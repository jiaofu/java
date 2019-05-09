package com.shui.base.cm.join;

public class Main {

	 public static void main(String[] args) throws InterruptedException
	    {
	        Thread t = new Thread(new Runnable()
	        {
	            public void run()
	            {
	                System.out.println("First task started");
	                System.out.println("Sleeping for 5 seconds");
	                try
	                {
	                    Thread.sleep(5000);
	                } catch (InterruptedException e)
	                {
	                    e.printStackTrace();
	                }
	                System.out.println("First task completed");
	            }
	        });
	        Thread t1 = new Thread(new Runnable()
	        {
	            public void run()
	            {
	                System.out.println("Second task completed");
	            }
	        });
	   

	        
	        t.start(); // Line 15
	        t.join(); // Line 16
	        t1.start();
	        System.out.println("主线程");
	        // join 是等待线程，阻塞其他线程，也就是线程同步， join()方法还有带超时限制的重载版本。 例如t.join(5000);则让线程等待5000毫秒，如果超过这个时间，则停止等待，变为可运行状态。
	    }

}
