package com.xxxx.seckill.utils;

import java.util.UUID;

//UUID工具类，用于生成随机的UID
public class UUIDUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
