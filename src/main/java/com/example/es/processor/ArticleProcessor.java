package com.example.es.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.es.util.FileUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ArticleProcessor implements PageProcessor {
    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me()
            .setRetryTimes(1)
            .setSleepTime(5000).addHeader("Accept-Encoding", "/")
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")
            .setCharset("utf-8");

    private static String url = "https://www.meiwen.com.cn/article/{index}.html";
    private static String fileNameTemp = "E:/dev-document/article/{name}.txt";

    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {
        Map<String,String> map = scanMeiWenSite(page);

        System.out.println("-----------------抓取完成，正在保存数据。。。------------------");
        // 保存文章内容
        FileUtil.writeFile(fileNameTemp.replace("{name}",map.get("title")),map.get("content"));

        // 更新json
        writeJson(map.get("title"),map.get("author"),map.get("date"),map.get("content"));
        System.out.println("-----------------数据保存完成，即将进行下次抓取。。。------------------");
    }

    private Map<String,String> scanMeiWenSite(Page page){
        int nextIndex = getNextArticleIndex(page.getUrl().toString());
        if(nextIndex<5){
            // 部分三：从页面发现后续的url地址来抓取
            page.addTargetRequest(url.replace("{index}",nextIndex+""));
        }else{
            System.out.println("-----------------抓取任务完成，即将退出------------------");
        }

        if (page.getStatusCode() != 200) {
            //skip this page
            page.setSkip(true);
            System.out.println("-----------------请求状态码异常，跳过当前页------------------");
        }
        Html html = page.getHtml();
        String title = html.$("#title>h1","text").toString();
        String author = html.$("li.writer","text").toString().replace("作者:","");
        String date = html.$("li.pubdate>span","text").toString();
        String content = html.$("div.content>p","text").toString();
        Map<String,String> map = new HashMap<>();
        map.put("title",title);
        map.put("author",author);
        map.put("date",date);
        map.put("content",content);
        return map;
    }

    private void writeJson(String title,String author,String date,String contentPath){
        String path = "E:/my-project/ES-DEMO-master/src/main/resources/articleMessage/article.json";
        File file = new File(path);
        if(file.exists()){
            String json = FileUtil.readTxtFile(file);

            JSONObject jsonObject = JSON.parseObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("articleList");
            JSONObject newJson = new JSONObject();
            newJson.put("title",title);
            newJson.put("author",author);
            newJson.put("date",date);
            newJson.put("contentPath",contentPath);
            newJson.put("cost", Math.random()*10);
            newJson.put("available",Math.random()>0.5);
            jsonArray.add(newJson);

            FileUtil.writeFile(path,jsonObject.toJSONString());
        }
    }

    // 获取下一篇文章编号
    private int getNextArticleIndex(String curUrl){
        int start = curUrl.lastIndexOf("/");
        int end = curUrl.lastIndexOf(".");
        String page = curUrl.substring(start+1,end);
        System.out.println("-----------------抓取第："+page+"页------------------");
        return Integer.valueOf(page)+1;
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new ArticleProcessor())
            //从"https://github.com/code4craft"开始抓
            .addUrl(url.replace("{index}","1"))
            //开启1个线程抓取
            .thread(1)
            //启动爬虫
            .run();
    }
}
