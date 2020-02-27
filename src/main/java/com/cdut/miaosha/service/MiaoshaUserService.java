package com.cdut.miaosha.service;

import com.cdut.miaosha.dao.MiaoshaUserDao;
import com.cdut.miaosha.entity.MiaoshaUser;
import com.cdut.miaosha.exception.GlobalException;
import com.cdut.miaosha.redis.MiaoshaUserKey;
import com.cdut.miaosha.redis.RedisService;
import com.cdut.miaosha.result.CodeMsg;
import com.cdut.miaosha.util.MD5Util;
import com.cdut.miaosha.util.UUIDUtil;
import com.cdut.miaosha.vo.LoginVo;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ：yinmy
 * @date ：Created on 2020/1/16 15:18
 */
@Service
public class MiaoshaUserService {

    public static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    MiaoshaUserDao miaoshaUserDao;

    @Autowired
    RedisService redisService;

    public MiaoshaUser getById(long id){
        return miaoshaUserDao.getById(id);
    }

    public MiaoshaUser getByToken(HttpServletResponse response,String token) {
        if(StringUtils.isEmptyOrWhitespaceOnly(token)){
            return null;
        }
        //先不返回，延长下有效期
        MiaoshaUser miaoshaUser = redisService.get(MiaoshaUserKey.token,token,MiaoshaUser.class);
        //判断用户是否为空
        if(miaoshaUser!=null){
            addCookie(response,token,miaoshaUser);

        }
        return miaoshaUser;
    }

    private void addCookie(HttpServletResponse response,String token,MiaoshaUser user){

        redisService.set(MiaoshaUserKey.token,token,user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN,token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public String login(HttpServletResponse response, LoginVo loginVo) {
        if(loginVo == null){
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        //判断手机号是否存在
        MiaoshaUser user = getById(Long.parseLong(mobile));
        if(user == null){
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //验证密码
        String dbPass = user.getPassword();
        String slatDB =  user.getSalt();
        String calcpass = MD5Util.formPassToDBPass(formPass, slatDB);
        if(!calcpass.equals(dbPass)){
            throw new GlobalException( CodeMsg.PASSWORD_ERROR);
        }
        //生成cookie
        String token = UUIDUtil.uuid();
        addCookie(response,token,user);
        return token;
    }

}
