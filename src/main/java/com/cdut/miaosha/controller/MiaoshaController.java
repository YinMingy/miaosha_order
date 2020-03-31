package com.cdut.miaosha.controller;

import com.cdut.miaosha.entity.MiaoshaOrder;
import com.cdut.miaosha.entity.MiaoshaUser;
import com.cdut.miaosha.entity.OrderInfo;
import com.cdut.miaosha.rabbitmq.MQSender;
import com.cdut.miaosha.rabbitmq.MiaoshaMessage;
import com.cdut.miaosha.redis.GoodsKey;
import com.cdut.miaosha.redis.MiaoshaKey;
import com.cdut.miaosha.redis.OrderKey;
import com.cdut.miaosha.redis.RedisService;
import com.cdut.miaosha.result.CodeMsg;
import com.cdut.miaosha.result.Result;
import com.cdut.miaosha.service.GoodsService;
import com.cdut.miaosha.service.MiaoshaService;
import com.cdut.miaosha.service.MiaoshaUserService;
import com.cdut.miaosha.service.OrderService;
import com.cdut.miaosha.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：yinmy
 * @date ：Created on 2020/2/24 14:46
 */

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController implements InitializingBean {

    Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    GoodsService goodsService;

    @Autowired
    RedisService redisService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @Autowired
    MQSender sender;

    private Map<Long,Boolean> localOverMap = new HashMap<>();

    /**
     * 系统初始化
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        if(goodsList == null){
            return;
        }
        for(GoodsVo goodsVo: goodsList){
            redisService.set(GoodsKey.getMiaoshaGoodsStock,""+goodsVo.getId(),goodsVo.getStockCount());
            localOverMap.put(goodsVo.getId(),false);
        }
    }

    /**
     * QPS:453
     * 5000 *  2
     */
    /**
     * GET \ POST 有什么区别
     * GET 幂等
     * POST 向服务端提交数据
     */
    @RequestMapping(value = "/do_miaosha",method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> miaosha(Model model, MiaoshaUser user, @RequestParam("goodsId")long goodsId){
        model.addAttribute("user",user);

        if(user == null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        //内存标记,减少内存标记
        Boolean isOver = localOverMap.get(goodsId);
        if(isOver){
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        //预减库存
        long stock = redisService.decr(GoodsKey.getMiaoshaGoodsStock,""+goodsId);
        if(stock <= 0 ){
            localOverMap.put(goodsId,true);
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        //判断是否秒杀到了
        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(),goodsId);
        if(miaoshaOrder != null){
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }
        //入队
        MiaoshaMessage mm = new MiaoshaMessage();
        mm.setUser(user);
        mm.setGoodsId(goodsId);
        sender.sendMiaoshaMessage(mm);

        return Result.success(0);//排队中

        /*//判断库存
        GoodsVo goodsVo = goodsService.getGoodsByGoodsId(goodsId);
        int stock = goodsVo.getStockCount();
        if(stock <= 0){
            model.addAttribute("errorMsg", CodeMsg.MIAO_SHA_OVER.getMsg());
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }

        //判断用户有没有秒杀过
        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(),goodsId);
        if(miaoshaOrder != null){
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }

        //1.减库存  2.下订单  3.写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(user,goodsVo);

        return Result.success(orderInfo);*/

    }

    /*
     * orderId:成功
     * -1：秒杀失败
     * 0：排队中
     */
    @RequestMapping(value = "/result",method = RequestMethod.GET)
    @ResponseBody
    public Result<Long> miaoshaResult(Model model, MiaoshaUser user, @RequestParam("goodsId")long goodsId) {
        model.addAttribute("user", user);

        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        long result = miaoshaService.getMiaoshaResult(user.getId(), goodsId);
        return Result.success(result);
    }

    @RequestMapping(value="/reset", method=RequestMethod.GET)
    @ResponseBody
    public Result<Boolean> reset(Model model) {
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        for(GoodsVo goods : goodsList) {
            goods.setStockCount(10);
            redisService.set(GoodsKey.getMiaoshaGoodsStock, ""+goods.getId(), 10);
            localOverMap.put(goods.getId(), false);
        }
        redisService.delete(OrderKey.getMiaoshaOrderByUidGid);
        redisService.delete(MiaoshaKey.isGoodsOver);
        miaoshaService.reset(goodsList);
        return Result.success(true);
    }
}
