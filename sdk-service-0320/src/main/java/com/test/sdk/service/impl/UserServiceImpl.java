package com.test.sdk.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.test.sdk.common.exception.SdkException;
import com.test.sdk.common.pojo.OnlineUser;
import com.test.sdk.common.pojo.User;
import com.test.sdk.common.util.AccountUtil;
import com.test.sdk.common.util.DigestUtils;
import com.test.sdk.common.util.ErrorConstants;
import com.test.sdk.common.util.SdkConstants;
import com.test.sdk.dao.OnlineDAO;
import com.test.sdk.dao.UserDAO;
import com.test.sdk.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

@Service(timeout = 3000)//dubbo的Service 不是Spring的Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private OnlineDAO onlineDAO;

    @Override
    public User doNameRegist(String name, String password) throws SdkException {
        if (userDAO.getUserIdByName(name) != null) {//用户名已存在
            throw new SdkException(ErrorConstants.ACCOUNT_EXIST);
        }
        User user = new User();
        user.setName(name);
        user.setPassword(DigestUtils.getMD5(password + SdkConstants.PASSWORD_SALT));
        userDAO.addUser(user);
        userDAO.addUserName(user);
        return user;
    }

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean doSendCode(String num) throws SdkException {
        //验证手机号有没有注册
        Integer userId = userDAO.getUserIdByNum(num);
        if (userId != null) {//手机号已经注册
            throw new SdkException(ErrorConstants.NUM_EXIST);
        }
        String code = AccountUtil.getRandomCode(4);
        //调用发送短信的第三方接口
        //userDAO.addNumCode(num, code);

        redisTemplate.opsForValue().set(num, code);
        System.out.println(redisTemplate.opsForValue().get(num));
        redisTemplate.expire(num, 60, TimeUnit.SECONDS);
        return true;
    }

    private final int ONE_MIMUTE = 1000 * 60;

    @Override
    public User doNumRegist(String num, String code, String password) throws SdkException {
        //NumCode numCode = userDAO.getNumCode(num);
        String numCode = redisTemplate.opsForValue().get(num);
        if (numCode == null) {
            throw new SdkException(ErrorConstants.CODE_INVALID);
        }
//        if (numCode.getCode().equals(code) && (System.currentTimeMillis() - numCode.getCreatedDate().getTime()) > ONE_MIMUTE) {
//            throw new SdkException(ErrorConstants.CODE_INVALID);
//        }
        if(!numCode.equals(code)){
            throw new SdkException(ErrorConstants.CODE_INVALID);
        }
        //userDAO.deleteCodeByNum(num);

        redisTemplate.delete(num);
        User user = new User();
        user.setNum(num);
        user.setPassword(DigestUtils.getMD5(password + SdkConstants.PASSWORD_SALT));
        userDAO.addUser(user);
        userDAO.addUserNum(user);
        return user;
    }

    @Override
    public OnlineUser doLogin(String account, String password) throws SdkException {
        int accountType = AccountUtil.getAccountType(account);
        if (accountType == SdkConstants.ACCOUNT_TYPE_UNKNOWN) {
            throw new SdkException(ErrorConstants.ACCOUNT_INVALID);
        }
        Integer userId = (accountType == SdkConstants.ACCOUNT_TYPE_NUM) ? userDAO.getUserIdByNum(account) : userDAO.getUserIdByName(account);
        if (userId == null) {//账号不存在
            throw new SdkException(ErrorConstants.ACCOUNT_NOT_EXIST);
        }
        User user = userDAO.getUserById(userId);
        if (!DigestUtils.getMD5(password + SdkConstants.PASSWORD_SALT).equals(user.getPassword())) {
            throw new SdkException(ErrorConstants.PASSWORD_ERROR);
        }
        String ticket=DigestUtils.getMD5(userId+user.getPassword()+System.currentTimeMillis());
        OnlineUser onlineUser=new OnlineUser();
        onlineUser.setTicket(ticket);
        onlineUser.setUserId(userId);
        onlineUser.setAccount(account);
        onlineDAO.deleteTicketByUserId(userId);
        onlineDAO.addOnlineUser(onlineUser);
        //redis 中ticket为key,userId为value  ticket expire20分钟

        return onlineUser;
    }

    @Override
    public void updateLastAct(String ticket) {
        onlineDAO.updateLastAct(ticket);
    }
}
