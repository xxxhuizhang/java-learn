package IO.java;

import org.junit.Test;

import java.io.*;

/**
 * 测试FileInputStream和FileOutputStream的使用
 * <p>
 * 结论：
 * 1. 对于文本文件(.txt,.java,.c,.cpp)，使用字符流处理
 * 2. 对于非文本文件(.jpg,.mp3,.mp4,.avi,.doc,.ppt,...)，使用字节流处理
 * 处理流之一：缓冲流的使用
 * <p>
 * 1.缓冲流：
 * BufferedInputStream
 * BufferedOutputStream
 * BufferedReader
 * BufferedWriter
 * <p>
 * 2.作用：提供流的读取、写入的速度
 * 提高读写速度的原因：内部提供了一个缓冲区
 * <p>
 * 3. 处理流，就是“套接”在已有的流的基础上。
 */

public class FileInputOutputStreamTest {

    @Test
    public void FileInputStreamToString() {
        String str = null;
        try {
            FileInputStream inputStream = new FileInputStream("./file/io/data.txt");
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            str = new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCopyFile() {
        long start = System.currentTimeMillis();
        String srcPath = "./file/io/爱情与友情.jpg";
        String destPath = "./file/io/爱情与友情2.jpg";
        bufferedStreamCopy(srcPath, destPath);
        long end = System.currentTimeMillis();
        System.out.println("复制操作花费的时间为：" + (end - start));//618
    }

    public void bufferedStreamCopy(String srcPath, String destPath) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(new FileInputStream((srcPath)));
            bos = new BufferedOutputStream(new FileOutputStream(new File(destPath)));
            int len;
            byte[] buffer = new byte[1024];
            while ((len = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
//                bos.flush();//刷新缓冲区
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //说明：关闭外层流的同时，内层流也会自动的进行关闭.
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
