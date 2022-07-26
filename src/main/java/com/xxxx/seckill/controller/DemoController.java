package com.xxxx.seckill.controller;


import com.xxxx.seckill.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/demo")
public class DemoController {

    private User user;

    @RequestMapping("/hello")
    public String hello(Model model){
        User user1 = new User();
        user1.setNickname("xiaoming");
        model.addAttribute("user",user1);
//        System.out.println(model);
        return "hello";
    }
}
