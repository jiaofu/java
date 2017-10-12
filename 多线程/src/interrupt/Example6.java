package interrupt;

import java.io.IOException;
import java.net.ServerSocket;

public class Example6 extends Thread {

	volatile ServerSocket socket;

    public static void main(String args[]) throws Exception {
        Example6 thread = new Example6();
        System.out.println("Starting thread...");
        thread.start();
        Thread.sleep(3000);
        System.out.println("Asking thread to stop...");
        Thread.currentThread().interrupt();// �ٵ���interrupt����
        thread.socket.close();// �ٵ���close����
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
        System.out.println("Stopping application...");
    }

    public void run() {
        try {
            socket = new ServerSocket(8888);
        } catch (IOException e) {
            System.out.println("Could not create the socket...");
            return;
        }
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("Waiting for connection...");
            try {
                socket.accept();
            } catch (IOException e) {
                System.out.println("accept() failed or interrupted...");
                Thread.currentThread().interrupt();//���������жϱ�ʾλ
            }
        }
        System.out.println("Thread exiting under request...");
    }

}

//�ж�I/O����
//
//Ȼ��������߳���I/O��������ʱ���������ֻ���Σ�I/O�������������߳�һ���൱����ʱ�䣬�ر���ǣ��������Ӧ��ʱ�����磬������������Ҫ�ȴ�һ������request�����ֻ��ߣ�һ������Ӧ�ó������Ҫ�ȴ�Զ����������Ӧ��
//
// 
//
//ʵ�ִ�InterruptibleChannel�ӿڵ�ͨ���ǿ��жϵģ����ĳ���߳��ڿ��ж�ͨ���������ĳ�������� I/O �����������Ĳ���һ������Щ��serverSocketChannel. accept()��socketChannel.connect��socketChannel.open��socketChannel.read��socketChannel.write��fileChannel.read��fileChannel.write������������״̬������һ���߳��ֵ����˸������̵߳� interrupt �������⽫���¸�ͨ�����رգ������������߳̽ӽ����յ�ClosedByInterruptException�����������������̵߳��ж�״̬�����⣬���������ĳ���̵߳��ж�״̬��������ͨ���ϵ���ĳ�������� I/O ���������ͨ�����رղ��Ҹ��߳��������յ� ClosedByInterruptException������Ȼ�������ж�״̬�����������������������߼��͵����������е���һ���ģ�ֻ���쳣��ͬ���ѡ�
//
// 
//
//�������ʹ��ͨ����channels����������Java 1.4��������µ�I/O API������ô���������߳̽��յ�һ��ClosedByInterruptException�쳣�����ǣ��������ʹ��Java1.0֮ǰ�ʹ��ڵĴ�ͳ��I/O������Ҫ�����Ĺ�������Ȼ������Thread.interrupt()���������ã���Ϊ�߳̽������˳�������״̬��Example5��������һ��Ϊ������interrupt()�����ã��߳�Ҳ�����˳�������״̬������ServerSocket��accept�����������׳��쳣��
//
// 
//
//�����ˣ�Javaƽ̨Ϊ���������ṩ��һ�����������������������̵߳��׽��ֵ�close()�����������������£�����̱߳�I/O���������������ø��׽��ֵ�close����ʱ�����߳��ڵ���accept�ط��������յ�һ��SocketException��SocketExceptionΪIOException�����쳣���쳣������ʹ��interrupt()��������һ��InterruptedException�쳣���׳��ǳ����ƣ���ע������������д�����󣬵�������close����Ҳ�ᱻ�������������ܵ��ã���������IOExcepiton����������������жϣ��������ת��Ϊͨ�������������Խ���������ļ�ͨ�����������Ǿ���ʵ�֣�
