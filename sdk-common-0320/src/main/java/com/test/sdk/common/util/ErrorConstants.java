package com.test.sdk.common.util;

public interface ErrorConstants {
    ErrorCodeTO UNKNOWN_ERROR=new ErrorCodeTO(0000,"未知异常");
    ErrorCodeTO SIGN_ERROR=new ErrorCodeTO(0001,"非法签名");
    ErrorCodeTO PARAM_ERROR=new ErrorCodeTO(0002,"参数异常");
    ErrorCodeTO GAME_NOT_EXIST=new ErrorCodeTO(0003,"游戏不存在");
    ErrorCodeTO SESSION_TIMEOUT=new ErrorCodeTO(0004,"登陆过期");
    ErrorCodeTO ACCOUNT_EXIST=new ErrorCodeTO(1000,"用户名已存在");
    ErrorCodeTO ACCOUNT_NOT_EXIST=new ErrorCodeTO(1001,"用户名不存在");
    ErrorCodeTO ACCOUNT_INVALID=new ErrorCodeTO(1002,"非法账号");
    ErrorCodeTO NUM_EXIST=new ErrorCodeTO(1003,"手机号已注册");
    ErrorCodeTO NUM_NOT_EXIST=new ErrorCodeTO(1004,"手机号未注册");
    ErrorCodeTO CODE_EMPTY=new ErrorCodeTO(1005,"请输入验证码");
    ErrorCodeTO PASSWORD_INVALID=new ErrorCodeTO(1006,"密码格式不正确");
    ErrorCodeTO CODE_INVALID=new ErrorCodeTO(1007,"验证码无效");
    ErrorCodeTO PASSWORD_ERROR=new ErrorCodeTO(1008,"用户名或密码错误");

}
