package com.xxxx.seckill.utils;


import com.xxxx.seckill.pojo.Order;
import com.xxxx.seckill.vo.GoodsVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailVo {
    private Order order;
    private GoodsVo goodsVo;
}
