package com.test.sdk.common.pojo;

import java.io.Serializable;
import java.util.Date;

public class NumCode implements Serializable{
    private String code;
    private String num;
    private Date createdDate;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
