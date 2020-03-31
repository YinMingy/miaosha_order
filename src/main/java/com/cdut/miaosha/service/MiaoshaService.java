package com.cdut.miaosha.service;

import com.cdut.miaosha.dao.GoodsDao;
import com.cdut.miaosha.entity.*;
import com.cdut.miaosha.redis.MiaoshaKey;
import com.cdut.miaosha.redis.RedisService;
import com.cdut.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Autowired
    RedisService redisService;

    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goodsVo) {
        //1.减库存  2.下订单  3.写入秒杀订单
        boolean isReduce = goodsService.reduceStock(goodsVo);
        if(isReduce){
            // order_info miaosha_order
            return orderService.createOrder(user,goodsVo);
        }else {
            setGoodsOver(goodsVo.getId());
            return null;
        }

    }

    private void setGoodsOver(Long id) {
        redisService.set(MiaoshaKey.isGoodsOver,""+id,true);
    }
    private boolean getGoodsOver(long goodsId) {
        return redisService.exists(MiaoshaKey.isGoodsOver, "" + goodsId);

    }
    public long getMiaoshaResult(Long userId, long goodsId) {
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
        if(order != null){//秒杀成功
            return order.getOrderId();
        }else{
            boolean isOver = getGoodsOver(goodsId);
            if(isOver){
                return -1;
            }else{
                return 0;
            }
        }
    }

    public void reset(List<GoodsVo> goodsList) {
        goodsService.resetStock(goodsList);
        orderService.deleteOrders();
    }


}
