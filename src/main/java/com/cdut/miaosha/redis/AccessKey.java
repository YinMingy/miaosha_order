package com.cdut.miaosha.redis;

/**
 * @author ：yinmy
 * @date ：Created on 2020/3/30 21:25
 */
public class AccessKey extends BasePrefix {

    private AccessKey(int expire, String prefix) {
        super(expire,prefix);
    }

    public static AccessKey withAccess(int expire){
        return new AccessKey(expire,"access");
    }


}
