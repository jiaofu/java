package interrupt;

class Example1 extends Thread {
    public static void main(String args[]) throws Exception {
        Example1 thread = new Example1();
        System.out.println("Starting thread...");
        thread.start();
        Thread.sleep(3000);
        System.out.println("Asking thread to stop...");
        // 发出中断请求
        thread.interrupt();
        Thread.sleep(3000);
        System.out.println("Stopping application...");
    }

    public void run() {
        // 每隔一秒检测是否设置了中断标示
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("Thread is running...");
            long time = System.currentTimeMillis();
            // 使用while循环模拟 sleep
            while ((System.currentTimeMillis() - time < 1000) ) {
            }
        }
        System.out.println("Thread exiting under request...");
    }
}
//
//虽然Example2该方法要求一些编码，但并不难实现。同时，它给予线程机会进行必要的清理工作。这里需注意一点的是需将共享变量定义成volatile 类型或将对它的一切访问封入同步的块/方法（synchronized blocks/methods）中。上面是中断一个非阻塞状态的线程的常见做法，但对非检测isInterrupted()条件会更简洁: