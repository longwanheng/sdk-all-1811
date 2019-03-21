package com.test.sdk.data.pojo;

import java.io.Serializable;
import java.util.Date;

public class RegistData implements Serializable {
    private Integer gameId;
    private Integer userAmount;
    private Integer channelId;
    private Date date;

    public RegistData(Integer gameId, Integer userAmount, Integer channelId, Date date) {
        this.gameId = gameId;
        this.userAmount = userAmount;
        this.channelId = channelId;
        this.date = date;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getUserAmount() {
        return userAmount;
    }

    public void setUserAmount(Integer userAmount) {
        this.userAmount = userAmount;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public RegistData() {
    }
}
