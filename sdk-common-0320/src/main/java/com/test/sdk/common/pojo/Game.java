package com.test.sdk.common.pojo;

import java.io.Serializable;

public class Game implements Serializable{
    private Integer id;
    private Integer cpId;
    private Integer repairStatus;
    private String repairContent;
    private String version;

    public Integer getCpId() {
        return cpId;
    }

    public void setCpId(Integer cpId) {
        this.cpId = cpId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRepairStatus() {
        return repairStatus;
    }

    public void setRepairStatus(Integer repairStatus) {
        this.repairStatus = repairStatus;
    }

    public String getRepairContent() {
        return repairContent;
    }

    public void setRepairContent(String repairContent) {
        this.repairContent = repairContent;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
