package com.cdut.miaosha.Interceptor;

import com.cdut.miaosha.entity.MiaoshaUser;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ：yinmy
 * @date ：Created on 2020/2/12 15:28
 */

/**
 * 单点登录拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //System.out.println("执行了LoginInterceptor的preHandle方法");
        try{
            long timestamp = System.currentTimeMillis();
            response.sendRedirect("http://47.96.187.200/profile/oauth2/authorize?client_id=WdRq4UAMjB" +
                    "&response_type=code&redirect_uri=http://localhost:8080/login/do_login&oauth_timestamp="+timestamp);
            System.out.println("创建code的时间戳是"+timestamp);
        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

}
