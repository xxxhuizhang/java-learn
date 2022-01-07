package IO.java;

import org.junit.Test;

import java.io.*;

/**
 * 一、流的分类：
 * 1.操作数据单位：字节流、字符流
 * 2.数据的流向：输入流、输出流
 * 3.流的角色：节点流、处理流
 * <p>
 * 二、流的体系结构
 * 抽象基类         节点流（或文件流）                               缓冲流（处理流的一种）
 * InputStream     FileInputStream   (read(byte[] buffer))        BufferedInputStream (read(byte[] buffer))
 * OutputStream    FileOutputStream  (write(byte[] buffer,0,len)  BufferedOutputStream (write(byte[] buffer,0,len) / flush()
 * Reader          FileReader (read(char[] cbuf))                 BufferedReader (read(char[] cbuf) / readLine())
 * Writer          FileWriter (write(char[] cbuf,0,len)           BufferedWriter (write(char[] cbuf,0,len) / flush()
 *
 * 
 * @create 2019 上午 10:40
 */
public class FileReaderWriterTest {

    /*
    说明点：
    1. read()的理解：返回读入的一个字符。如果达到文件末尾，返回-1
    2. read(char[] cbuf):返回每次读入cbuf数组中的字符的个数。如果达到文件末尾，返回-1
     */

    @Test
    public void testFileReader1() {
        FileReader fr = null;
        try {
            fr = new FileReader(new File("hello.txt"));
//            int data;
//            while ((data = fr.read()) != -1) {
//                System.out.print((char) data);
//            }
            char[] cbuf = new char[5];
            int len;
            while ((len = fr.read(cbuf)) != -1) {
                //错误 for(int i = 0;i < cbuf.length;i++){
//                for(int i = 0;i < len;i++){
//                    System.out.print(cbuf[i]);
//                }
                //错误 String str = new String(cbuf);
                String str = new String(cbuf, 0, len);
                System.out.print(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /*
    从内存中写出数据到硬盘的文件里。
    File对应的硬盘中的文件如果不存在，在输出的过程中，会自动创建此文件。
         File对应的硬盘中的文件如果存在：
                FileWriter(file):对原有文件的覆盖
                FileWriter(file,true):不对原有文件的覆盖追加
     */
    @Test
    public void testFileWriter() {
        FileWriter fw = null;
        try {
            fw = new FileWriter("hello1.txt", false);
            fw.write("I have a dream!\n");
            fw.write("you need to have a dream!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testBufferedReaderBufferedWriter() {
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            //创建文件和相应的流
            br = new BufferedReader(new FileReader("./file/io/data.txt"));
            bw = new BufferedWriter(new FileWriter(new File("./file/io/data2.txt")));
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
                //bw.write(data + "\n"); //data中不包含换行符
                bw.write(data);
                bw.newLine();
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
