package com.test.sdk.common.util;

import java.util.Random;

public class TicketUtil {
    public static String encode(String ticket) {
        Long time = System.currentTimeMillis();
        System.out.println(time);
        Random random = new Random();
        char[] timeChar = time.toString().toCharArray();
        int r = random.nextInt(10);//随机数
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < timeChar.length; i++) {
            builder.append(ticket.charAt(i));
            builder.append(Integer.toHexString((timeChar[i] - '0') ^ r));

        }
        builder.append(ticket.substring(timeChar.length));
        builder.append(r);
        return builder.toString();
    }

    public static String decode(String ticket) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 13; i++) {
            builder.append(ticket.charAt(i * 2));
        }
        builder.append(ticket.substring(26, 45));
        return builder.toString();
    }

    public static long getLastActTime(String ticket) {
        StringBuilder builder = new StringBuilder();
        int r=ticket.charAt(45)-'0';
        for (int i = 0; i < 13; i++) {
            builder.append(Integer.parseInt(String.valueOf(ticket.charAt(i * 2 + 1)),16)^r);
        }
        return Long.valueOf(builder.toString());
    }

    public static void main(String[] args) throws Exception{
        String t =DigestUtils.getMD5("adasasfafsaff");
        System.out.println(t);
        System.out.println(encode(t));
        Thread.sleep(500);
        System.out.println(encode(t));
        System.out.println(getLastActTime("d75323541f272667241236a7e3fa1c181265daafe2c106"));

//        for (int i = 0; i < 10; i++) {
//            System.out.println(encode(t));
//        }
//        ConcurrentHashMap map=new ConcurrentHashMap<>();
//        map.put("aa",12);
//        map.get("");
//        System.out.println(decode("37f393a44222f1b14f8392e3f730b7cab1c548b8896fd6"));
//        System.out.println(getLastActTime("37f393a44222f1b14f8392e3f730b7cab1c548b8896fd6"));
//        System.out.println(new Date(1552447795451L));
    }
}
