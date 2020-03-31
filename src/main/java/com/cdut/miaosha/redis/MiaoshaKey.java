package com.cdut.miaosha.redis;

/**
 * @author ：yinmy
 * @date ：Created on 2020/3/30 21:25
 */
public class MiaoshaKey extends BasePrefix {

    private MiaoshaKey(String prefix) {
        super(prefix);
    }

    public static MiaoshaKey isGoodsOver = new MiaoshaKey("go");

}
