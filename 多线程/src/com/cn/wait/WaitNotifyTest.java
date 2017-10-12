package com.cn.wait;

public class WaitNotifyTest {

    // 在多线程间共享的对象上使用wait
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
                    System.out.println("线程"+ this.getName() + "开始等待");
                    long startTime = System.currentTimeMillis();
                    try {
                        shareObj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    long endTime = System.currentTimeMillis();
                    System.out.println("线程" + this.getName() 
                            + "等待时间为：" + (endTime - startTime));
                }
            }
            System.out.println("线程" + getName() + "等待结束");
        }
    }

    class ThreadNotify extends Thread {

        public ThreadNotify(String name){
            super(name);
        }


        public void run() {
            try {
                // 给等待线程等待时间
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (shareObj) {
                System.out.println("线程" + this.getName() + "开始准备通知");
                shareObj[0] = "false";
                shareObj.notifyAll();
                System.out.println("线程" + this.getName() + "通知结束");
                try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            System.out.println("线程" + this.getName() + "运行结束");
        }
    }
}

//由于 wait() 与  notify/notifyAll() 是放在同步代码块中的，因此线程在执行它们时，肯定是进入了临界区中的，即该线程肯定是获得了锁的。
//
//当线程执行wait()时，会把当前的锁释放，然后让出CPU，进入等待状态。
//
// 当执行notify/notifyAll方法时，会唤醒一个处于等待该 对象锁 的线程，然后继续往下执行，直到执行完退出对象锁锁住的区域（synchronized修饰的代码块）后再释放锁。
//多线程中测试某个条件的变化用 if 还是用 while？因为可能有多个线程await在这里，一个notifyAll，全部唤醒，又要重新竞争，先得到时间片的线程向下运行了，其他线程又需要回到await上。如果不是while，而是if，所有的线程都会走下去了