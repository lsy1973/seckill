package com.xxxx.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.seckill.pojo.User;
import com.xxxx.seckill.vo.LoginVo;
import com.xxxx.seckill.vo.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author linksy
 * @since 2022-07-22
 */
public interface IUserService extends IService<User> {



    RespBean login(HttpServletRequest request, HttpServletResponse response, LoginVo loginVo);

    User getUserByCookie(String userTicket,HttpServletRequest request,HttpServletResponse response);


    RespBean updatePassword(String userTicket,Long id,String password);

}
