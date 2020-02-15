package com.cdut.miaosha.result;

/**
 * @author ：yinmy
 * @date ：Created on 2020/2/15 13:22
 */
public class TokenResult {
    private int status;
    private String msg;
    private String access_token;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
