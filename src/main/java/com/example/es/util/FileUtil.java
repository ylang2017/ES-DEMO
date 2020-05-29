package com.example.es.util;

import sun.misc.BASE64Encoder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;

public class FileUtil {
    public static void writeFile(String filePath,String content) {
        try {
            File file = new File(filePath);
            if(!file.exists()){
                file.createNewFile();
            }

            try(FileWriter fw = new FileWriter(file)){
                fw.write(content);
            }
        }catch (IOException e){
            System.out.println(" io error !!!");
        }
    }

    public static String readTxtFile(File file) {
        StringBuilder result = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file));) {
            //构造一个BufferedReader类来读取文件
            String s = null;
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                result.append(System.lineSeparator() + s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static String[] readFileByLines(File file,int skip) {
        try (BufferedReader br = new BufferedReader(new FileReader(file));) {
            Object[] objects = br.lines().skip(skip).limit(100).toArray();
            return Arrays.asList(objects).toArray(new String[objects.length]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void cleanWordTxt(File oriFile,File newFile) throws IOException {
        if(!newFile.exists()){
            newFile.createNewFile();
        }

        if(oriFile.exists()){
            try (BufferedReader br = new BufferedReader(new FileReader(oriFile));
                 BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                         new FileOutputStream(newFile, true)));) {
                String s = null;
                while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                    String t = s.replaceAll("[^\u4E00-\u9FA5]", "")+System.lineSeparator();
                    out.write(t);
                }
            }
        }
    }

    /**
     * 读取指定路径文件，返回base64
     * @param filePath
     * @return
     * @throws Exception
     */
    public static String readFileToBase64(String filePath) throws Exception{
        byte[] b = Files.readAllBytes(Paths.get(filePath));
        return Base64.getEncoder().encodeToString(b);
    }

}
