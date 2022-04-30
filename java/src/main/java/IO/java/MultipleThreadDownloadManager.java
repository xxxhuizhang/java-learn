package IO.java;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * https://blog.csdn.net/lyt_7cs1dn9/article/details/75105266
 *
 * Http 协议中的Range请求头例子
 * https://emacsist.github.io/2015/12/29/http-%E5%8D%8F%E8%AE%AE%E4%B8%AD%E7%9A%84range%E8%AF%B7%E6%B1%82%E5%A4%B4%E4%BE%8B%E5%AD%90/
 *
 * https://www.jb51.net/article/182046.htm
 *
 */


public class MultipleThreadDownloadManager implements Runnable {

    private String uri;
    private File target;

    public MultipleThreadDownloadManager(String uri, File target) {
        this.target = target;
        this.uri = uri;
        if (target.exists() == false) {
            try {
                target.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 开始下载
     */
    public void start() {
        new Thread(this).start();
    }


    /**
     * 根据文件总大小计算线程数量
     *
     * @param totalSize
     * @return
     */
    public int threadCount(int totalSize) {
        if (totalSize < 30 * 2014 * 1024) {
            return 1;
        }
        return 30;
    }


    @Override
    public void run() {
        //获取文件总大小
        int totalSize = 0;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(uri).openConnection();
            connection.connect();
            int contentLength = connection.getContentLength();
            totalSize = contentLength;
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将文件分片并分开下载
        int threadCount = threadCount(totalSize);
        int perThreadSize = totalSize / threadCount;//每一个线程分到的任务下载量
        int id = 0;
        int from = 0, to = 0;
        while (totalSize > 0) {
            id++;
            //计算分片
            if (totalSize < perThreadSize) {
                from = 0;
                to = totalSize;
            } else {
                from = totalSize;
                to = from + perThreadSize;
            }
            //开始下载
            UnitDownloader downloader = new UnitDownloader(from, to, target, uri, id);
            new Thread(downloader).start();
        }
    }
}


class UnitDownloader implements Runnable {
    private int from;
    private int to;
    private File target;
    private String uri;
    private int id;

    public UnitDownloader(int from, int to, File target, String uri, int id) {
        this.from = from;
        this.to = to;
        this.target = target;
        this.uri = uri;
        this.id = id;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    @Override
    public void run() {
        //download and save data
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(uri).openConnection();
            connection.setRequestProperty("Range", "bytes=" + from + "-" + to);
            connection.connect();
            int totalSize = connection.getContentLength();
            InputStream inputStream = connection.getInputStream();

            RandomAccessFile randomAccessFile = new RandomAccessFile(target, "rw");
            randomAccessFile.seek(from);
            byte[] buffer = new byte[1024 * 1024];
            int readCount = inputStream.read(buffer, 0, buffer.length);

            while (readCount > 0) {
                totalSize -= readCount;
                System.out.println("分片：" + this.id + "的剩余：" + totalSize);
                randomAccessFile.write(buffer, 0, readCount);
                readCount = inputStream.read(buffer, 0, buffer.length);
            }

            inputStream.close();
            randomAccessFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


