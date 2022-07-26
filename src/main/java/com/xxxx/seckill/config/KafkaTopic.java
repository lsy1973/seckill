package com.xxxx.seckill.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopic {

    @Bean
    public NewTopic batchTopic() {
        return new NewTopic("testTopic",8,(short)0);
    }
}


