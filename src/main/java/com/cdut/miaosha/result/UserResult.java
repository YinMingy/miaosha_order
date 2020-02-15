package com.cdut.miaosha.result;

import java.util.List;

/**
 * @author ：yinmy
 * @date ：Created on 2020/2/15 13:56
 */
public class UserResult {
    private int status;
    private String id;
    private String msg;
    private List<ll> attributes;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ll> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ll> attributes) {
        this.attributes = attributes;
    }
}
class ll{
    private List<String> language;

    public List<String> getLanguage() {
        return language;
    }

    public void setLanguage(List<String> language) {
        this.language = language;
    }
}