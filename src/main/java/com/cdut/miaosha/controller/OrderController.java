package com.cdut.miaosha.controller;

import com.cdut.miaosha.entity.MiaoshaUser;
import com.cdut.miaosha.entity.OrderInfo;
import com.cdut.miaosha.redis.RedisService;
import com.cdut.miaosha.result.CodeMsg;
import com.cdut.miaosha.result.Result;
import com.cdut.miaosha.service.GoodsService;
import com.cdut.miaosha.service.OrderService;
import com.cdut.miaosha.vo.GoodsVo;
import com.cdut.miaosha.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    RedisService redisService;

    @Autowired
    OrderService orderService;

    @Autowired
    GoodsService goodsService;

    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> info(Model model, MiaoshaUser user, @RequestParam("orderId") long orderId){
        if(user == null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        OrderInfo order = orderService.getOrderById(orderId);
        if(order == null){
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }
        long goodsId = order.getGoodsId();
        GoodsVo goods = goodsService.getGoodsByGoodsId(goodsId);

        OrderDetailVo orderDetailVo = new OrderDetailVo();
        orderDetailVo.setGoods(goods);
        orderDetailVo.setOrder(order);

        return Result.success(orderDetailVo);
    }

}
