package com.test.sdk.dao;


import com.test.sdk.common.pojo.OnlineUser;

public interface OnlineDAO {
    void addOnlineUser(OnlineUser user);

    void deleteTicketByUserId(Integer userId);

    void updateLastAct(String ticket);//刷新最后一次操作时间


    OnlineUser getOnlineUser(String ticket);
}
