package interrupt;

class Example3 extends Thread {
    public static void main(String args[]) throws Exception {
        Example3 thread = new Example3();
        System.out.println("Starting thread...");
        thread.start();
        Thread.sleep(3000);
        System.out.println("Asking thread to stop...");
        thread.interrupt();// ���ж��ź������ú��ٵ���
        Thread.sleep(3000);
        System.out.println("Stopping application...");
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("Thread running...");
            try {
                /*
                 * ����߳�������������ȥ����ж��ź���stop�������� ��thread.interrupt()
                 * ��ʹ�����̴߳������ĵط��׳��쳣���������̴߳�����״̬�����������
                 * �����쳣����� ��Ӧ�Ĵ���
                 */
                Thread.sleep(1000);// �߳�����������߳��յ��жϲ����źŽ��׳��쳣
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted...");
                /*
                 * ����߳��ڵ��� Object.wait()���������߸���� join() ��sleep()����
                 * ���������裬�����ж�״̬�������
                 */
                System.out.println(this.isInterrupted());// false

                //�в��ж����Լ������������Ҫ�����ж��̣߳�����Ҫ���������ж�λ�����
                //����Ҫ�����õ���
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Thread exiting under request...");
    }
}

//ʹ��thread.interrupt()�ж�����״̬�߳�
//
//Thread.interrupt()���������ж�һ���������е��̡߳���һ����ʵ������ɵ��ǣ������̵߳��жϱ�ʾλ�����߳��ܵ������ĵط��������sleep��wait��join�ȵط����׳�һ���쳣InterruptedException�������ж�״̬Ҳ��������������߳̾͵����˳�������״̬�������Ǿ���ʵ�֣�