package com.cdut.miaosha.rabbitmq;

import com.cdut.miaosha.entity.MiaoshaUser;

/**
 * @author ：yinmy
 * @date ：Created on 2020/3/30 18:08
 */
public class MiaoshaMessage {
    private MiaoshaUser user;
    private long goodsId;

    public MiaoshaUser getUser() {
        return user;
    }

    public void setUser(MiaoshaUser user) {
        this.user = user;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }
}
