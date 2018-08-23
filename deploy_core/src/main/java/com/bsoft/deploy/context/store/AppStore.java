package com.bsoft.deploy.context.store;

import com.bsoft.deploy.dao.entity.App;
import com.bsoft.deploy.dao.mapper.AppMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 主节点缓存
 * Created on 2018/8/13.
 *
 * @author yangl
 */
public class AppStore {

    @Autowired
    AppMapper appMapper;

    /**
     * master主机应用默认的路径
     */
    private ConcurrentHashMap<Integer, App> apps = new ConcurrentHashMap<>();

    public String getAppBasePath(int appId) {
        return getApp(appId).getPath();
    }

    public App getApp(int appId) {
        if (apps.containsKey(appId)) {
            return apps.get(appId);
        }
        App app = appMapper.findApp(appId);
        apps.put(appId, app);
        return app;
    }

    /**
     * 清除缓存
     */
    public void reloadAll() {
        apps.clear();
    }
}
