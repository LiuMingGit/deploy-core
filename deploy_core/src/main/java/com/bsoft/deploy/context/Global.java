package com.bsoft.deploy.context;

import com.bsoft.deploy.context.store.AppStore;
import com.bsoft.deploy.context.store.SlaveStore;
import com.bsoft.deploy.context.store.TokenStore;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;

/**
 * 全局上下文
 *
 * @author yangl
 * Created on 2018/8/15.
 */
public class Global {
    // spring boot ApplicationContext
    private static ApplicationContext appContext;

    @PostConstruct
    public void init() {

    }


    public static <T> T getBean(Class<T> type) {
        return appContext.getBean(type);
    }

    public static TokenStore getTokenStore() {
        return appContext.getBean(TokenStore.class);
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
