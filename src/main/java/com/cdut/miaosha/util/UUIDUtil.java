package com.cdut.miaosha.util;

import java.util.UUID;

/**
 * @author ：yinmy
 * @date ：Created on 2020/1/17 14:40
 */
public class UUIDUtil {
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
