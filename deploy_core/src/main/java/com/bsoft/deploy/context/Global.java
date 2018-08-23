package com.bsoft.deploy.context;

import com.bsoft.deploy.context.store.AppStore;
import com.bsoft.deploy.context.store.SlaveStore;
import com.bsoft.deploy.dao.entity.App;
import com.bsoft.deploy.dao.entity.Slave;
import com.bsoft.deploy.dao.entity.SlaveApp;
import com.bsoft.deploy.dao.mapper.AppFileMapper;
import com.bsoft.deploy.dao.mapper.AppMapper;
import com.bsoft.deploy.dao.mapper.SlaveAppFileMapper;
import com.bsoft.deploy.dao.mapper.SlaveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 全局上下文
 * @TODO 缓存移到store中
 * Created on 2018/8/15.
 *
 * @author yangl
 */
public class Global {
    // spring boot ApplicationContext
    private static ApplicationContext appContext;

    private static AppFileMapper commFileMapper;
    private static SlaveAppFileMapper commSlaveAppFileMapper;

    @Autowired
    AppMapper appMapper;

    @Autowired
    AppFileMapper fileMapper;

    @Autowired
    SlaveMapper slaveMapper;

    @Autowired
    SlaveAppFileMapper slaveAppFileMapper;

    /**
     * master主机应用默认的路径
     */
    private static Map<Integer, App> appInfo = new HashMap<>();

    private static Map<String, Slave> slaves = new HashMap<>();

    private static Map<Integer, SlaveApp> slaveApps = new HashMap<>();

    public static String getAppBasePath(int appId) {
        return appInfo.get(appId).getPath();
    }

    public static AppFileMapper getFileMapper() {
        return commFileMapper;
    }

    public static SlaveAppFileMapper getSlaveAppFileMapper() {
        return commSlaveAppFileMapper;
    }

    public static Slave getSlave(String ip) {
        return slaves.get(ip);
    }

    public static SlaveApp getSlaveApp(int id) {
        return slaveApps.get(id);
    }

    @PostConstruct
    public void init() {
        commFileMapper = fileMapper;
        commSlaveAppFileMapper = slaveAppFileMapper;

        initApps();
        initSalves();
        initSlaveApps();
    }


    private void initApps() {
        List<App> apps = appMapper.loadApps();
        for (App app : apps) {
            appInfo.put(app.getAppId(), app);
        }
    }

    private void initSalves() {
        List<Slave> slaveList = slaveMapper.loadSlaves();
        for (Slave slave : slaveList) {
            slaves.put(slave.getIp(), slave);
        }
    }

    private void initSlaveApps() {
        List<SlaveApp> slaveAppList = slaveAppFileMapper.loadSlaveApps();
        for (SlaveApp slaveApp : slaveAppList) {
            slaveApps.put(slaveApp.getId(), slaveApp);
        }
    }

    public static AppStore getAppStore() {
        return appContext.getBean(AppStore.class);
    }
    public static SlaveStore getSlaveStore() {
        return appContext.getBean(SlaveStore.class);
    }

    public static ApplicationContext getAppContext() {
        return appContext;
    }

    public static void setAppContext(ApplicationContext appContext) {
        Global.appContext = appContext;
    }
}
