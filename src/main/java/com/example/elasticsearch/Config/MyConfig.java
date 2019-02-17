package com.example.elasticsearch.Config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @program: elasticsearch
 * @description: ES的基础配置类
 * @author: cyj
 * @create: 2019-01-23 10:13
 **/
@Configuration
public class MyConfig {

    @Bean
    public TransportClient client() throws UnknownHostException {
        TransportAddress node = new TransportAddress(
                InetAddress.getByName("www.thetnteam.com"), 9300
        );
        Settings settings = Settings.builder()
                .put("cluster.name", "cyj")
                .build();
        TransportClient client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(node);
        return client;
    }
}
