package com.test.sdk.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

public class SignUtil {

    public static String getSign(Map<String, String[]> params) {
        if (params == null || params.isEmpty()) {
            return "";
        }
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);//升序
        StringBuilder builder = new StringBuilder();
        for (String key : keys) {
            if("sign".equals(key)){
                continue;
            }
            String[] values = params.get(key);//参数对应的值

            if (values != null && values.length > 0) {
                builder.append("&");
                builder.append(key).append("=");
                try {
                    builder.append(URLEncoder.encode(values[0],"utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return builder.substring(1);
    }

    public static void main(String[] args) {
        Map<String, String[]> params=new HashMap<>();
        params.put("rsdk",new String[]{"abcx"});
        params.put("abc",new String[]{"xxxx"});
        params.put("rtre",new String[]{"wqeq"});
        params.put("sign",new String[]{"4b49556367bb8c600b53937d4c5ebdb0"});
        String str=getSign(params);
        System.out.println(str);
        System.out.println(DigestUtils.getMD5(str));
    }
}
