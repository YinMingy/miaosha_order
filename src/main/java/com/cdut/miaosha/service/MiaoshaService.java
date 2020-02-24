package com.cdut.miaosha.service;

import com.cdut.miaosha.dao.GoodsDao;
import com.cdut.miaosha.entity.Goods;
import com.cdut.miaosha.entity.MiaoshaGoods;
import com.cdut.miaosha.entity.MiaoshaUser;
import com.cdut.miaosha.entity.OrderInfo;
import com.cdut.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ：yinmy
 * @date ：Created on 2020/2/24 15:05
 */

@Service
public class MiaoshaService {


    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goodsVo) {
        //1.减库存  2.下订单  3.写入秒杀订单
        goodsService.reduceStock(goodsVo);

        // order_info miaosha_order
        return orderService.createOrder(user,goodsVo);

    }
}
