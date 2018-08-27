package com.bsoft.deploy.dao.entity;

/**
 * desc
 * Created on 2018/8/24.
 *
 * @author yangl
 */
public class Token {

    /**
     * token票根:唯一标志
     */
    private String ticket;
    /**
     * 最后活跃时间
     */
    private long lastActive;
    /**
     * 过期时间 小于等于0标识永久存活
     * 单位 s
     */
    private long expire;

    /**
     * 票据所属的用户
     */
    private String uid;

    public Token(){
        this.lastActive = System.currentTimeMillis();
    }

    public Token(String ticket, long expire) {
        this.ticket = ticket;
        this.expire = expire;
        this.lastActive = System.currentTimeMillis();
    }

    public boolean isExpire() {
        return expire > 0 && (System.currentTimeMillis() > this.lastActive + expire * 1000);
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public long getLastActive() {
        return lastActive;
    }

    public void setLastActive(long lastActive) {
        this.lastActive = lastActive;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
