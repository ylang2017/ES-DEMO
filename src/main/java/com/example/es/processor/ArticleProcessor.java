package com.example.es.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.es.util.FileUtil;
import io.netty.util.internal.StringUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArticleProcessor implements PageProcessor {
    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me()
            .setRetryTimes(1)
            .setSleepTime(3000).addHeader("Accept-Encoding", "/")
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")
            //.setCharset("utf-8");
            .setCharset("GBK");

    private static String url = "https://www.meiwen.com.cn/article/{index}.html";
    private static String url2 = "http://www.duwenzhang.com/meiwen.html";
    //private static String fileNameTemp = "E:/dev-document/article/{name}.txt";
    private static String fileNameTemp = "E:/projectRepository/article/{name}.txt";
    private static List<String> urlList = new ArrayList<>();

    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {
        Map<String, String> map = null;
        if(page.getUrl().toString().contains("www.duwenzhang.com")){
            map = scanDuWenZhangSite(page);
        }else if(page.getUrl().toString().contains("www.meiwen.com.cn")){
            map = scanMeiWenSite(page);
        }

        if (null != map) {
            System.out.println("-----------------抓取完成，正在保存数据。。。------------------");
            // 保存文章内容
            FileUtil.writeFile(fileNameTemp.replace("{name}",map.get("title")),map.get("content"));

            // 更新json
            writeJson(map.get("title"),map.get("author"),map.get("date"),fileNameTemp.replace("{name}",map.get("title")));
            System.out.println("-----------------数据保存完成，即将进行下次抓取。。。------------------");
        }
    }

    /**
     * 扫描 读文章网
     *
     * @param page
     * @return
     */
    private Map<String, String> scanDuWenZhangSite(Page page) {
        if (urlList.isEmpty()) {
            Html html = page.getHtml();
            urlList = html.$("center>table:eq(3) a", "href").all();

            page.addTargetRequest(urlList.get(0));
            return null;
        } else {
            //提取页面信息
            Html html = page.getHtml();
            String title = html.$("table.tbspan:nth-child(4) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(2) > tbody:nth-child(1) > tr:nth-child(2) > td:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > h1:nth-child(1)", "text").toString();
            //String author = html.$(".author", "text").toString();
            String author = html.$("table.tbspan:nth-child(4) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(2) > tbody:nth-child(1) > tr:nth-child(2) > td:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(2) *","text").toString();

            String pattern = "\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{2}";
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(author);
            String date = "";
            if(m.find()){
                date = m.group();
            }

            List<String> contentList = html.$("#wenzhangziti p", "text").all();
            StringBuilder stringBuilder = new StringBuilder();
            contentList.forEach((s) -> {
                stringBuilder.append(s);
            });

            String content = stringBuilder.toString();
            if(StringUtil.isNullOrEmpty(content)){
                content = html.$("#wenzhangziti", "text").toString();
            }

            /*System.out.println(title);
            System.out.println(author);
            System.out.println(stringBuilder.toString());
            System.out.println("------------------------------");*/

            String curUrl = page.getUrl().toString();
            if (urlList.indexOf(curUrl) != (urlList.size() - 1)) {
                page.addTargetRequest(urlList.get(urlList.indexOf(curUrl) + 1));
            }

            Map<String, String> map = new HashMap<>();
            map.put("title", title);
            map.put("author", "佚名");
            map.put("date", date);
            map.put("content", content);
            return map;
        }
    }

    /**
     * 扫描 美文网 文章
     *
     * @param page
     * @return
     */
    private Map<String, String> scanMeiWenSite(Page page) {
        int nextIndex = getNextArticleIndex(page.getUrl().toString());
        if (nextIndex < 5) {
            // 部分三：从页面发现后续的url地址来抓取
            page.addTargetRequest(url.replace("{index}", nextIndex + ""));
        } else {
            System.out.println("-----------------抓取任务完成，即将退出------------------");
        }

        if (page.getStatusCode() != 200) {
            //skip this page
            page.setSkip(true);
            System.out.println("-----------------请求状态码异常，跳过当前页------------------");
        }
        Html html = page.getHtml();
        String title = html.$("#title>h1", "text").toString();
        String author = html.$("li.writer", "text").toString().replace("作者:", "");
        String date = html.$("li.pubdate>span", "text").toString();
        String content = html.$("div.content>p", "text").toString();
        Map<String, String> map = new HashMap<>();
        map.put("title", title);
        map.put("author", author);
        map.put("date", date);
        map.put("content", content);
        return map;
    }

    private void writeJson(String title, String author, String date, String contentPath) {
        //String path = "E:/my-project/ES-DEMO-master/src/main/resources/articleMessage/article.json";
        String path = "E:/projectRepository/es-demo/src/main/resources/articleMessage/article.json";
        File file = new File(path);
        if (file.exists()) {
            String json = FileUtil.readTxtFile(file);

            JSONObject jsonObject = JSON.parseObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("articleList");
            JSONObject newJson = new JSONObject();
            newJson.put("title", title);
            newJson.put("author", author);
            newJson.put("date", date);
            newJson.put("contentPath", contentPath);
            newJson.put("cost", Math.random() * 10);
            newJson.put("available", Math.random() > 0.5);
            jsonArray.add(newJson);

            FileUtil.writeFile(path, jsonObject.toJSONString());
        }
    }

    // 获取下一篇文章编号
    private int getNextArticleIndex(String curUrl) {
        int start = curUrl.lastIndexOf("/");
        int end = curUrl.lastIndexOf(".");
        String page = curUrl.substring(start + 1, end);
        System.out.println("-----------------抓取第：" + page + "页------------------");
        return Integer.valueOf(page) + 1;
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new ArticleProcessor())
                //从"https://github.com/code4craft"开始抓
                .addUrl(url2)
                //开启1个线程抓取
                .thread(1)
                //启动爬虫
                .run();
    }
}
