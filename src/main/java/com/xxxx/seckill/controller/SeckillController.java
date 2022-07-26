package com.xxxx.seckill.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxxx.seckill.pojo.Order;
import com.xxxx.seckill.pojo.SeckillMessage;
import com.xxxx.seckill.pojo.SeckillOrders;
import com.xxxx.seckill.pojo.User;
import com.xxxx.seckill.pojo.kafka.Sender;
import com.xxxx.seckill.service.IGoodsService;
import com.xxxx.seckill.service.IOrderService;
import com.xxxx.seckill.service.ISeckillOrdersService;
import com.xxxx.seckill.vo.GoodsVo;
import com.xxxx.seckill.vo.RespBean;
import com.xxxx.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.converter.ObjectToStringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


//redis 页面缓存 优化前 window 吞吐量qps：315.9/sec,334/sec
//redis 页面缓存 优化前 linux 吞吐量qps:113.5/s


//页面静态化+redis存储 360/sec
@Controller
@RequestMapping("/seckill")
public class SeckillController {
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private ISeckillOrdersService seckillOrderService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private Sender sender;

    @RequestMapping(value="/doSeckill",method = RequestMethod.POST)
    @ResponseBody
    public RespBean doSeckill(Model model, User user, Long goodsId) {
//        if (user == null) {
//            return RespBean.error(RespBeanEnum.SESSION_ERROR);
//        }
////        model.addAttribute("user", user);
//        GoodsVo goods = goodsService.findGoodsVoByGoodsId(goodsId);
////判断库存
//        if (goods.getStockCount() < 1) {
////            model.addAttribute("errmsg", RespBeanEnum.EMPTY_STOCK.getMessage());
//            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
//        }
//        //判断是否重复抢购
////        SeckillOrders seckillOrder = seckillOrderService.getOne(new
////                QueryWrapper<SeckillOrders>().eq("user_id", user.getId()).eq(
////                "goods_id",
////                goodsId));
////        if (seckillOrder != null) {
////            model.addAttribute("errmsg", RespBeanEnum.REPEATE_ERROR.getMessage());
////            return RespBean.error(RespBeanEnum.REPEATE_ERROR);
////        }
////        Order order = orderService.seckill(user, goods);
//////        model.addAttribute("order",order);
//////        model.addAttribute("goods",goods);
////        return RespBean.success(order);
//        SeckillOrders seckillOrder = (SeckillOrders)
//                redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);
//        if (seckillOrder!=null) {
//            return RespBean.error(RespBeanEnum.REPEATE_ERROR);
//        }

        SeckillMessage seckillMessage = new SeckillMessage( goodsId,user);


//        Order order = orderService.seckill(user, goods);
//        sender.send(JSON.toString());
//        JSONString authTbAccountsByBd = instance.getAuthTbAccountsByBd("");
//        JSONObject parse = (JSONObject) JSON.parse(authTbAccountsByBd.getValue());
        sender.send(JSONObject.toJSONString(seckillMessage));
        return RespBean.success(0);
    }

}
