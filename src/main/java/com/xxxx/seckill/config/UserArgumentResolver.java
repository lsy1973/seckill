package com.xxxx.seckill.config;

import com.xxxx.seckill.pojo.User;
import com.xxxx.seckill.service.IUserService;
import com.xxxx.seckill.service.impl.UserServiceImpl;
import com.xxxx.seckill.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.xxxx.seckill.utils.CookieUtil.getCookieValue;

@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private IUserService userService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> clazz = methodParameter.getParameterType();
        return clazz== User.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
//        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
//        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
//
//        String ticket = getCookieValue(request,"userTicket");
//        if(StringUtils.isEmpty(ticket)){
//            return null;
//        }
//        return userService.getUserByCookie(ticket,request,response);
        return UserContext.getUser();

    }
}
