package com.bsoft.deploy.dao.entity;

/**
 * desc
 * Created on 2018/9/13.
 *
 * @author yangl
 */

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 文件传输守卫
 */
public class Guard {

    private volatile int totalFiles = 0;
    /**
     * 更新记录
     */
    private int updateId;
    private int slaveAppId;
    private int slaveId;
    private int pkgId;
    private AtomicInteger sendNum = new AtomicInteger(0);
    private AtomicInteger receiveNum = new AtomicInteger(0);

    /**
     * 传输中有任意文件失败,即更新失败
     */
    private boolean fail;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 发送百分比
     * @return
     */
    public int getSendPercent() {
        if(totalFiles == 0) {
            return 0;
        }
        return sendNum.get() / totalFiles;
    }

    /**
     * 接受百分比
     * @return
     */
    public int getReceivePercent() {
        if(totalFiles == 0) {
            return 0;
        }
        return receiveNum.get() / totalFiles;
    }

    public int getUpdateId() {
        return updateId;
    }

    public void setUpdateId(int updateId) {
        this.updateId = updateId;
    }

    public int getTotalFiles() {
        return totalFiles;
    }

    public void setTotalFiles(int totalFiles) {
        this.totalFiles = totalFiles;
    }

    public int getSlaveAppId() {
        return slaveAppId;
    }

    public void setSlaveAppId(int slaveAppId) {
        this.slaveAppId = slaveAppId;
    }

    public int getSlaveId() {
        return slaveId;
    }

    public void setSlaveId(int slaveId) {
        this.slaveId = slaveId;
    }

    public int getSendNum() {
        return sendNum.get();
    }

    public void setSendNum(int sendNum) {
        this.sendNum.getAndAdd(sendNum);
    }

    public int getReceiveNum() {
        return receiveNum.get();
    }

    public void setReceiveNum(int reciveNum) {
        this.receiveNum.getAndAdd(reciveNum);
    }

    public void addTotalFiles(int size) {
        totalFiles += size;
    }

    public boolean isFail() {
        return fail;
    }

    public void setFail(boolean fail) {
        this.fail = fail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPkgId() {
        return pkgId;
    }

    public void setPkgId(int pkgId) {
        this.pkgId = pkgId;
    }
}
