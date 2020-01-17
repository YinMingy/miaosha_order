package com.cdut.miaosha.service;

import com.cdut.miaosha.dao.MiaoshaUserDao;
import com.cdut.miaosha.entity.MiaoshaUser;
import com.cdut.miaosha.exception.GlobalException;
import com.cdut.miaosha.result.CodeMsg;
import com.cdut.miaosha.util.MD5Util;
import com.cdut.miaosha.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：yinmy
 * @date ：Created on 2020/1/16 15:18
 */
@Service
public class MiaoshaUserService {

    @Autowired
    MiaoshaUserDao miaoshaUserDao;

    public MiaoshaUser getById(long id){
        return miaoshaUserDao.getById(id);
    }

    public boolean login(LoginVo loginVo) {
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
        return true;
    }
}
