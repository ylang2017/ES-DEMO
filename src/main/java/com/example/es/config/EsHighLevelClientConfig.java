package com.example.es.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;

//@Configuration
public class EsHighLevelClientConfig {
    /**
     * 使用sprnig-data-elasticsearch 自动提供的 RestHighLevelClient等构建 ElasticsearchRestTemplate
     * 2020年3月18日 下午3:05:55 xx添加此方法
     * @param client
     * @param converter
     * @param resultsMapper
     * @return
     */
   /* @Bean
    public ElasticsearchRestTemplate elasticsearchTemplate(RestHighLevelClient client, ElasticsearchConverter converter,
        ResultsMapper resultsMapper) {
        return new ElasticsearchRestTemplate(client, converter, resultsMapper);
    }*/
}
