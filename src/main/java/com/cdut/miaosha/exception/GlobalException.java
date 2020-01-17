package com.cdut.miaosha.exception;

import com.cdut.miaosha.result.CodeMsg;

/**
 * @author ：yinmy
 * @date ：Created on 2020/1/17 11:30
 */
public class GlobalException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private CodeMsg codeMsg;

    public GlobalException(CodeMsg codeMsg){

        super(codeMsg.toString());
        this.codeMsg = codeMsg;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }

}
