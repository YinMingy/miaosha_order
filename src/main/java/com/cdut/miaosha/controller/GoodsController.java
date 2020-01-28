package com.cdut.miaosha.controller;

import com.cdut.miaosha.entity.MiaoshaUser;
import com.cdut.miaosha.result.Result;
import com.cdut.miaosha.service.GoodsService;
import com.cdut.miaosha.service.MiaoshaUserService;
import com.cdut.miaosha.vo.GoodsVo;
import com.cdut.miaosha.vo.LoginVo;
import com.mysql.cj.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * @author ：yinmy
 * @date ：Created on 2020/1/17 15:27
 */

@Controller
@RequestMapping("/goods")
public class GoodsController {

    Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    MiaoshaUserService miaoshaUserService;

    @Autowired
    GoodsService goodsService;

//    @RequestMapping("/to_list")
//    public String toList(Model model,MiaoshaUser user){
//
//        model.addAttribute("user",user);
//        return "goods_list";
//    }

    @RequestMapping("/to_list")
    public String toList(HttpServletResponse response,Model model,
                         @CookieValue(value = MiaoshaUserService.COOKIE_NAME_TOKEN,required = false) String cookie,
                         @RequestParam(value =MiaoshaUserService.COOKIE_NAME_TOKEN,required = false) String paramToken){
        if(StringUtils.isEmptyOrWhitespaceOnly(cookie)&&StringUtils.isEmptyOrWhitespaceOnly(paramToken)){
            return "login";
        }
        String token = StringUtils.isEmptyOrWhitespaceOnly(paramToken)?cookie:paramToken;
        MiaoshaUser miaoshaUser = miaoshaUserService.getByToken(response,token);
        model.addAttribute("user",miaoshaUser);
        //查询商品列表
        List<GoodsVo> goodsVos = goodsService.listGoodsVo();
        model.addAttribute("goodsList",goodsVos);

        return "goods_list";
    }

}
