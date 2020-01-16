package com.cdut.miaosha.redis;

/**
 * @author ：yinmy
 * @date ：Created on 2020/1/15 14:18
 */
public class OrderKey extends BasePrefix {

    public OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}
