package com.test.sdk.common.pojo;

import java.io.Serializable;

public class CP implements Serializable{
    private Integer id;
    private String secret;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
