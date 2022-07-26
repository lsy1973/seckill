package com.xxxx.seckill.pojo.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Sender {
    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;


    public void send(String data) {
        log.info("发送消息:"+data);
        kafkaTemplate.send("testTopic","0",data);
    }

}

