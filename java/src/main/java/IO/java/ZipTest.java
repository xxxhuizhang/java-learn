package IO.java;

// https://blog.csdn.net/weixin_51503235/article/details/124180841

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipTest {


    @Test
    public void zipTest() {

        String fileDirPath = "/Users/apple/Desktop/lemsReceive/zip";
        String fileDirZipPath = "./fileDirZipPath.zip";

        final File fileDir = new File(fileDirPath);

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(fileDirZipPath));) {

            for (File file : fileDir.listFiles()) {

                String name = file.getName();
                String parent = file.getParent();
                String path = file.getPath();
                String absolutePath = file.getAbsolutePath();
                String canonicalPath = file.getCanonicalPath();

                zipOutputStream.putNextEntry(new ZipEntry("/a/b/c/" + name));// 这里可以自定义压缩文件的目录，如/a/b/c/name.txt
                FileInputStream inputStream = new FileInputStream(absolutePath);

                int len;
                byte[] bytes = new byte[1024];
                while ((len = inputStream.read(bytes)) != -1) {
                    zipOutputStream.write(bytes, 0, len);
                }
                inputStream.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
