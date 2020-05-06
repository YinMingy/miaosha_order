package com.cdut.miaosha.redis;

/**
 * @author ：yinmy
 * @date ：Created on 2020/3/30 21:25
 */
public class MiaoshaKey extends BasePrefix {

    private MiaoshaKey(int expire,String prefix) {
        super(expire,prefix);
    }

    public static MiaoshaKey isGoodsOver = new MiaoshaKey(0,"go");
    public static MiaoshaKey getMiaoshaPath = new MiaoshaKey(60,"mp");
    public static MiaoshaKey getMiaoshaVerifyCode = new MiaoshaKey(300,"vc");



}
