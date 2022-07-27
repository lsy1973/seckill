package com.xxxx.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.seckill.pojo.Order;
import com.xxxx.seckill.pojo.User;
import com.xxxx.seckill.utils.OrderDetailVo;
import com.xxxx.seckill.vo.GoodsVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author linksy
 * @since 2022-07-23
 */
public interface IOrderService extends IService<Order> {
    Order seckill(User user, GoodsVo goods);

    OrderDetailVo getDetail(Long orderId);
    //校验秒杀地址
    boolean checkPath(User user, Long goodsId, String path);
    //校验验证码
    boolean checkCaptcha(User user, Long goodsId, String captcha);
}
