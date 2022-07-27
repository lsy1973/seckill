package com.xxxx.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.seckill.mapper.SeckillOrdersMapper;
import com.xxxx.seckill.pojo.SeckillOrders;
import com.xxxx.seckill.pojo.User;
import com.xxxx.seckill.service.ISeckillOrdersService;
import com.xxxx.seckill.utils.MD5Util;
import com.xxxx.seckill.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author linksy
 * @since 2022-07-23
 */
@Service
public class SeckillOrdersServiceImpl extends ServiceImpl<SeckillOrdersMapper, SeckillOrders> implements ISeckillOrdersService {


    @Autowired
    private SeckillOrdersMapper seckillOrdersMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Long getResult(User user, Long goodsId) {
        SeckillOrders seckillOrder = seckillOrdersMapper.selectOne(new
                QueryWrapper<SeckillOrders>().eq("user_id", user.getId()).eq("goods_id",
                goodsId));
        System.out.println("getresult_seckillOrder:"+seckillOrder);
        if (null != seckillOrder) {
            return seckillOrder.getOrderId();
        } else {
            if (redisTemplate.hasKey("isStockEmpty:" + goodsId)) {
                return -1L;
            } else {
                return 0L;
            }
        }
    }


    //获取秒杀地址，在中间插入随机的字符串，同时存入redis中，
    @Override
    public String createPath(User user, Long goodsId) {
        String str = MD5Util.md5(UUIDUtil.uuid()+"123456");
        redisTemplate.opsForValue().set("seckillPath:" + user.getId() + ":" + goodsId, str,60, TimeUnit.SECONDS);
        return str;
    }
}
