package IO.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;


//https://blog.csdn.net/qq_17522211/article/details/84579883

public class UnzipFile {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String zipFileSuffix = ".zip";
    private String baseFolder = "/Users/apple/Desktop/lems-zip/";
    private List zipDirList = Arrays.asList("SSE", "SZSE", "NEEQ"); //上交所（SSE）、深交所(SZSE)、股转(NEEQ)


    public static void main(String[] args) {

        UnzipFile unzipFile = new UnzipFile();
        unzipFile.unzipFile();
    }


    public void unzipFile() {

        String currentDateFolder = new SimpleDateFormat("yyyyMMdd").format(new Date());

        String zipPath = String.format("%szip/%s/", baseFolder, currentDateFolder);
        String unzipPath = String.format("%sunzip/%s/", baseFolder, currentDateFolder);

        recusiveFindFile(zipPath, unzipPath);
    }

    private void recusiveFindFile(String zipPath, String unzipPath) {

        File zipPathFile = new File(zipPath);

        if (!zipPathFile.exists()) {
            logger.error("没有 {} 文件夹", zipPath);
        }

        for (File file : zipPathFile.listFiles()) {
            String fileName = file.getName();
            if (file.isDirectory()) {
                recusiveFindFile(zipPath + fileName + "/", unzipPath + fileName + "/");
            } else if (fileName.endsWith(zipFileSuffix)) {
                unzipFile(zipPath + fileName, unzipPath);
            } else {
                logger.info("other file : {}", fileName);
            }
        }
    }


    public void unzipFile(String zipPath, String unzipPath) {

        File file = new File(zipPath);

        ZipInputStream zipInputStream = null;
        OutputStream os = null;
        InputStream in = null;


        try {
            ZipFile zipFile = new ZipFile(file);
            zipInputStream = new ZipInputStream(new FileInputStream(file));
            ZipEntry entry;
            while (((entry = zipInputStream.getNextEntry()) != null)) {

                if (entry.isDirectory()) {
                    System.out.println("Dir : " + entry.getName());
                    File dir = new File(unzipPath + entry.getName());
                    if (!dir.exists()) {
                        dir.mkdirs();
                        continue;
                    }
                }

                File tmp = new File(entry.getName());
                if (tmp.isHidden()) {
                    System.out.println("hidden : " + entry.getName());
                }

                File destFile = new File(unzipPath + entry.getName());
                if (!destFile.exists()) {

                    os = new FileOutputStream(destFile);
                    //in = zipFile.getInputStream(entry);
                    in = new BufferedInputStream(zipFile.getInputStream(entry));
                    int count = 0;
                    byte[] bytes = new byte[1024];
                    while ((count = in.read()) != -1) {
                        //os.write(bytes, 0, count);
                        os.write(bytes);
                    }
                }
                System.out.println(entry.getName() + "解压成功");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zipInputStream != null) {
                try {
                    zipInputStream.closeEntry();
                    zipInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
