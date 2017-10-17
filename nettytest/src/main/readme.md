#NIO
[TOC]
## Buffers(缓冲区)
>* 分配空间（ByteBuffer buf = ByteBuffer.allocate(1024); 还有一种allocateDirector后面再陈述）
>* 写入数据到Buffer(int bytesRead = fileChannel.read(buf);)
>* 调用filp()方法（ buf.flip();）
>* 从Buffer中读取数据（System.out.print((char)buf.get());）
>* 调用clear()方法或者compact()方法

### 向Buffer中写数据：
从Channel写到Buffer (fileChannel.read(buf))
通过Buffer的put()方法 （buf.put(…)）
###从Buffer中读取数据：
从Buffer读取到Channel (channel.write(buf))
使用get()方法从Buffer中读取数据 （buf.get()）
### 理解buffer
可以把Buffer简单地理解为一组基本数据类型的元素列表，它通过几个变量来保存这个数据的当前位置状态：capacity, position, limit, mark

|索引 |	说明|
|------   |-------|
|capacity|	缓冲区数组的总长度|
|position|	下一个要操作的数据元素的位置|
|limit	|缓冲区数组中不可操作的下一个元素的位置：limit<=capacity|
|mark|	用于记录当前position的前一个位置或者默认是0|
![](http://img.blog.csdn.net/20160519212348550)
无图无真相，举例：我们通过ByteBuffer.allocate(11)方法创建了一个11个byte的数组的缓冲区，初始状态如上图，position的位置为0，capacity和limit默认都是数组长度。当我们写入5个字节时，变化如下图：
这里写图片描述
![](http://img.blog.csdn.net/20160519212417971)
这时我们需要将缓冲区中的5个字节数据写入Channel的通信信道，所以我们调用ByteBuffer.flip()方法，变化如下图所示(position设回0，并将limit设成之前的position的值)：
这里写图片描述
![](http://img.blog.csdn.net/20160519212431988)
这时底层操作系统就可以从缓冲区中正确读取这个5个字节数据并发送出去了。在下一次写数据之前我们再调用clear()方法，缓冲区的索引位置又回到了初始位置。

调用clear()方法：position将被设回0，limit设置成capacity，换句话说，Buffer被清空了，其实Buffer中的数据并未被清楚，只是这些标记告诉我们可以从哪里开始往Buffer里写数据。如果Buffer中有一些未读的数据，调用clear()方法，数据将“被遗忘”，意味着不再有任何标记会告诉你哪些数据被读过，哪些还没有。如果Buffer中仍有未读的数据，且后续还需要这些数据，但是此时想要先先写些数据，那么使用compact()方法。compact()方法将所有未读的数据拷贝到Buffer起始处。然后将position设到最后一个未读元素正后面。limit属性依然像clear()方法一样，设置成capacity。现在Buffer准备好写数据了，但是不会覆盖未读的数据。

通过调用Buffer.mark()方法，可以标记Buffer中的一个特定的position，之后可以通过调用Buffer.reset()方法恢复到这个position。Buffer.rewind()方法将position设回0，所以你可以重读Buffer中的所有数据。limit保持不变，仍然表示能从Buffer中读取多少个元素。


## Channels (通道)
### FileChannel
>* 读取数据
```java
 aFile = new RandomAccessFile("src/nio.txt","rw");
            FileChannel fileChannel = aFile.getChannel();
            ByteBuffer buf = ByteBuffer.allocate(1024);
 
            int bytesRead = fileChannel.read(buf);
```
### DatagramChannel
>* 接受数据
```java
 channel = DatagramChannel.open();
           channel.socket().bind(new InetSocketAddress(8888));
           ByteBuffer buf = ByteBuffer.allocate(1024);
           buf.clear();
           channel.receive(buf);
 
           buf.flip();
           while(buf.hasRemaining()){
               System.out.print((char)buf.get());
           }
           System.out.println();
 
```
>* 发送数据
```java
    channel = DatagramChannel.open();
           String info = "I'm the Sender!";
           ByteBuffer buf = ByteBuffer.allocate(1024);
           buf.clear();
           buf.put(info.getBytes());
           buf.flip();
           int bytesSent = channel.send(buf, new InetSocketAddress("10.10.195.115",8888));
           System.out.println(bytesSent);
```
### SocketChannel
>* 打开SocketChannel：
   socketChannel = SocketChannel.open();
 socketChannel.connect(new InetSocketAddress("10.10.195.115",8080));
>* 关闭：
 serverSocket.close();
>* 读取数据：
```java
    buffer.clear();
    buffer.put(info.getBytes());
    buffer.flip();
    while(buffer.hasRemaining()){
      System.out.println(buffer);
      socketChannel.write(buffer);
    }
```
### ServerSocketChannel
>* 打开ServerSocketChannel： 
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
>* 关闭ServerSocketChannel：
   serverSocketChannel.close();
>* 监听新进来的连接
while(true){
    SocketChannel socketChannel = serverSocketChannel.accept();
}
>* 在非阻塞模式下
ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9999));
        serverSocketChannel.configureBlocking(false);
```java
ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9999));
        serverSocketChannel.configureBlocking(false);
        while (true)
        {
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel != null)
            {
                // do something with socketChannel...
            }
        }
```

## Selectors（选择器）
### Selector的创建
#### 创建
```java
sc= ServerSocketChannel.open();
           ssc.socket().bind(new InetSocketAddress(PORT));
           ssc.configureBlocking(false);
           ssc.register(selector, SelectionKey.OP_ACCEPT);
```
与Selector一起使用时，Channel必须处于非阻塞模式下。这意味着不能将FileChannel与Selector一起使用，因为FileChannel不能切换到非阻塞模式。而套接字通道都可以
#### 二个参数
注意register()方法的第二个参数。这是一个“interest集合”，意思是在通过Selector监听Channel时对什么事件感兴趣。可以监听四种不同类型的事件
 >* Connect
 >* Accept
 >* Read
>*  Write
####  常量
这四种事件用SelectionKey的四个常量来表示
>*  SelectionKey.OP_CONNECT
>*  SelectionKey.OP_ACCEPT
>*  SelectionKey.OP_READ
>*  SelectionKey.OP_WRITE
### SelectionKey 对象
当向Selector注册Channel时，register()方法会返回一个SelectionKey对象。这个对象包含了一些你感兴趣的属性：
>* interest集合
 >* ready集合
 >* Channel
 >* Selector
>* 附加的对象（可选）

ready 集合是通道已经准备就绪的操作的集合。在一次选择(Selection)之后，你会首先访问这个ready set。Selection将在下一小节进行解释。可以这样访问ready集合：


int readySet = selectionKey.readyOps();
可以用像检测interest集合那样的方法，来检测channel中什么事件或操作已经就绪。但是，也可以使用以下四个方法，它们都会返回一个布尔类型：


 >* selectionKey.isAcceptable();
>*  selectionKey.isConnectable();
>*  selectionKey.isReadable();
>*  selectionKey.isWritable();

从SelectionKey访问Channel和Selector很简单。如下：
>* Channel  channel  = selectionKey.channel();
>* Selector selector = selectionKey.selector();

可以将一个对象或者更多信息附着到SelectionKey上，这样就能方便的识别某个给定的通道。例如，可以附加 与通道一起使用的Buffer，或是包含聚集数据的某个对象。使用方法如下：


selectionKey.attach(theObject);
Object attachedObj = selectionKey.attachment();
还可以在用register()方法向Selector注册Channel的时候附加对象。如：

>* SelectionKey key = channel.register(selector, SelectionKey.OP_READ, theObject);

### 通过Selector选择通道 
一旦向Selector注册了一或多个通道，就可以调用几个重载的select()方法。这些方法返回你所感兴趣的事件（如连接、接受、读或写）已经准备就绪的那些通道。换句话说，如果你对“读就绪”的通道感兴趣，select()方法会返回读事件已经就绪的那些通道。
下面是select()方法：

>* int select()
>* int select(long timeout)
>* int selectNow()

select()阻塞到至少有一个通道在你注册的事件上就绪了。
select(long timeout)和select()一样，除了最长会阻塞timeout毫秒(参数)。
selectNow()不会阻塞，不管什么通道就绪都立刻返回（译者注：此方法执行非阻塞的选择操作。如果自从前一次选择操作后，没有通道变成可选择的，则此方法直接返回零。）。

select()方法返回的int值表示有多少通道已经就绪。亦即，自上次调用select()方法后有多少通道变成就绪状态。如果调用select()方法，因为有一个通道变成就绪状态，返回了1，若再次调用select()方法，如果另一个通道就绪了，它会再次返回1。如果对第一个就绪的channel没有做任何操作，现在就有两个就绪的通道，但在每次select()方法调用之间，只有一个通道就绪了。

一旦调用了select()方法，并且返回值表明有一个或更多个通道就绪了，然后可以通过调用selector的selectedKeys()方法，访问“已选择键集（selected key set）”中的就绪通道。如下所示：


>* Set selectedKeys = selector.selectedKeys();
当像Selector注册Channel时，Channel.register()方法会返回一个SelectionKey 对象。这个对象代表了注册到该Selector的通道。可以通过SelectionKey的selectedKeySet()方法访问这些对象。

注意每次迭代末尾的keyIterator.remove()调用。Selector不会自己从已选择键集中移除SelectionKey实例。必须在处理完通道时自己移除。下次该通道变成就绪时，Selector会再次将其放入已选择键集中。

SelectionKey.channel()方法返回的通道需要转型成你要处理的类型，如ServerSocketChannel或SocketChannel等
 ###完整的示例 
```java
 Selector selector = Selector.open();  
channel.configureBlocking(false);  
SelectionKey key = channel.register(selector, SelectionKey.OP_READ);  
while(true) {  
  int readyChannels = selector.select();  
  if(readyChannels == 0) continue;  
  Set selectedKeys = selector.selectedKeys();  
  Iterator keyIterator = selectedKeys.iterator();  
  while(keyIterator.hasNext()) {  
    SelectionKey key = keyIterator.next();  
    if(key.isAcceptable()) {  
        // a connection was accepted by a ServerSocketChannel.  
    } else if (key.isConnectable()) {  
        // a connection was established with a remote server.  
    } else if (key.isReadable()) {  
        // a channel is ready for reading  
    } else if (key.isWritable()) {  
        // a channel is ready for writing  
    }  
  ; 
  }  
}  
```
 
## 其他
### 大文件读写
JAVA处理大文件，一般用BufferedReader,BufferedInputStream这类带缓冲的IO类，不过如果文件超大的话，更快的方式是采用MappedByteBuffer。
### transferFrom & transferTo
FileChannel的transferFrom()方法可以将数据从源通道传输到FileChannel中。
transferTo()方法将数据从FileChannel传输到其他的channel中。
###Pipe
Java NIO 管道是2个线程之间的单向数据连接。Pipe有一个source通道和一个sink通道。数据会被写到sink通道，从source通道读取。







