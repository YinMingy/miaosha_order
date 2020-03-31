package com.cdut.miaosha.service;

import com.cdut.miaosha.dao.OrderDao;
import com.cdut.miaosha.entity.MiaoshaOrder;
import com.cdut.miaosha.entity.MiaoshaUser;
import com.cdut.miaosha.entity.OrderInfo;
import com.cdut.miaosha.redis.OrderKey;
import com.cdut.miaosha.redis.RedisService;
import com.cdut.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author ：yinmy
 * @date ：Created on 2020/2/24 14:56
 */

@Service
public class OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    RedisService redisService;

    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(long userId, long goodsId) {

        //return orderDao.getMiaoshaOrderByUserIdGoodsId(userId,goodsId);
        return redisService.get(OrderKey.getMiaoshaOrderByUidGid,""+userId+"_"+goodsId,MiaoshaOrder.class);

    }

    public OrderInfo getOrderById(long orderId) {
        return orderDao.getOrderById(orderId);
    }

    @Transactional
    public OrderInfo createOrder(MiaoshaUser user, GoodsVo goodsVo) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0l);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goodsVo.getId());
        orderInfo.setGoodsName(goodsVo.getGoodsName());
        orderInfo.setGoodsPrice(goodsVo.getMiaoshaPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());

        orderDao.insert(orderInfo);
        long orderId = orderInfo.getId();

        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goodsVo.getId());
        miaoshaOrder.setUserId(user.getId());
        miaoshaOrder.setOrderId(orderId);
        orderDao.insertMiaoshaOrder(miaoshaOrder);

        redisService.set(OrderKey.getMiaoshaOrderByUidGid,""+user.getId()+"_"+goodsVo.getId(),miaoshaOrder);
        return orderInfo;
    }

    public void deleteOrders() {
        orderDao.deleteOrders();
        orderDao.deleteMiaoshaOrders();
    }

}
