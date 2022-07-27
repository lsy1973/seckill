package com.xxxx.seckill.controller;

import com.xxxx.seckill.pojo.User;
import com.xxxx.seckill.service.IGoodsService;
import com.xxxx.seckill.service.IUserService;
import com.xxxx.seckill.vo.DetailVo;
import com.xxxx.seckill.vo.GoodsVo;
import com.xxxx.seckill.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.concurrent.TimeUnit;

//windows
//goodsList 使用服务器redis缓存页面，1000线程*10  吞吐量241/s
//不使用redis缓存页面，吞吐量470/s
//使用本地redis 吞吐量12000/s

//linux 100*10
//使用服务器redis：68/s
//不使用：98/s


@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    //跳转到登录页面

    //    public String toLogin(HttpServletRequest request, HttpServletResponse response, Model model, @CookieValue("userTicket") String ticket){
    @RequestMapping(value = "/toList",produces = "text/html;charset=utf-8")
    @ResponseBody
    public String toList(Model model, User user, HttpServletRequest request, HttpServletResponse response){
        //在Redis中获取页面，如果不为空，则返回页面
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String html = (String) valueOperations.get("goodsList");
        if(!StringUtils.isEmpty(html)){
            return html;
        }

        model.addAttribute("user",user);
        model.addAttribute("goodsList",goodsService.findGoodsVo());

        //如果为空，则先存入redis，再返回
        WebContext context = new WebContext(request,response,request.getServletContext(),request.getLocale(),model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsList",context);
        if(!StringUtils.isEmpty(html)){
            valueOperations.set("goodsList",html,60, TimeUnit.SECONDS);
        }
        return html;
    }

    //将整个页面传入redis缓存中
//    @RequestMapping(value = "/toDetail2/{goodsId}", produces = "text/html;charset=utf-8")
//    @ResponseBody
//    public String toDetail2(HttpServletRequest request, HttpServletResponse
//            response,Model model, User user, @PathVariable Long goodsId) {
//
//        ValueOperations valueOperations = redisTemplate.opsForValue();
////Redis中获取页面，如果不为空，直接返回页面
//        String html = (String) valueOperations.get("goodsDetail:" + goodsId);
//        if (!StringUtils.isEmpty(html)) {
//            return html;
//        }
//
//
//
//        model.addAttribute("user", user);
//        System.out.println("传入进来toDetail的user为:"+user);
//        GoodsVo goods = goodsService.findGoodsVoByGoodsId(goodsId);
//        model.addAttribute("goods", goods);
//        Date startDate = goods.getStartDate();
//        Date endDate = goods.getEndDate();
//        Date nowDate = new Date();
////秒杀状态
//        int secKillStatus = 0;
////剩余开始时间
//        int remainSeconds = 0;
////秒杀还未开始
//        if (nowDate.before(startDate)) {
//            remainSeconds = (int) ((startDate.getTime()-nowDate.getTime())/1000);
//// 秒杀已结束
//        } else if (nowDate.after(endDate)) {
//            secKillStatus = 2;
//            remainSeconds = -1;
//// 秒杀中
//        } else {
//            secKillStatus = 1;
//            remainSeconds = 0;
//        }
//        model.addAttribute("secKillStatus",secKillStatus);
//        model.addAttribute("remainSeconds",remainSeconds);
////如果为空，手动渲染，存入Redis并返回
//        WebContext context = new WebContext(request, response,
//                request.getServletContext(), request.getLocale(),
//                model.asMap());
//        html = thymeleafViewResolver.getTemplateEngine().process("goodsDetail",
//                context);
//        if (!StringUtils.isEmpty(html)) {
//            valueOperations.set("goodsDetail:" + goodsId, html, 60,
//                    TimeUnit.SECONDS);
//        }
//        return html;
//    }



    //页面静态化，直接返回respbean，里面包含DetailVo对象，直接返回给页面，页面获取对象数据直接渲染
    @RequestMapping(value = "/toDetail/{goodsId}")
    @ResponseBody
    public RespBean toDetail(Model model, User user, @PathVariable Long goodsId) {

//        ValueOperations valueOperations = redisTemplate.opsForValue();
//Redis中获取页面，如果不为空，直接返回页面
//        String html = (String) valueOperations.get("goodsDetail:" + goodsId);
//        if (!StringUtils.isEmpty(html)) {
//            return html;
//        }


//        model.addAttribute("user", user);
//        System.out.println("传入进来toDetail的user为:"+user);
        GoodsVo goods = goodsService.findGoodsVoByGoodsId(goodsId);
//        model.addAttribute("goods", goods);
        Date startDate = goods.getStartDate();
        Date endDate = goods.getEndDate();
        Date nowDate = new Date();
//秒杀状态
        int secKillStatus = 0;
//剩余开始时间
        int remainSeconds = 0;
//秒杀还未开始
        if (nowDate.before(startDate)) {
            remainSeconds = (int) ((startDate.getTime()-nowDate.getTime())/1000);
// 秒杀已结束
        } else if (nowDate.after(endDate)) {
            secKillStatus = 2;
            remainSeconds = -1;
// 秒杀中
        } else {
            secKillStatus = 1;
            remainSeconds = 0;
        }
//        model.addAttribute("secKillStatus",secKillStatus);
//        model.addAttribute("remainSeconds",remainSeconds);
//如果为空，手动渲染，存入Redis并返回
//        WebContext context = new WebContext(request, response,
//                request.getServletContext(), request.getLocale(),
//                model.asMap());
//        html = thymeleafViewResolver.getTemplateEngine().process("goodsDetail",
//                context);
//        if (!StringUtils.isEmpty(html)) {
//            valueOperations.set("goodsDetail:" + goodsId, html, 60,
//                    TimeUnit.SECONDS);
//        }
        DetailVo detailVo = new DetailVo();
        detailVo.setGoodsVo(goods);
        detailVo.setUser(user);
        detailVo.setRemainSeconds(remainSeconds);
        detailVo.setSecKillStatus(secKillStatus);
        return RespBean.success(detailVo);
    }
}
