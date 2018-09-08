package com.bsoft.deploy.exception;

/**
 * 节点离线异常
 * Created on 2018/9/5.
 *
 * @author yangl
 */
public class SlaveOfflineException extends RuntimeException{
    public SlaveOfflineException(String message) {
        super(message);
    }
}
