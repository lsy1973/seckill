package com.xxxx.seckill.pojo.kafka;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.xxxx.seckill.pojo.SeckillMessage;
import com.xxxx.seckill.pojo.SeckillOrders;
import com.xxxx.seckill.pojo.User;
import com.xxxx.seckill.service.IGoodsService;
import com.xxxx.seckill.service.IOrderService;
import com.xxxx.seckill.vo.GoodsVo;
import com.xxxx.seckill.vo.RespBean;
import com.xxxx.seckill.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Receiver {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IOrderService orderService;

    @KafkaListener(topics = "testTopic",groupId = "0")
    public void receive(String message) {
        log.info("接收消息："+message);
//        message = JSONObject.toJSONString(message);
        SeckillMessage seckillMessage= JSONObject.parseObject(message,SeckillMessage.class);
        Long goodId=seckillMessage.getGoodsId();
        User user=seckillMessage.getUser();
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodId);
//        库存不够的话直接返回
        if(goodsVo.getStockCount()<1) {
            return ;
        }
//        从redis中拿订单信息
        SeckillOrders seckillOrder=(SeckillOrders)redisTemplate.opsForValue().get("order:"+user.getId()+":"+goodId);
//     如果有，直接返回
        if(seckillOrder!=null)
            return;

//        然后进行秒杀操作并创建订单信息
        orderService.seckill(user,goodsVo);

    }
}


