package com.bsoft.deploy.dao.entity;

import java.util.Date;

/**
 * 节点更新日志
 * Created on 2018/9/12.
 *
 * @author yangl
 */
public class UpdateLog {
    private int id;
    private int slaveAppId;
    /**
     * 原始版本
     */
    private int oldPkgId;
    /**
     * 更新版本
     */
    private int newPkgId;
    private Date optime;
    private String opuser;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSlaveAppId() {
        return slaveAppId;
    }

    public void setSlaveAppId(int slaveAppId) {
        this.slaveAppId = slaveAppId;
    }

    public int getOldPkgId() {
        return oldPkgId;
    }

    public void setOldPkgId(int oldPkgId) {
        this.oldPkgId = oldPkgId;
    }

    public int getNewPkgId() {
        return newPkgId;
    }

    public void setNewPkgId(int newPkgId) {
        this.newPkgId = newPkgId;
    }

    public Date getOptime() {
        return optime;
    }

    public void setOptime(Date optime) {
        this.optime = optime;
    }

    public String getOpuser() {
        return opuser;
    }

    public void setOpuser(String opuser) {
        this.opuser = opuser;
    }
}
