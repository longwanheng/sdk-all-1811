package com.test.sdk.common.util;


import org.springframework.util.StringUtils;

public class AccountUtil {

    public static boolean checkUsername(String name) {
        if (StringUtils.isEmpty(name)) {//空的
            return false;
        }
        String reg = "^[a-zA-Z][a-zA-Z\\d]{5,31}$";
        return name.matches(reg);
    }

    public static boolean checkMobile(String phone) {
        if (StringUtils.isEmpty(phone)) {//空的
            return false;
        }
        String reg = "^1[3-8]\\d{9}$";
        return phone.matches(reg);
    }
    public static boolean checkPassword(String password) {
        if (StringUtils.isEmpty(password)) {//空的
            return false;
        }
        String reg = "^[a-zA-Z0-9]{6,20}$";
        return password.matches(reg);
    }


    public static int getAccountType(String account){
        if(checkMobile(account)){
            return SdkConstants.ACCOUNT_TYPE_NUM;
        }
        if(checkUsername(account)){
            return SdkConstants.ACCOUNT_TYPE_NAME;
        }
        return SdkConstants.ACCOUNT_TYPE_UNKNOWN;
    }

    public static String getRandomCode(int len) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            builder.append((int) (Math.random() * 10));
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        System.out.println(getRandomCode(4));
    }
}
