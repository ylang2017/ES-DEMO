package com.example.es;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.es.util.FileUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArticleTest {
    @Test
    public void strTest(){
        String str = "推荐人： 来源：文章阅读网 时间：2020-05-10 18:48 阅读： ";
        String pattern = "\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{2}";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);
        if(m.find()){
            System.out.println(m.group());
        }
    }

    @Test
    public void testBulkAdd(){
        // 模拟从数据库中获取关于文章的其它信息
        List<Map<String,String>> articleList = new ArrayList<>();
        Map<String,String> articleMap = new HashMap<>();
        articleMap.put("title","希望");
        articleMap.put("author","宁再军");
        articleMap.put("date","2018-05-14");
        articleMap.put("contentPath","E:/dev-document/article/希望.txt");
    }

    @Test
    public void testUrl(){
        String url = "https://www.meiwen.com.cn/article/142.html";
        System.out.println(url.lastIndexOf("/"));
        System.out.println(url.lastIndexOf("."));
        int start = url.lastIndexOf("/");
        int end = url.lastIndexOf(".");
        String page = url.substring(start+1,end);
        Integer i = Integer.valueOf(page);
        System.out.println(page);
        System.out.println(i+1);
        System.out.println(url.replace("article","1541"));
    }

    @Test
    public void testJson() throws JsonProcessingException {
        String path = "E:/my-project/ES-DEMO-master/src/main/resources/articleMessage/article.json";
        File file = new File(path);
        if(file.exists()){
            String json = FileUtil.readTxtFile(file);

            JSONObject jsonObject = JSON.parseObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("articleList");
            JSONObject newJson = new JSONObject();
            newJson.put("title","测试");
            newJson.put("author","测试v");
            newJson.put("date","2020-05-29");
            newJson.put("contentPath","faefafeafe");
            newJson.put("cost",13.22);
            newJson.put("available",true);
            jsonArray.add(newJson);

            System.out.println("-------------put前---------------");
            System.out.println(jsonObject.toJSONString());
            System.out.println("--------------jsonArr--------------");
            System.out.println(jsonArray.toJSONString());
            System.out.println("--------------newJson--------------");
            System.out.println(newJson.toJSONString());
            System.out.println("--------------put后--------------");
            jsonObject.put("articleList",jsonArray);
            System.out.println(jsonObject.toJSONString());
            FileUtil.writeFile(path,jsonObject.toJSONString());
        }
    }
}
