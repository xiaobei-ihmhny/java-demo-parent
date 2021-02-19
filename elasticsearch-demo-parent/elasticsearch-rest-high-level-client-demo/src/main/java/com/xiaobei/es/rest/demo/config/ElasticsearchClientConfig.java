package com.xiaobei.es.rest.demo.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置 {@link RestHighLevelClient}
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-19 19:35:35
 */
@Configuration
public class ElasticsearchClientConfig {

    @Bean
    public RestHighLevelClient client() {
        HttpHost httpHost = new HttpHost(
                "localhost", 9200, "http");
        return new RestHighLevelClient(
                RestClient.builder(httpHost));
    }
}
