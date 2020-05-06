package com.cdut.miaosha.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cdut.miaosha.result.CodeMsg;
import com.cdut.miaosha.result.Result;
import com.cdut.miaosha.result.TokenResult;
import com.cdut.miaosha.result.UserResult;
import com.cdut.miaosha.service.MiaoshaUserService;
import com.cdut.miaosha.service.UserService;
import com.cdut.miaosha.util.MD5Util;
import com.cdut.miaosha.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author ：yinmy
 * @date ：Created on 2020/1/13 15:27
 */

@Controller
@RequestMapping("/login")
public class LoginController {

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    MiaoshaUserService miaoshaUserService;

    @RequestMapping("/to_login")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<String> doLogin(HttpServletResponse response,@Valid LoginVo loginVo){

        String token = miaoshaUserService.login(response,loginVo);
        return Result.success(token);

    } @RequestMapping("/callback")
    @ResponseBody
    public String callback(HttpServletRequest request,HttpServletResponse response){// @Valid LoginVo loginVo) Result<Boolean>

        String code = request.getParameter("code");
        System.out.println("得到的code是"+code);
        try {
            System.out.println("请求token的时间戳是"+System.currentTimeMillis());

            String getTokenRes = getResultByHttp("http://47.96.187.200/profile/oauth2/accessToken",getAccessTokenParam("WdRq4UAMjB", "56cf4fbd-3219-425d-ba46-07a238287003",
                    "http://localhost:8080/login/do_login", code));
            TokenResult tokenResult = JSON.parseObject(getTokenRes,TokenResult.class);

            String token = tokenResult.getAccess_token();

            String getUserRes = getResultByHttp("http://47.96.187.200/profile/oauth2/profile",getUserParam("WdRq4UAMjB", "56cf4fbd-3219-425d-ba46-07a238287003",token));
            System.out.println(getUserRes);

            UserResult userResult = JSON.parseObject(getUserRes, UserResult.class);
            System.out.println("当前用户id为："+userResult.getId());
            if(userResult.getStatus() == 200){
                return "当前用户为："+userResult.getId();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return "登录失败";
    }

    private static String getResultByHttp(String sendUrl, String param)
            throws NoSuchAlgorithmException, KeyManagementException,
            IOException {

        HttpURLConnection conn = null;
        OutputStream out = null;
        BufferedReader reader = null;
        String result = "";
        try {

            URL url = new URL(sendUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(150000);
            conn.connect();
            out = conn.getOutputStream();
            out.write(param.getBytes());
            out.flush();
            out.close();
            InputStream input = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
            String line;
            StringBuffer sb = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            result = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            out.close();
            conn.disconnect();
        }

        return result;
    }
    private static String radomString(){
        String result="";
        for(int i=0;i<10;i++){
            int intVal=(int)(Math.random()*26+97);
            result=result+(char)intVal;
        }
        return result;
    }

    public static String getSign(Map<String, String> params,String secret)
    {
        String sign="";
        StringBuilder sb = new StringBuilder();
        //排序
        Set<String> keyset=params.keySet();
        TreeSet<String> sortSet=new TreeSet<String>();
        sortSet.addAll(keyset);
        Iterator<String> it=sortSet.iterator();
        //加密字符串
        while(it.hasNext())
        {
            String key=it.next();
            String value=params.get(key);
            sb.append(key).append(value);
        }
        sb.append("appkey").append(secret);
        try {
            sign=  MD5Util.md5(sb.toString()).toUpperCase();
        } catch (Exception e) {
        }
        return sign;
    }
    /**
     * 组装获取token api参数 含签名
     * @param client_ID
     * @param client_secret
     * @param redirect_uri
     * @param code
     * @return
     */
    public static String getAccessTokenParam(String client_ID, String client_secret, String redirect_uri, String code) {

        String nonce_str = radomString();

        String appkey="56eea6c8e76fc4262a4a2816dfd79c7fdb4781a9433da5509d3ee649125447d8";
        long timestamp= System.currentTimeMillis();
        Map<String, String> params = new HashMap<String, String>();

        params.put("client_id", client_ID);
        params.put("client_secret", client_secret);
        params.put("nonce_str", nonce_str);
        params.put("oauth_timestamp", String.valueOf(timestamp));
        params.put("code", code);
        params.put("redirect_uri",redirect_uri);
        params.put("grant_type", "authorization_code");
        String sign = getSign(params, appkey+client_secret);
        StringBuffer tokenParam = new StringBuffer();
        for (String key : params.keySet()) {
            if(tokenParam.length()==0){
                tokenParam.append(key).append("=").append(params.get(key));
            }else{
                tokenParam.append("&").append(key).append("=").append(params.get(key));
            }

        }
        tokenParam.append("&sign=").append(sign);
        System.out.println(tokenParam.toString());
        return tokenParam.toString();
    }

    public static String getUserParam(String client_ID,String client_secret,String token){
        String nonce_str = radomString();
        String appkey="56eea6c8e76fc4262a4a2816dfd79c7fdb4781a9433da5509d3ee649125447d8";

        long timestamp= System.currentTimeMillis();
        Map<String, String> params = new HashMap<String, String>();

        params.put("client_id", client_ID);
        params.put("client_secret", client_secret);
        params.put("access_token",token);
        params.put("oauth_timestamp", String.valueOf(timestamp));
        params.put("nonce_str", nonce_str);
        String sign = getSign(params, appkey+client_secret);
        StringBuffer userParam = new StringBuffer();
        for (String key : params.keySet()) {
            if(userParam.length()==0){
                userParam.append(key).append("=").append(params.get(key));
            }else{
                userParam.append("&").append(key).append("=").append(params.get(key));
            }

        }
        userParam.append("&sign=").append(sign);
        System.out.println(userParam.toString());
        return userParam.toString();
    }
}
