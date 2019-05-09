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
	        System.out.println("���߳�");
	        // join �ǵȴ��̣߳����������̣߳�Ҳ�����߳�ͬ���� join()�������д���ʱ���Ƶ����ذ汾�� ����t.join(5000);�����̵߳ȴ�5000���룬����������ʱ�䣬��ֹͣ�ȴ�����Ϊ������״̬��
	    }

}
