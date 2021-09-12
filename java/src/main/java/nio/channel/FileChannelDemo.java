package nio.channel;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelDemo {

    /**
     * FileChannel读取数据到buffer中
     */
    @Test
    public void fileChannelRead() throws Exception {
        //创建FileChannel
        RandomAccessFile aFile = new RandomAccessFile("./file/nio/fileChannelRead.txt", "rw");
        FileChannel channel = aFile.getChannel();
        //创建Buffer
        ByteBuffer buf = ByteBuffer.allocate(1024);
        //读取数据到buffer中
        int bytesRead = channel.read(buf);
        while (bytesRead != -1) {
            System.out.println("读取了：" + bytesRead);
            buf.flip();
            while (buf.hasRemaining()) {
                System.out.println((char) buf.get());
            }
            buf.clear();
            bytesRead = channel.read(buf);
        }
        aFile.close();
        System.out.println("结束了");
    }


    /**
     * FileChannel写数据到buffer中
     */
    @Test
    public void fileChannelWrite() throws Exception {
        // 打开FileChannel
        RandomAccessFile aFile = new RandomAccessFile("./file/nio/fileChannelWrite.txt", "rw");
        FileChannel channel = aFile.getChannel();
        //创建buffer对象
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        String newData = "你是个好人123abc";
        buffer.clear();
        //写入内容
        buffer.put(newData.getBytes());
        buffer.flip();
        //FileChannel完成最终实现
        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
        //关闭
        channel.close();
    }


    @Test
    public void channelTransfer() throws Exception {
        // 创建两个fileChannel
        RandomAccessFile fromFile = new RandomAccessFile("./file/nio/fromFile.txt", "rw");
        FileChannel fromChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile("./file/nio/toFile.txt", "rw");
        FileChannel toChannel = toFile.getChannel();

        long position = 0;
        long size = fromChannel.size();
        fromChannel.transferTo(position, size, toChannel);
        //toChannel.transferFrom(fromChannel, position, size);

        fromFile.close();
        toFile.close();
        System.out.println("over!");
    }


}
