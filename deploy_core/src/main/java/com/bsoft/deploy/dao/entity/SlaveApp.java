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
    /**
     * 当前更新包版本
     */
    private int pkgId;
    private String appTargetPath;
    private String appBackupPath;
    private String appTomcatHome;
    private AppPackage appPackage;

    private String appName;
    /**
     * 更新标志
     */
    private boolean update = false;

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

    public int getPkgId() {
        return pkgId;
    }

    public void setPkgId(int pkgId) {
        this.pkgId = pkgId;
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

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public AppPackage getAppPackage() {
        return appPackage;
    }

    public void setAppPackage(AppPackage appPackage) {
        this.appPackage = appPackage;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }
}
