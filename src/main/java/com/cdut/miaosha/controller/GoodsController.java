package com.cdut.miaosha.controller;

import com.cdut.miaosha.entity.MiaoshaUser;
import com.cdut.miaosha.redis.GoodsKey;
import com.cdut.miaosha.redis.RedisService;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.context.webflux.SpringWebFluxContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    RedisService redisService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    /**
     * 未优化前： QPS——757
     * 5000 * 2
     * 加缓存：QPS——1105
     */
    @RequestMapping(value = "/to_list",produces = "text/html")
    @ResponseBody
    public String toList(HttpServletRequest request, HttpServletResponse response,Model model, MiaoshaUser user){

        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if(!StringUtils.isEmpty(html)){
            return html;
        }
        model.addAttribute("user",user);
        List<GoodsVo> goodsVos = goodsService.listGoodsVo();
        model.addAttribute("goodsList",goodsVos);
        //return "goods_list";

        WebContext ctx = new WebContext(request,response,request.getServletContext(),request.getLocale(),model.asMap());
        //手动渲染
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list",ctx);
        if(!StringUtils.isEmpty(html)){
            redisService.set(GoodsKey.getGoodsList,"",html);
        }
        return html;
    }

    @RequestMapping(value = "/to_detail/{goodsId}",produces = "text/html")
    @ResponseBody
    public String detail(HttpServletRequest request, HttpServletResponse response,Model model, MiaoshaUser user, @PathVariable("goodsId") long goodsId){

        String html = redisService.get(GoodsKey.getGoodsDetail, String.valueOf(goodsId), String.class);
        if(!StringUtils.isEmpty(html)){
            return html;
        }
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

        //return "goods_detail";

        WebContext ctx = new WebContext(request,response,request.getServletContext(),request.getLocale(),model.asMap());
        //手动渲染
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail",ctx);
        if(!StringUtils.isEmpty(html)){
            redisService.set(GoodsKey.getGoodsDetail,String.valueOf(goodsId),html);
        }
        return html;

    }


}
