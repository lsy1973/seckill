package com.xxxx.seckill.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SeckillMessage {
    Long goodsId;
    User user;
}
