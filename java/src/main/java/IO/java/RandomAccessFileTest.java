package IO.java;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/*
r:以只读的模式打开，如果调用write方法将会抛出IO异常
rw:以读和写的模式打开
rws:以读和写的模式打开，要求对”文件的内容“和”元数据“的每个更新都同步到存储设备
rwd:以读和写的模式打开，要求对”文件的内容“的每个更新都同步到存储设备
 */


public class RandomAccessFileTest {


    @Test
    public void testCopy() {

        try (FileInputStream inputStream = new FileInputStream("./file/io/爱情与友情.jpg");
             RandomAccessFile randomAccessFile = new RandomAccessFile("./file/io/爱情与友情copy.jpg", "rw")) {

            randomAccessFile.seek(0);
            int len;
            byte[] buffer = new byte[1024 * 1024];

            while ((len = inputStream.read(buffer)) != -1) {
                randomAccessFile.write(buffer, 0, len);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testCopy2() throws IOException {
        RandomAccessFile raf1 = new RandomAccessFile("", "r");
        RandomAccessFile raf2 = new RandomAccessFile("", "rw");
        byte[] bytes = new byte[1024];
        int len;
        while ((len = raf1.read(bytes)) != -1) {
            raf2.write(bytes, 0, len);
        }

        raf1.close();
        raf2.close();
    }

    @Test
    public void testSeek() throws IOException {
        RandomAccessFile raf1 = new RandomAccessFile("", "rw");
        String appendStr = "appendStr";
        raf1.seek(5);
        raf1.write(appendStr.getBytes());
        raf1.close();
    }


}
