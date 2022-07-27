package com.xxxx.seckill.vo;


import javafx.concurrent.Service;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum RespBeanEnum {
    //通用代码
    SUCCESS (200,"成功"),
    ERROR (500,"服务端异常"),
    //登录模块代码
    LOGIN_ERROR(500210,"用户名或密码错误"),
    MOBILE_ERROR(500211, "手机号码格式不正确"),
    BIND_ERROR(500212,"参数校验异常" ),
    MOBILE_NOT_EXIST(500213, "手机号码不存在"),
    PASSWORD_UPDATE_FAIL(500214, "密码更新失败"),
    //秒杀模块代码
    EMPTY_STOCK(5005210,"库存不足！"),
    REPEATE_ERROR(5005211,"重复购买！"),
    SESSION_ERROR(500212,"用户不存在" ),
    ORDER_NOT_EXIST(500213,"订单不存在" ),
    PATH_ERROR(500214,"路径错误" ),
    REQUEST_ILLEGAL(500215,"请求非法" ),
    CAPTCHA_ERROR(200216, "验证码错误");

    private final Integer code;
    private final String message;
}
