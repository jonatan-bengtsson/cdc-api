package com.tingcore.cdc.configuration;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfiguration {
    @Bean(destroyMethod = "close")
    public RestHighLevelClient restHighLevelClient(
            @Value("${app.elasticsearch.port}") final Integer port,
            @Value("${app.elasticsearch.host}") final String host,
            @Value("${app.elasticsearch.scheme}") final String scheme) {
        return new RestHighLevelClient(RestClient.builder(new HttpHost(host, port, scheme)));
    }
}
