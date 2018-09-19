package com.bsoft.deploy.context;

/**
 * 常量类
 * Created on 2018/8/20.
 *
 * @author yangl
 */
public interface Constant {
    /**
     * tomcat是否运行
     */
    String CMD_IS_TOMCAT_RUN = "IS_TOMCAT_RUN";
    /**
     * 启动tomcat
     */
    String CMD_TOMCAT_START = "TOMCAT_START";
    /**
     * 关闭tomcat
     */
    String CMD_TOMCAT_STOP = "TOMCAT_STOP";

    /**
     * 清除slave缓存
     */
    String CMD_RELOAD_CACHE = "RELOAD_CACHE";

    /**
     * 导出线程
     */
    String CMD_THREAD_DUMP = "THREAD_DUMP";

    /**
     * 备份应用
     */
    String CMD_APP_BACKUP = "APP_BACKUP";

    /**
     * 更新包同步状态
     */
    String CMD_FILE_STATUS = "FILE_STATUS";


    String TMP_FILE_SUFFIX = ".tmp";
}
