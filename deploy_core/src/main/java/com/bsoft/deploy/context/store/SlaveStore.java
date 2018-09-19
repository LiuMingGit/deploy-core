package com.bsoft.deploy.context.store;

import com.bsoft.deploy.dao.entity.Slave;
import com.bsoft.deploy.dao.entity.SlaveApp;
import com.bsoft.deploy.dao.mapper.SlaveAppFileMapper;
import com.bsoft.deploy.dao.mapper.SlaveMapper;
import com.bsoft.deploy.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 节点缓存
 * Created on 2018/8/19.
 *
 * @author yangl
 */
public class SlaveStore {

    @Autowired
    SlaveMapper slaveMapper;

    @Autowired
    SlaveAppFileMapper slaveAppFileMapper;


    private static ConcurrentHashMap<Integer, Slave> slaves = new ConcurrentHashMap<>();

    private static ConcurrentHashMap<Integer, SlaveApp> slaveApps = new ConcurrentHashMap<>();

    private static ConcurrentHashMap<Integer, String> slaveAppPort = new ConcurrentHashMap<>();


    public Slave getSlave(int slaveId) {
        if (slaves.containsKey(slaveId)) {
            return slaves.get(slaveId);
        }
        Slave slave = slaveMapper.findSlave(slaveId);
        slaves.put(slaveId, slave);
        return slave;
    }

    public SlaveApp getSlaveApp(int slaveId, int appId) {
        for (SlaveApp slaveApp : slaveApps.values()) {
            if (slaveApp.getSlaveId() == slaveId && slaveApp.getAppId() == appId) {
                return slaveApp;
            }
        }
        SlaveApp slaveApp = slaveMapper.findSlaveApp(slaveId, appId);
        if (slaveApp != null) {
            slaveApps.put(slaveApp.getId(), slaveApp);
        }
        return slaveApp;
    }

    public SlaveApp getSlaveApp(int slaveAppId) {
        if (slaveApps.containsKey(slaveAppId)) {
            return slaveApps.get(slaveAppId);
        }
        SlaveApp slaveApp = slaveAppFileMapper.findSlaveApp(slaveAppId);
        slaveApps.put(slaveAppId, slaveApp);
        return slaveApp;
    }

    public String getSlaveAppPort(int slaveAppId) {
        if (slaveAppPort.containsKey(slaveAppId)) {
            return slaveAppPort.get(slaveAppId);
        }
        String tomcat_home = getSlaveApp(slaveAppId).getAppTomcatHome();
        String port = FileUtils.getTomcatServerPort(tomcat_home);
        slaveAppPort.put(slaveAppId, port);
        return port;
    }

    /**
     * 清除缓存
     */
    public void reloadAll() {
        slaves.clear();
        slaveApps.clear();
        slaveAppPort.clear();
    }

    public void reloadSlaveApp(int slaveAppId) {
        slaveApps.remove(slaveAppId);
    }
}
