package com.test.sdk.common.pojo;

import java.io.Serializable;
import java.util.Date;

public class OnlineUser implements Serializable{
    private Integer userId;
    private String ticket;
    private String account;
    private Date lastAct;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getUserId() {
        return userId;
    }

    public Date getLastAct() {
        return lastAct;
    }

    public void setLastAct(Date lastAct) {
        this.lastAct = lastAct;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
