package com.bsoft.deploy.dao.entity;

/**
 * 自节点应用属性
 * Created on 2018/8/18.
 *
 * @author yangl
 */
public class SlaveApp {
    private int id;
    private int appId;
    private int slaveId;
    private String appTargetPath;
    private String appBackupPath;
    private String appTomcatHome;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public int getSlaveId() {
        return slaveId;
    }

    public void setSlaveId(int slaveId) {
        this.slaveId = slaveId;
    }

    public String getAppTargetPath() {
        return appTargetPath;
    }

    public void setAppTargetPath(String appTargetPath) {
        this.appTargetPath = appTargetPath;
    }

    public String getAppBackupPath() {
        return appBackupPath;
    }

    public void setAppBackupPath(String appBackupPath) {
        this.appBackupPath = appBackupPath;
    }

    public String getAppTomcatHome() {
        return appTomcatHome;
    }

    public void setAppTomcatHome(String appTomcatHome) {
        this.appTomcatHome = appTomcatHome;
    }
}
