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
            //统一拦截（查询当前session是否存在user(这里user会在每次登录成功后，写入session
            MiaoshaUser user = (MiaoshaUser)request.getSession().getAttribute("token");
            if(user != null){
                return true;
            }
            response.sendRedirect("http://47.96.187.200/");
        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

}
