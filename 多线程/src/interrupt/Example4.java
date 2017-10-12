package interrupt;

class Example4 extends Thread {
    public static void main(String args[]) throws Exception {
        final Object lock1 = new Object();
        final Object lock2 = new Object();
        Thread thread1 = new Thread() {
            public void run() {
                deathLock(lock1, lock2);
            }
        };
        Thread thread2 = new Thread() {
            public void run() {
                // ע�⣬�����ڽ�����һ��λ��
                deathLock(lock2, lock1);
            }
        };
        System.out.println("Starting thread...");
        thread1.start();
        thread2.start();
        Thread.sleep(3000);
        System.out.println("Interrupting thread...");
        thread1.interrupt();
        thread2.interrupt();
        Thread.sleep(3000);
        System.out.println("Stopping application...");
    }

    static void deathLock(Object lock1, Object lock2) {
        try {
            synchronized (lock1) {
                Thread.sleep(10);// ��������������
                synchronized (lock2) {// �����������Ȼ�����ˣ����������쳣
                    System.out.println(Thread.currentThread());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
//Example4����ȥ�жϴ�������״̬�������̣߳����������߶�û���յ��κ��ж��źţ��׳��쳣��������interrupt()�����ǲ����ж������̵߳ģ���Ϊ������λ�ø����޷��׳��쳣��