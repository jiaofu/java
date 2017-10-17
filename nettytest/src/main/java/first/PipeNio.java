package first;

import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.util.concurrent.*;

public class PipeNio {
    public static  void method1(){
        final Pipe pipeTemp = null;
        ExecutorService exec=    new ThreadPoolExecutor(2,2,5000L,TimeUnit.SECONDS,null);
        exec.submit(new Callable<Object>() {

            @Override
            public Object call() throws Exception {
                //向通道中写数据
                Pipe.SinkChannel sinkChannel= pipeTemp.sink();
                while (true){
                    TimeUnit.SECONDS.sleep(1);
                    String newData = "Pipe Test At Time "+System.currentTimeMillis();
                    ByteBuffer buf=ByteBuffer.allocate(1024);
                    buf.clear();
                    buf.put(newData.getBytes());
                    buf.flip();
                    while (buf.hasRemaining()){
                        System.out.println(buf);
                        sinkChannel.write(buf);
                    }
                }
            }
        });

        exec.submit(new Callable<Object>() {

            @Override
            public Object call() throws Exception {
                Pipe.SourceChannel sourceChannel =pipeTemp.source();// 向通道读取数据
                while (true){
                    TimeUnit.SECONDS.sleep(1);
                    ByteBuffer buf= ByteBuffer.allocate(1024);
                    buf.clear();
                    int bytesRead =sourceChannel.read(buf);
                    System.out.println("bytesRead="+bytesRead);
                    while (bytesRead>0){
                        buf.flip();
                        byte b[] =new byte[bytesRead];
                        int i=0;
                        while (buf.hasRemaining()){
                            b[i] =buf.get();
                            System.out.printf("%X",b[i]);
                            i++;
                        }
                        String s =new String(b);
                        System.out.println("=================||"+s);
                        bytesRead = sourceChannel.read(buf);
                    }
                }
            }
        });


    }
}
