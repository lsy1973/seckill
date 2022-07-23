package com.xxxx.seckill.vo;


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
    BIND_ERROR(500212,"参数校验异常" );


    private final Integer code;
    private final String message;
}
