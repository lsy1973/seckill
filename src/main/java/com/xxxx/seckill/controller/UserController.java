package com.xxxx.seckill.controller;


import com.xxxx.seckill.pojo.User;
import com.xxxx.seckill.rabbitmq.MQSender;
import com.xxxx.seckill.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//前端控制器
//用于测试用户登录接口 压测
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private MQSender    mqSender;



    @RequestMapping("/info")
    @ResponseBody
    public RespBean info(User user){
        return RespBean.success(user);
    }



//    //用于测试发送mq的方法
//    @RequestMapping("/mq")
//    @ResponseBody
//    public void mq(){
//        mqSender.send("hello,rabbitmq!");
//    }
//
//    @RequestMapping("/mq/direct01")
//    @ResponseBody
//    public void mq_direct_01(){
//        mqSender.directSend01("hello,direct,red!");
//    }
//
//    @RequestMapping("/mq/direct02")
//    @ResponseBody
//    public void mq_direct_02(){
//        mqSender.directSend02("hello,direct,green!");
//    }


}
