package IO.java;

import org.junit.Test;

import java.io.*;

/**
 * https://blog.csdn.net/zhaoyanjun6/article/details/54292148
 * <p>
 * <p>
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
 *
 * @author shkstart
 * @create 2019 下午 2:44
 */
public class BufferedTest {

    @Test
    public void BufferedStreamTest() {
        String srcPath = "./file/io/爱情与友情.jpg";
        String destPath = "./file/io/爱情与友情2.jpg";
        BufferedStreamCopy(srcPath, destPath);
    }

    public void BufferedStreamCopy(String srcPath, String destPath) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(new FileInputStream((new File(srcPath))));
            bos = new BufferedOutputStream(new FileOutputStream(new File(destPath)));
            int len;
            byte[] buffer = new byte[10];
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


    /*
    使用BufferedReader和BufferedWriter实现文本文件的复制

     */
    @Test
    public void testBufferedReaderBufferedWriter() {
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            //创建文件和相应的流
            br = new BufferedReader(new FileReader(new File("dbcp.txt")));
            bw = new BufferedWriter(new FileWriter(new File("dbcp1.txt")));
            //读写操作
            //方式一：使用char[]数组
//            char[] cbuf = new char[1024];
//            int len;
//            while((len = br.read(cbuf)) != -1){
//                bw.write(cbuf,0,len);
//                bw.flush();
//            }
            //方式二：使用String
            String data;
            while ((data = br.readLine()) != null) {
                //方法一：
//                bw.write(data + "\n");//data中不包含换行符
                //方法二：
                bw.write(data);//data中不包含换行符
                bw.newLine();//提供换行的操作
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
