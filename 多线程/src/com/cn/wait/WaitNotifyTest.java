package com.cn.wait;

public class WaitNotifyTest {

    // �ڶ��̼߳乲��Ķ�����ʹ��wait
    private String[] shareObj = { "true" };

    public static void main(String[] args) {
        WaitNotifyTest test = new WaitNotifyTest();
        ThreadWait threadWait1 = test.new ThreadWait("wait thread1");
        threadWait1.setPriority(2);
        ThreadWait threadWait2 = test.new ThreadWait("wait thread2");
        threadWait2.setPriority(3);
        ThreadWait threadWait3 = test.new ThreadWait("wait thread3");
        threadWait3.setPriority(4);

        ThreadNotify threadNotify = test.new ThreadNotify("notify thread");

        threadNotify.start();
        threadWait1.start();
        threadWait2.start();
        threadWait3.start();
    }

    class ThreadWait extends Thread {

        public ThreadWait(String name){
            super(name);
        }

        public void run() {
            synchronized (shareObj) {
                while ("true".equals(shareObj[0])) {
                    System.out.println("�߳�"+ this.getName() + "��ʼ�ȴ�");
                    long startTime = System.currentTimeMillis();
                    try {
                        shareObj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    long endTime = System.currentTimeMillis();
                    System.out.println("�߳�" + this.getName() 
                            + "�ȴ�ʱ��Ϊ��" + (endTime - startTime));
                }
            }
            System.out.println("�߳�" + getName() + "�ȴ�����");
        }
    }

    class ThreadNotify extends Thread {

        public ThreadNotify(String name){
            super(name);
        }


        public void run() {
            try {
                // ���ȴ��̵߳ȴ�ʱ��
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (shareObj) {
                System.out.println("�߳�" + this.getName() + "��ʼ׼��֪ͨ");
                shareObj[0] = "false";
                shareObj.notifyAll();
                System.out.println("�߳�" + this.getName() + "֪ͨ����");
                try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            System.out.println("�߳�" + this.getName() + "���н���");
        }
    }
}

//���� wait() ��  notify/notifyAll() �Ƿ���ͬ��������еģ�����߳���ִ������ʱ���϶��ǽ������ٽ����еģ������߳̿϶��ǻ�������ġ�
//
//���߳�ִ��wait()ʱ����ѵ�ǰ�����ͷţ�Ȼ���ó�CPU������ȴ�״̬��
//
// ��ִ��notify/notifyAll����ʱ���ỽ��һ�����ڵȴ��� ������ ���̣߳�Ȼ���������ִ�У�ֱ��ִ�����˳���������ס������synchronized���εĴ���飩�����ͷ�����
//���߳��в���ĳ�������ı仯�� if ������ while����Ϊ�����ж���߳�await�����һ��notifyAll��ȫ�����ѣ���Ҫ���¾������ȵõ�ʱ��Ƭ���߳����������ˣ������߳�����Ҫ�ص�await�ϡ��������while������if�����е��̶߳�������ȥ��