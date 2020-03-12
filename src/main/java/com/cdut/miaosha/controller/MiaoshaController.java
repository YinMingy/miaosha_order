package com.cdut.miaosha.controller;

import com.cdut.miaosha.entity.MiaoshaOrder;
import com.cdut.miaosha.entity.MiaoshaUser;
import com.cdut.miaosha.entity.OrderInfo;
import com.cdut.miaosha.result.CodeMsg;
import com.cdut.miaosha.service.GoodsService;
import com.cdut.miaosha.service.MiaoshaService;
import com.cdut.miaosha.service.MiaoshaUserService;
import com.cdut.miaosha.service.OrderService;
import com.cdut.miaosha.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author ：yinmy
 * @date ：Created on 2020/2/24 14:46
 */

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {

    Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    /**
     * QPS:539/507
     * 5000 *  2
     */
    @RequestMapping("/do_miaosha")
    public String toList(Model model, MiaoshaUser user, @RequestParam("goodsId")long goodsId){

        model.addAttribute("user",user);

        if(user == null){
            return "login";
        }
        //判断库存
        GoodsVo goodsVo = goodsService.getGoodsByGoodsId(goodsId);
        int stock = goodsVo.getStockCount();
        if(stock <= 0){
            model.addAttribute("errorMsg", CodeMsg.MIAO_SHA_OVER.getMsg());
            return "miaosha_fail";
        }

        //判断用户有没有秒杀过
        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(),goodsId);
        if(miaoshaOrder != null){
            model.addAttribute("errorMsg",CodeMsg.REPEATE_MIAOSHA.getMsg());
            return "miaosha_fail";
        }

        //1.减库存  2.下订单  3.写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(user,goodsVo);
        model.addAttribute("orderInfo",orderInfo);
        model.addAttribute("goods",goodsVo);

        return "order_detail";

    }

}
