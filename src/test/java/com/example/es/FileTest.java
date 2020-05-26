package com.example.es;

import com.example.es.util.FileUtil;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class FileTest {

    @Test
    public void testReadFile(){
        File file = new File("E:/dev-document/THUOCL_lishimingren_copy.txt");
        if(file.exists()){
           String[] strArr = FileUtil.readFileByLines(file,100);
           Arrays.asList(strArr).forEach(str->System.out.println(str));
        }
    }

    @Test
    public void testClean() throws IOException {
        /*File file = new File("E:/dev-document/THUOCL_lishimingren.txt");
        File file2 = new File("E:/dev-document/THUOCL_lishimingren_copy.txt");*/
        /*File file = new File("E:/dev-document/THUOCL_poem.txt");
        File file2 = new File("E:/dev-document/THUOCL_poem_copy.txt"); */
        File file = new File("E:/dev-document/30wChinsesSeqDic.txt");
        File file2 = new File("E:/dev-document/30wChinsesSeqDic_copy.txt");
        FileUtil.cleanWordTxt(file,file2);
    }
}
