package com.cdut.miaosha.controller;

import com.cdut.miaosha.result.CodeMsg;
import com.cdut.miaosha.result.Result;
import com.cdut.miaosha.service.MiaoshaUserService;
import com.cdut.miaosha.util.VaildatorUil;
import com.cdut.miaosha.vo.LoginVo;
import com.mysql.cj.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * @author ：yinmy
 * @date ：Created on 2020/1/13 15:27
 */

@Controller
@RequestMapping("/login")
public class LoginController {

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    MiaoshaUserService miaoshaUserService;

    @RequestMapping("/to_login")
    public String toLogin(Model model){

        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(@Valid LoginVo loginVo){

        logger.info(loginVo.toString());
        // 1. 参数校验
//        String passInput = loginVo.getPassword();
//        String mobile = loginVo.getMobile();
//        if(StringUtils.isEmptyOrWhitespaceOnly(passInput)){
//            return Result.error(CodeMsg.PASSWORD_EMPTY);
//        }
//        if(StringUtils.isEmptyOrWhitespaceOnly(mobile)){
//            return Result.error(CodeMsg.MOBILE_EMPTY);
//        }
//        if(!VaildatorUil.isMobile(mobile)){
//            return Result.error(CodeMsg.MOBILE_ERROR);
//        }
        CodeMsg cm = miaoshaUserService.login(loginVo);
        if(cm.getCode() == 0){
            return Result.success(true);
        }else {
            return Result.error(cm);
        }
    }

}
