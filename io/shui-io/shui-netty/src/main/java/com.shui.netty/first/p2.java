package first;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class p2 {
    public static void main(String[] args) {

    }
    //客户端采用NIO实现
    public static void client(){
        ByteBuffer buffer=ByteBuffer.allocate(1024);
        SocketChannel socketChannel=null;
        try{
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("10.10.195.115",8080));
            if(socketChannel.finishConnect()){
                int i=0;
                while (true){
                    TimeUnit.SECONDS.sleep(1);
                    String info = "I'm "+i+++"-th information from client";
                    buffer.clear();
                    buffer.put(info.getBytes());
                    buffer.flip();
                    while (buffer.hasRemaining()){
                        System.out.println(buffer);
                        socketChannel.write(buffer);
                    }

                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try{
                if(socketChannel!=null){
                    socketChannel.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    //客户端采用IO实现
    public static  void server(){
        ServerSocket  serverSocket= null;
        InputStream in =null;
        try{
            serverSocket = new ServerSocket(8080);
            int recvMsgSize=0;
            byte[] recvBuf=new byte[1024];
            while (true){
                Socket clntSocket=serverSocket.accept();
                SocketAddress clientAddress=clntSocket.getRemoteSocketAddress();
                System.out.println("Handling client at "+clientAddress);
                in = clntSocket.getInputStream();
                while((recvMsgSize=in.read(recvBuf))!=-1){
                    byte[] temp =new byte[recvMsgSize];
                    System.arraycopy(recvBuf,0,temp,0,recvMsgSize);
                    System.out.println(new String(temp));

                }
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }  finally{
            try{
                if(serverSocket!=null){
                    serverSocket.close();
                }
                if(in!=null){
                    in.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }


    }

}
//将上面的TCP服务端代码改写成NIO的方式
 class ServerConnect{
    private static final int BUF_SIZE=1024;
    private static final int PORT = 8080;
    private static final int TIMEOUT = 3000;

    public static void main(String[] args) {
        selector();
    }
    public static void selector() {
        Selector selector=null;
        ServerSocketChannel ssc=null;
        try{
            selector=Selector.open();
            ssc=ServerSocketChannel.open();
            ssc.socket().bind(new InetSocketAddress(PORT));
            ssc.configureBlocking(false);
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            while (true){
                if(selector.select(TIMEOUT)==0){
                    System.out.println("==");
                    continue;
                }
                Iterator<SelectionKey> iter=selector.selectedKeys().iterator();
                while (iter.hasNext()){
                    SelectionKey key=iter.next();
                    if(key.isAcceptable()){
                        handleAccept(key);
                    }
                    if(key.isReadable()){
                        handleRead(key);
                    }
                    if(key.isWritable() && key.isValid()){
                        handleWrite(key);
                    }
                    if(key.isConnectable()){
                        System.out.println("isConnectable = true");
                    }
                    iter.remove();

                }
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try{
                if(selector !=null){
                    selector.close();
                }
                if(ssc !=null){
                    ssc.close();
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

    }

    private static void handleRead(SelectionKey key) throws  Exception{
        SocketChannel sc=(SocketChannel)key.channel();
        ByteBuffer buf=(ByteBuffer)key.attachment();
        long bytesRead=sc.read(buf);
        while (bytesRead>0){
            buf.flip();
            while (buf.hasRemaining()){
                System.out.println((char)buf.get());
            }
            System.out.println();
            buf.clear();
            bytesRead=sc.read(buf);
        }
        if(bytesRead == -1){
            sc.close();
        }
    }

    private static void handleAccept(SelectionKey key)throws  Exception {
        ServerSocketChannel ssChannel=(ServerSocketChannel)key.channel();
        SocketChannel sc = ssChannel.accept();
        sc.configureBlocking(false);
        sc.register(key.selector(), SelectionKey.OP_READ,ByteBuffer.allocateDirect(BUF_SIZE));

    }
    private static void handleWrite(SelectionKey key) throws  Exception{
        ByteBuffer buf = (ByteBuffer)key.attachment();
        buf.flip();
        SocketChannel sc = (SocketChannel) key.channel();
        while(buf.hasRemaining()){
            sc.write(buf);
        }
        buf.compact();
    }



}

