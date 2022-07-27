package com.xxxx.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.seckill.pojo.SeckillOrders;
import com.xxxx.seckill.pojo.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author linksy
 * @since 2022-07-23
 */
public interface ISeckillOrdersService extends IService<SeckillOrders> {
    Long getResult(User user, Long goodsId);

    String createPath(User user, Long goodsId);
}
