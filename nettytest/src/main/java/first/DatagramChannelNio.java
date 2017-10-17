package first;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class DatagramChannelNio {
    public static void main(String[] args) {

    }
    public static  void receive(){
        DatagramChannel channel=null;
        try{
            channel=DatagramChannel.open();
            channel.socket().bind(new InetSocketAddress(8888));
            ByteBuffer buf=ByteBuffer.allocate(1024);
            buf.clear();
            channel.receive(buf);
            buf.flip();
            while (buf.hasRemaining()){
                System.out.print((char)buf.get());
            }
            System.out.println();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                if(channel!=null){
                    channel.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    public static  void  send(){
        DatagramChannel channel = null;
        try{
            channel = DatagramChannel.open();
            String info = "I'm the Sender!";
            ByteBuffer buf=ByteBuffer.allocate(1024);
            buf.clear();
            buf.put(info.getBytes());
            buf.flip();
            int bytesSent = channel.send(buf, new InetSocketAddress("10.10.195.115",8888));
            System.out.println(bytesSent);
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try{
                if(channel!=null){
                    channel.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
