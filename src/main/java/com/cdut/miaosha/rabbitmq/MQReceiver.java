package com.cdut.miaosha.rabbitmq;

import com.cdut.miaosha.entity.MiaoshaOrder;
import com.cdut.miaosha.entity.MiaoshaUser;
import com.cdut.miaosha.redis.RedisService;
import com.cdut.miaosha.service.GoodsService;
import com.cdut.miaosha.service.MiaoshaService;
import com.cdut.miaosha.service.OrderService;
import com.cdut.miaosha.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.Action;

/**
 * @author ：yinmy
 * @date ：Created on 2020/3/26 21:01
 */

@Service
public class MQReceiver {

    private Logger log = LoggerFactory.getLogger(MQReceiver.class);

    @Autowired
    GoodsService goodsService;

    @Autowired
    RedisService redisService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @RabbitListener(queues = MQConfig.MIAOSHA_QUEUE)
    public void receiveMiaosha(String message){
        log.info("receive miaosha_message :"+message);
        MiaoshaMessage miaoshaMessage = RedisService.stringToBean(message, MiaoshaMessage.class);
        MiaoshaUser user = miaoshaMessage.getUser();
        long goodsId = miaoshaMessage.getGoodsId();

        GoodsVo goods = goodsService.getGoodsByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if(stock <= 0){
            return;
        }
        //判断用户有没有秒杀过
        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(),goodsId);
        if(miaoshaOrder != null){
            return;
        }
        //生成秒杀订单
        miaoshaService.miaosha(user,goods);

    }

    /*@RabbitListener(queues = MQConfig.QUEUE)
    public void revice(String message){
        log.info("receiver message :"+ message);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
    public void reviceTopic1(String message){
        log.info("topic queue1 message :"+ message);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
    public void reviceTopic2(String message){
        log.info("topic queue2 message :"+ message);
    }

    @RabbitListener(queues = MQConfig.HEADERS_QUEUE)
    public void reviceHeader(byte[] message){
        log.info("header queue message :"+ new String(message));
    }*/



}

