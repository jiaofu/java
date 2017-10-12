package interrupt;

class Example1 extends Thread {
    public static void main(String args[]) throws Exception {
        Example1 thread = new Example1();
        System.out.println("Starting thread...");
        thread.start();
        Thread.sleep(3000);
        System.out.println("Asking thread to stop...");
        // �����ж�����
        thread.interrupt();
        Thread.sleep(3000);
        System.out.println("Stopping application...");
    }

    public void run() {
        // ÿ��һ�����Ƿ��������жϱ�ʾ
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("Thread is running...");
            long time = System.currentTimeMillis();
            // ʹ��whileѭ��ģ�� sleep
            while ((System.currentTimeMillis() - time < 1000) ) {
            }
        }
        System.out.println("Thread exiting under request...");
    }
}
//
//��ȻExample2�÷���Ҫ��һЩ���룬��������ʵ�֡�ͬʱ���������̻߳�����б�Ҫ����������������ע��һ������轫������������volatile ���ͻ򽫶�����һ�з��ʷ���ͬ���Ŀ�/������synchronized blocks/methods���С��������ж�һ��������״̬���̵߳ĳ������������ԷǼ��isInterrupted()����������: