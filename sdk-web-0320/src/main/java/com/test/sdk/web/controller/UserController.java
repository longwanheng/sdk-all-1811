package com.test.sdk.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.test.sdk.web.cache.CPCache;
import com.test.sdk.common.exception.SdkException;
import com.test.sdk.common.pojo.OnlineUser;
import com.test.sdk.common.util.*;
import com.test.sdk.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Reference
    private UserService userService;

    @RequestMapping("/common/sendcode.html")
    //@ResponseBody
    public ResponseTO sendCode(String num) throws SdkException {
        if (!AccountUtil.checkMobile(num)) {
            throw new SdkException(ErrorConstants.ACCOUNT_INVALID);
        }
        userService.doSendCode(num);
        return new ResponseTO("发送成功");
    }


    @RequestMapping("/common/regist.html")
    //@ResponseBody
    public ResponseTO regist(String account, String password, String code, Integer cpid) throws SdkException {
        try {
            password = DESUtil.decode(password, Base64.encode(CPCache.getCpSecret(cpid).getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new SdkException(ErrorConstants.PASSWORD_INVALID);
        }
        int accountType = AccountUtil.getAccountType(account);
        if (accountType == SdkConstants.ACCOUNT_TYPE_UNKNOWN) {
            throw new SdkException(ErrorConstants.ACCOUNT_INVALID);
        }
        if (!AccountUtil.checkPassword(password)) {
            throw new SdkException(ErrorConstants.PASSWORD_INVALID);
        }
        if (accountType == SdkConstants.ACCOUNT_TYPE_NUM) {
            if (StringUtils.isEmpty(code)) {
                throw new SdkException(ErrorConstants.CODE_EMPTY);
            }
            userService.doNumRegist(account, code, password);
        } else {
            userService.doNameRegist(account, password);
        }

        return new ResponseTO("注册成功");
    }

    @RequestMapping("common/login.html")
    public ResponseTO login(String account, String password, Integer cpid) throws SdkException {
        try {
            password = DESUtil.decode(password, Base64.encode(CPCache.getCpSecret(cpid).getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new SdkException(ErrorConstants.PASSWORD_INVALID);
        }
        if (!AccountUtil.checkPassword(password)) {
            throw new SdkException(ErrorConstants.PASSWORD_INVALID);
        }
        OnlineUser user = userService.doLogin(account, password);
        ResponseTO response = new ResponseTO();
        response.setTicket(TicketUtil.encode(user.getTicket()));
        return response;
    }

    private static final long ONE_MINUTE = 60 * 1000;

    @RequestMapping("user/heartbeat.html")
    public ResponseTO heartBeat(String ticket) throws SdkException {
        String decodeTicket = TicketUtil.decode(ticket);
        long lastAct = TicketUtil.getLastActTime(ticket);
        if (System.currentTimeMillis() - lastAct > ONE_MINUTE) {
            userService.updateLastAct(decodeTicket);
            //redis expire ticket 20分钟
        }
        ResponseTO response = new ResponseTO();
        response.setTicket(TicketUtil.encode(decodeTicket));
        return response;
    }

    @RequestMapping("user/servers.html")
    public ResponseTO servers(String ticket) throws SdkException {
        ResponseTO response = heartBeat(ticket);
        List<String> servers = new ArrayList<>();
        servers.add("1811");
        servers.add("帝都风云");
        response.setResult(servers);
        return response;
    }
}
