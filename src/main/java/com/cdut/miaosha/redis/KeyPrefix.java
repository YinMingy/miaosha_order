package com.cdut.miaosha.redis;

/**
 * @author ：yinmy
 * @date ：Created on 2020/1/14 17:53
 */
public interface KeyPrefix {

    public int expireSeconds();

    public String getPrefix();

}
