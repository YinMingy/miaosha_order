package com.cdut.miaosha.controller;

import com.cdut.miaosha.result.CodeMsg;
import com.cdut.miaosha.result.Result;
import com.cdut.miaosha.service.MiaoshaUserService;
import com.cdut.miaosha.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
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
    public Result<Boolean> doLogin(HttpServletResponse response, @Valid LoginVo loginVo){

        logger.info(loginVo.toString());
        miaoshaUserService.login(response,loginVo);
        return Result.success(true);
    }

}
