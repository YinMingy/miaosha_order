//package com.cdut.miaosha.config;
//
//import com.cdut.miaosha.Interceptor.LoginInterceptor;
//import jdk.nashorn.internal.ir.annotations.Ignore;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * @author ：yinmy
// * @date ：Created on 2020/2/12 15:42
// */
//
//@Configuration
//public class LoginConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
//        // 注册LoginInterception拦截器
//        InterceptorRegistration registration = registry.addInterceptor(new LoginInterceptor());
//        // 所有路径都被拦截
//        registration.addPathPatterns("/login/to_login");
//        // 添加不拦截路径
//        registration.excludePathPatterns("/**/*.html","/**/*.js","/**/*.css");
//    }
//}
