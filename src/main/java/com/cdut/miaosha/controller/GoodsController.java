package com.cdut.miaosha.controller;

import com.cdut.miaosha.entity.MiaoshaUser;
import com.cdut.miaosha.service.GoodsService;
import com.cdut.miaosha.service.MiaoshaUserService;
import com.cdut.miaosha.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/to_list")
    public String toList(Model model,MiaoshaUser user){

        model.addAttribute("user",user);
        System.out.println(user.getId());
        List<GoodsVo> goodsVos = goodsService.listGoodsVo();
        model.addAttribute("goodsList",goodsVos);
        return "goods_list";

    }

    @RequestMapping("/to_detail/{goodsId}")
    public String detail(Model model, MiaoshaUser user, @PathVariable("goodsId") long goodsId){

        model.addAttribute("user",user);

        GoodsVo goodsVo = goodsService.getGoodsByGoodsId(goodsId);
        model.addAttribute("goods",goodsVo);

        System.out.println(goodsVo.getStartDate());
        int miaoShaStatus = 0;
        int remainSeconds = 0;
        long startAt = goodsVo.getStartDate().getTime();
        long endAt = goodsVo.getEndDate().getTime();
        long nowTime = System.currentTimeMillis();
        if(nowTime < startAt){//秒杀未开始，倒计时
            miaoShaStatus = 0;
            remainSeconds = (int)(startAt-nowTime)/1000;
        }else if(nowTime > endAt){//秒杀已结束
            miaoShaStatus = 2;
            remainSeconds = -1;
        }else{//秒杀正在进行中
            miaoShaStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("miaoshaStatus",miaoShaStatus);
        model.addAttribute("remainSeconds",remainSeconds);

        return "goods_detail";

    }

}
