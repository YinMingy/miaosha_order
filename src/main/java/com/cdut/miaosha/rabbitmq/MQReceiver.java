package com.cdut.miaosha.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author ：yinmy
 * @date ：Created on 2020/3/26 21:01
 */

@Service
public class MQReceiver {

    private Logger log = LoggerFactory.getLogger(MQReceiver.class);


    @RabbitListener(queues = MQConfig.QUEUE)
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
    }



}

