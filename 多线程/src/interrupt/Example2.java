package interrupt;

public class Example2  extends Thread {
	volatile boolean stop = false;// �߳��ж��ź���

    public static void main(String args[]) throws Exception {
        Example2 thread = new Example2();
        System.out.println("Starting thread...");
        thread.start();
        Thread.sleep(3000);
        System.out.println("Asking thread to stop...");
        // �����ж��ź���
        thread.stop = true;
        Thread.sleep(3000);
        System.out.println("Stopping application...");
    }

    public void run() {
        // ÿ��һ����һ���ж��ź���
        while (!stop) {
            System.out.println("Thread is running...");
            long time = System.currentTimeMillis();
            /*
             * ʹ��whileѭ��ģ�� sleep ���������ﲻҪʹ��sleep������������ʱ�� ��
             * InterruptedException�쳣���˳�ѭ��������while���stop�����Ͳ���ִ�У�
             * ʧȥ�����塣
             */
            while ((System.currentTimeMillis() - time < 1000)) {}
        }
        System.out.println("Thread exiting under request...");
    }

}
//ʹ���ж��ź����жϷ�����״̬���߳�
//
//�ж��߳���õģ������Ƽ��ķ�ʽ�ǣ�ʹ�ù��������shared variable�������źţ������̱߳���ֹͣ�������е������̱߳��������Եĺ˲���һ������Ȼ�����������ֹ����Example2��������һ��ʽ��
