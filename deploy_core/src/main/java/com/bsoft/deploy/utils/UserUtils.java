package com.bsoft.deploy.utils;

import com.bsoft.deploy.dao.entity.User;

/**
 * desc
 * Created on 2018/9/13.
 *
 * @author yangl
 */
public class UserUtils {
    private static ThreadLocal<User> threadLocal = new ThreadLocal<>();

    public static User getCurrentUser() {
        return threadLocal.get();
    }

    public static void setCurrentUser(User user) {
        threadLocal.set(user);
    }

    public static void clear() {
        threadLocal.remove();
    }
}
