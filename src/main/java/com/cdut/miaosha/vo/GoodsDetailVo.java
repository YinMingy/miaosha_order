package com.cdut.miaosha.vo;

import com.cdut.miaosha.entity.MiaoshaUser;

/**
 * @author ：yinmy
 * @date ：Created on 2020/3/13 13:37
 */
public class GoodsDetailVo {
    private int miaoShaStatus = 0;
    private int remainSeconds = 0;
    private GoodsVo goods;
    private MiaoshaUser user;

    public int getMiaoShaStatus() {
        return miaoShaStatus;
    }

    public void setMiaoShaStatus(int miaoShaStatus) {
        this.miaoShaStatus = miaoShaStatus;
    }

    public int getRemainSeconds() {
        return remainSeconds;
    }

    public void setRemainSeconds(int remainSeconds) {
        this.remainSeconds = remainSeconds;
    }

    public GoodsVo getGoods() {
        return goods;
    }

    public void setGoods(GoodsVo goods) {
        this.goods = goods;
    }

    public MiaoshaUser getUser() {
        return user;
    }

    public void setUser(MiaoshaUser user) {
        this.user = user;
    }
}
