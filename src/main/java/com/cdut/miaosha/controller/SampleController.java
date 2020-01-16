package com.cdut.miaosha.controller;

import com.cdut.miaosha.entity.User;
import com.cdut.miaosha.redis.RedisService;
import com.cdut.miaosha.redis.UserKey;
import com.cdut.miaosha.result.Result;
import com.cdut.miaosha.service.MiaoshaUserService;
import com.cdut.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ：yinmy
 * @date ：Created on 2020/1/13 15:27
 */

@Controller
@RequestMapping("/demo")
public class SampleController {

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model){
        model.addAttribute("name","yinmy");
        return "hello";
    }
    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> doGet(){

        User user =  userService.getById(1);
        return Result.success(user);
    }

    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> doTx(){
        userService.tx();
        return Result.success(true);
    }
    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<User> redisGet(){
        User v1 = redisService.get(UserKey.getById,""+1,User.class);
        return Result.success(v1);
    }
    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> redisSet(){
        User user = new User();
        user.setId(1);
        user.setName("1111");
        redisService.set(UserKey.getById,""+1, user);
        return Result.success(true);
    }
}
