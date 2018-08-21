package com.bsoft.deploy.dao.entity;

/**
 * 节点
 * Created on 2018/8/17.
 *
 * @author yangl
 */
public class Slave {

    private int id;
    private String name;
    private String ip;
    private int status;
    private String describes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }
}
