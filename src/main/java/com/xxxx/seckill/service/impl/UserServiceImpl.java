package com.xxxx.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.seckill.exception.GlobalException;
import com.xxxx.seckill.mapper.UserMapper;
import com.xxxx.seckill.pojo.User;
import com.xxxx.seckill.service.IUserService;
import com.xxxx.seckill.utils.CookieUtil;
import com.xxxx.seckill.utils.MD5Util;
import com.xxxx.seckill.utils.UUIDUtil;
import com.xxxx.seckill.vo.LoginVo;
import com.xxxx.seckill.vo.RespBean;
import com.xxxx.seckill.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author linksy
 * @since 2022-07-22
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public RespBean login(HttpServletRequest request, HttpServletResponse response, LoginVo loginVo) {
        String mobile = loginVo.getMobile();

        String password = loginVo.getPassword();
//        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
//            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
//        }
//        if(!ValidatorUtil.isMobile(mobile)){
//            return RespBean.error(RespBeanEnum.MOBILE_ERROR);
//        }
        //根据手机号码查询用户
        User user = userMapper.selectById(mobile);
        System.out.println("登录用户的信息：" + user);
        if (null == user) {
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
//            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
        //判断密码是否正确
        if (!MD5Util.formPassToDBPass(password, user.getSlat()).equals(user.getPassword())) {
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
//            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
        String ticket = UUIDUtil.uuid();
        System.out.println("生成的ticket是："+ticket);
//        request.getSession().setAttribute(ticket,user);
        //把用户数据存入redis,key="user:"+ticket,value=user
        redisTemplate.opsForValue().set("user:" + ticket, user);
        CookieUtil.setCookie(request, response, "userTicket", ticket);
//        System.out.println("cookie设置成功！");
        return RespBean.success(ticket);


//        return RespBean.success();
    }

    @Override
    public User getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isEmpty(userTicket)) {
            return null;
        }
        User user = (User) redisTemplate.opsForValue().get("user:" + userTicket);
//        System.out.println("service获取到的userTicket是" + userTicket);
//        System.out.println("service获取到的user是" + user);

        //获取user之后，如果不为空，我们需要重新设置cookie
        if (user != null) {
            CookieUtil.setCookie(request, response, "userTicket", userTicket);
        }
        return user;
    }
    //测试类，每次在数据库更新用户信息的时候，必须要删除redis中的用户信息，这样在下一次登录的时候，redis中会创建新的用户的值

    @Override
    public RespBean updatePassword(String userTicket, Long id, String password) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new GlobalException(RespBeanEnum.MOBILE_NOT_EXIST);
        }
        user.setPassword(MD5Util.inputPassToDBPass(password, user.getSlat()));
        int result = userMapper.updateById(user);
        if (1 == result) {
//删除Redis
            redisTemplate.delete("user:" + userTicket);
            return RespBean.success();
        }
        return RespBean.error(RespBeanEnum.PASSWORD_UPDATE_FAIL);

    }

    @Override
    public RespBean register(HttpServletRequest request, HttpServletResponse response, LoginVo loginVo) {

        String mobile = loginVo.getMobile();

        String password = loginVo.getPassword();
        System.out.println("注册的手机号码是：" + mobile);
        System.out.println("注册的密码是：" + password);
        String salt = "1a2b3c";
        password = MD5Util.formPassToDBPass(password, salt);
        User user = new User();
        user.setId(Long.valueOf(mobile));
        user.setNickname("user_"+mobile);
        user.setPassword(password);
        user.setSlat(salt);
        user.setRegisterDate(new Date());
        System.out.println("注册的用户信息：" + user);
        if(userMapper.selectById(mobile)!=null){
            throw new GlobalException(RespBeanEnum.REGISTER_FAIL);
        }





        int result = userMapper.insert(user);
        /*添加commit*/

        System.out.println("插入的结果是：" + result);


        if (1 == result) {
            String ticket = UUIDUtil.uuid();
            System.out.println("生成的ticket是："+ticket);
//        request.getSession().setAttribute(ticket,user);
            //把用户数据存入redis,key="user:"+ticket,value=user
            redisTemplate.opsForValue().set("user:" + ticket, user);
            CookieUtil.setCookie(request, response, "userTicket", ticket);
            return RespBean.success();
        }
        System.out.println("注册失败！");
        throw new GlobalException(RespBeanEnum.REGISTER_FAIL);
    }
}

