package IO.java;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileCombine {

    public static void main(String[] args) {

    }


    public String fileCombine(String filePath) {

        String destFile = filePath + "/all.txt";
        BufferedWriter bw = null;
        BufferedReader br = null;
        String extension = ".csv";

        try {

//            BufferedWriter bw = new BufferedWriter(new FileWriter(destFile));
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destFile), StandardCharsets.UTF_8));
            File[] list = new File(filePath).listFiles();

            for (File file : list) {
                if (file.isFile() && file.getName().endsWith(extension)) {
//                    BufferedReader br = new BufferedReader(new FileReader(file));
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
                    String line;
                    while ((line = br.readLine()) != null) {
                        bw.write(line);
                        bw.newLine();
                    }
                    br.close();
                }
            }
            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(" 合并文件出错...");
        } finally {
            try {
                if (bw != null) bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (br != null) br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return destFile;
    }
}
