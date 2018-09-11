package com.bsoft.deploy.exception;

/**
 * 命令执行错误
 * Created on 2018/8/19.
 *
 * @author yangl
 */
public class CommandExecuteException extends Exception {

    public CommandExecuteException(String message) {
        super(message);
    }

    public CommandExecuteException(String message, Throwable cause) {
        super(message, cause);
    }
}
