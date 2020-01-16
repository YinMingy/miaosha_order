package com.cdut.miaosha.vo;

import com.cdut.miaosha.validator.IsMobile;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author ：yinmy
 * @date ：Created on 2020/1/16 14:33
 */
public class LoginVo {

    @NotNull
    @IsMobile
    private String mobile;

    @NotNull
    @Length(min = 32)
    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobil) {
        this.mobile = mobil;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginVo{" +
                "mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
