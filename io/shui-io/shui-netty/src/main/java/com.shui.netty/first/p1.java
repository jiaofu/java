package com.shui.netty.first;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class p1 {
    public static void main(String[] args) {

    }

    //案例1是采用FileInputStream读取文件内容的：
    public static void method1(){
        RandomAccessFile aFile=null;
        try{
            aFile = new RandomAccessFile("src/nio.txt","rw");
            FileChannel fileChannel =aFile.getChannel();
            ByteBuffer buf=ByteBuffer.allocate(1024);
            int bytesRead =fileChannel.read(buf);
            System.out.println(bytesRead);
            while (bytesRead != -1){
                buf.flip();
                while (buf.hasRemaining()){
                    System.out.println((char)buf.get());
                }
                buf.compact();
                bytesRead=fileChannel.read(buf);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                if(aFile != null){
                    aFile.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    //对应的NIO（这里通过RandomAccessFile进行操作，当然也可以通过FileInputStream.getChannel()进行操作）：
    public static  void methid2(){
        InputStream in =null;
        try{
            in=new BufferedInputStream(new FileInputStream("src/nomal_io.txt"));
            byte [] buf=new byte[1024];
            int bytesRead=in.read(buf);
            while (bytesRead != -1){
                for(int i=0;i<bytesRead;i++){
                    System.out.print((char)buf[i]);
                }
                bytesRead = in.read(buf);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(in != null){
                    in.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
            }
        }
    }

