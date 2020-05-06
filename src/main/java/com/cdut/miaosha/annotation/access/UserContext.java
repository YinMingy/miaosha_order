package com.cdut.miaosha.annotation.access;

import com.cdut.miaosha.entity.MiaoshaUser;

/**
 * @author ：yinmy
 * @date ：Created on 2020/4/9 21:09
 */
public class UserContext {

    private static ThreadLocal<MiaoshaUser> userHolder = new ThreadLocal<>();

    public static void setUser(MiaoshaUser user){
        userHolder.set(user);
    }

    public static MiaoshaUser getUser(){
        return userHolder.get();
    }
}
