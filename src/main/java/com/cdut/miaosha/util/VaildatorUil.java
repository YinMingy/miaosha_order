package com.cdut.miaosha.util;

import com.mysql.cj.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ：yinmy
 * @date ：Created on 2020/1/16 15:04
 */
public class VaildatorUil {

    private static final Pattern mobile_pattern = Pattern.compile("1\\d{10}");

    public static boolean isMobile(String src){
        if(StringUtils.isEmptyOrWhitespaceOnly(src)){
            return false;
        }

        Matcher m = mobile_pattern.matcher(src);
        return m.matches();

    }

    /*public static void main(String[] args) {
        boolean mobile = isMobile("22312341234");
        System.out.println(mobile);
    }*/
}
