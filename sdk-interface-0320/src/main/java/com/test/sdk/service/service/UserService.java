package com.test.sdk.service.service;


import com.test.sdk.common.exception.SdkException;
import com.test.sdk.common.pojo.OnlineUser;
import com.test.sdk.common.pojo.User;

public interface UserService {

    User doNameRegist(String name, String password) throws SdkException;

    User doNumRegist(String num, String code, String password) throws SdkException;

    boolean doSendCode(String num) throws SdkException;

    OnlineUser doLogin(String account, String password) throws SdkException;

    void updateLastAct(String ticket);

}
