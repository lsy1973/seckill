package com.xxxx.seckill.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Hashtable;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.producer.buffer-memory}")
    private Integer bufferMemory;
    @Value("${spring.kafka.producer.batch-size}")
    private Integer batchSize;
    @Value("${spring.kafka.bootstrap-servers}")
    private String producerServers;

    //生产配置信息
    @Bean
    public Map<String,Object> producerConfigs(){
        Map<String,Object> props = new Hashtable<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,producerServers);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG,batchSize);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG,bufferMemory);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
        return props;
    }


    //生产者工厂
    @Bean
    public ProducerFactory<String,Object> producerFactory(){
        return new DefaultKafkaProducerFactory<String,Object>(producerConfigs());
    }


    //生产者模板
    @Bean
    public KafkaTemplate<String,Object> kafkaTemplate(){
        return new KafkaTemplate<String,Object>(producerFactory());
    }
}

