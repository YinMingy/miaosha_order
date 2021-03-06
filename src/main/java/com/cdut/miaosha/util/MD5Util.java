package com.cdut.miaosha.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author ：yinmy
 * @date ：Created on 2020/1/15 16:38
 */
public class MD5Util {

    public static String md5(String str){
        return DigestUtils.md5Hex(str);
    }

    private static final String salt = "1a2b3c4d";

    public static String inputPassToFormPass(String inputPass){
        String i = ""+salt.charAt(0)+salt.charAt(2) + inputPass +salt.charAt(5) + salt.charAt(4);
        return md5(i);
    }

    public static String formPassToDBPass(String formPass,String salt){
        String i = ""+salt.charAt(0)+salt.charAt(2) + formPass +salt.charAt(5) + salt.charAt(4);
        return md5(i);
    }

    public static String inputPassToDBPass(String input,String DBSalt){
        String formPass = inputPassToFormPass(input);
        String DBPass = formPassToDBPass(formPass, DBSalt);
        return DBPass;
    }

}
