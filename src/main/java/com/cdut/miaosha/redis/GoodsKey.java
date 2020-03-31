package com.cdut.miaosha.redis;

/**
 * @author ：yinmy
 * @date ：Created on 2020/3/11 18:43
 */
public class GoodsKey extends BasePrefix {
    private GoodsKey(int expireSeconds,String prefix) {
        super(expireSeconds,prefix);
    }

    public static GoodsKey getGoodsList = new GoodsKey(60,"gl");
    public static GoodsKey getGoodsDetail = new GoodsKey(60,"gd");
    public static GoodsKey getMiaoshaGoodsStock = new GoodsKey(0,"gs");

}
