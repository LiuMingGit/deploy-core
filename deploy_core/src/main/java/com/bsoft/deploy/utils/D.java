package com.bsoft.deploy.utils;

/**
 * 数字类型工具类
 * Created on 2018/9/21.
 *
 * @author yangl
 */
public class D {
    public static <T> T safeParse(Object o, Class<T> type) {
        if (o == null)
            return null;
        if (type == Integer.TYPE) {
            o = Integer.parseInt(o.toString());
        } else if (type == Double.TYPE) {
            o = Double.parseDouble(o.toString());
        } else if (type == Long.TYPE) {
            o = Long.parseLong(o.toString());
        }
        return (T) o;
    }

}
