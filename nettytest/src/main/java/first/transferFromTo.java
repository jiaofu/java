package first;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class transferFromTo {
    public static void method1(){
        RandomAccessFile fromFile = null;
        RandomAccessFile toFile = null;
        try
        {
            fromFile = new RandomAccessFile("src/fromFile.xml","rw");
            FileChannel fromChannel = fromFile.getChannel();
            toFile = new RandomAccessFile("src/toFile.txt","rw");
            FileChannel toChannel = toFile.getChannel();

            long position = 0;
            long count = fromChannel.size();
            System.out.println(count);
            toChannel.transferFrom(fromChannel, position, count);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally{
            try{
                if(fromFile != null){
                    fromFile.close();
                }
                if(toFile != null){
                    toFile.close();
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    public static void method2()
    {
        RandomAccessFile fromFile = null;
        RandomAccessFile toFile = null;
        try
        {
            fromFile = new RandomAccessFile("src/fromFile.txt","rw");
            FileChannel fromChannel = fromFile.getChannel();
            toFile = new RandomAccessFile("src/toFile.txt","rw");
            FileChannel toChannel = toFile.getChannel();

            long position = 0;
            long count = fromChannel.size();
            System.out.println(count);
            fromChannel.transferTo(position, count,toChannel);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally{
            try{
                if(fromFile != null){
                    fromFile.close();
                }
                if(toFile != null){
                    toFile.close();
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
