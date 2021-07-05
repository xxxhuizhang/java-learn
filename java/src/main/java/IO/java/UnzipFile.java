package IO.java;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class UnzipFile {


    public static void main(String[] args) {

        File file = new File("/Users/apple/Desktop/lems-zip/zip/123.zip");
        String unzipPathstr = "/Users/apple/Desktop/lems-zip/unzip/";
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
                    File dir = new File(unzipPathstr + entry.getName());
                    if (!dir.exists()) {
                        dir.mkdir();
                        continue;
                    }
                }

                File tmp = new File(entry.getName());
                if (tmp.isHidden()) {
                    System.out.println("tempFiel : " + entry.getName());
                }

                File destFile = new File(unzipPathstr + entry.getName());
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
