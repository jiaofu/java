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
        Thread.currentThread().interrupt();// 再调用interrupt方法
        thread.socket.close();// 再调用close方法
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
                Thread.currentThread().interrupt();//重新设置中断标示位
            }
        }
        System.out.println("Thread exiting under request...");
    }

}

//中断I/O操作
//
//然而，如果线程在I/O操作进行时被阻塞，又会如何？I/O操作可以阻塞线程一段相当长的时间，特别是牵扯到网络应用时。例如，服务器可能需要等待一个请求（request），又或者，一个网络应用程序可能要等待远端主机的响应。
//
// 
//
//实现此InterruptibleChannel接口的通道是可中断的：如果某个线程在可中断通道上因调用某个阻塞的 I/O 操作（常见的操作一般有这些：serverSocketChannel. accept()、socketChannel.connect、socketChannel.open、socketChannel.read、socketChannel.write、fileChannel.read、fileChannel.write）而进入阻塞状态，而另一个线程又调用了该阻塞线程的 interrupt 方法，这将导致该通道被关闭，并且已阻塞线程接将会收到ClosedByInterruptException，并且设置已阻塞线程的中断状态。另外，如果已设置某个线程的中断状态并且它在通道上调用某个阻塞的 I/O 操作，则该通道将关闭并且该线程立即接收到 ClosedByInterruptException；并仍然设置其中断状态。如果情况是这样，其代码的逻辑和第三个例子中的是一样的，只是异常不同而已。
//
// 
//
//如果你正使用通道（channels）（这是在Java 1.4中引入的新的I/O API），那么被阻塞的线程将收到一个ClosedByInterruptException异常。但是，你可能正使用Java1.0之前就存在的传统的I/O，而且要求更多的工作。既然这样，Thread.interrupt()将不起作用，因为线程将不会退出被阻塞状态。Example5描述了这一行为。尽管interrupt()被调用，线程也不会退出被阻塞状态，比如ServerSocket的accept方法根本不抛出异常。
//
// 
//
//很幸运，Java平台为这种情形提供了一项解决方案，即调用阻塞该线程的套接字的close()方法。在这种情形下，如果线程被I/O操作阻塞，当调用该套接字的close方法时，该线程在调用accept地方法将接收到一个SocketException（SocketException为IOException的子异常）异常，这与使用interrupt()方法引起一个InterruptedException异常被抛出非常相似，（注，如果是流因读写阻塞后，调用流的close方法也会被阻塞，根本不能调用，更不会抛IOExcepiton，此种情况下怎样中断？我想可以转换为通道来操作流可以解决，比如文件通道）。下面是具体实现：
