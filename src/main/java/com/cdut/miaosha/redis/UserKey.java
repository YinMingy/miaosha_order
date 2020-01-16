package com.cdut.miaosha.redis;

/**
 * @author ：yinmy
 * @date ：Created on 2020/1/15 14:17
 */
public class UserKey extends BasePrefix {

    private UserKey(String prefix) {
        super(prefix);
    }

    public static UserKey getById = new UserKey("id");
    public static UserKey getByName = new UserKey("name");


}
