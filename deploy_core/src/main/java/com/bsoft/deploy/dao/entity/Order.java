package com.bsoft.deploy.dao.entity;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * 用于同步传输的指令
 * Created on 2018/8/19.
 *
 * @author yangl
 */
public class Order implements Serializable {
    /**
     * 指令类型
     */
    private String type;

    /**
     * 一次请求的唯一表示,uuid
     */
    private String uniqueId;
    /**
     * 请求数据
     */
    private Map<String,Object> reqData;

    /**
     * 应答数据
     */
    private Map<String,Object> respData;

    /**
     * 用于同步请求
     */
    private transient CountDownLatch latch;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getReqData() {
        return reqData;
    }

    public void setReqData(Map<String, Object> reqData) {
        this.reqData = reqData;
    }

    public Map<String, Object> getRespData() {
        return respData;
    }

    public void setRespData(Map<String, Object> respData) {
        this.respData = respData;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }
}
