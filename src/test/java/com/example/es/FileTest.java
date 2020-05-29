package com.example.es;

import com.example.es.util.FileUtil;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class FileTest {
    private final String FILE_INDEX_NAME = "file_index";
    private final String FILE_INDEX_NAME_2 = "file_index_2";
    private final String FILE_INDEX_NAME_3 = "file_index_3";

    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;

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

    @Test
    public void testIndexFile() throws Exception{
        String txtFileBase64 = FileUtil.readFileToBase64("E:/dev-document/THUOCL_poem_copy.txt");
        String txtFileBase64_2 = FileUtil.readFileToBase64("E:/dev-document/30wChinsesSeqDic.txt");
        String docFileBase64 = FileUtil.readFileToBase64("E:/dev-document/testFile_doc.doc");
        String docFileBase64_2 = FileUtil.readFileToBase64("E:/dev-document/8m_doc.doc");
        String docxFileBase64 = FileUtil.readFileToBase64("E:/dev-document/testFile.docx");
        String docxFileBase64_2 = FileUtil.readFileToBase64("E:/dev-document/8m.docx");
        String pdfFileBase64 = FileUtil.readFileToBase64("E:/dev-document/testFile_pdf.pdf");
        String pdfFileBase64_2 = FileUtil.readFileToBase64("E:/dev-document/5m_pdf.pdf");
        String txtEnglish = FileUtil.readFileToBase64("E:/dev-document/englishTxt.txt");

        Map<String,Object> jsonMap = new HashMap<>();

        jsonMap.put("data", pdfFileBase64_2);


        long curTime = System.currentTimeMillis();

        IndexRequest request = new IndexRequest(FILE_INDEX_NAME_3)
            .setPipeline("attachment")   //这里就是前面通过json创建的管道
            .source(jsonMap);

        client.index(request, RequestOptions.DEFAULT);   //执行

        System.out.println("索引完毕，共耗时："+(System.currentTimeMillis()-curTime));
    }


}
