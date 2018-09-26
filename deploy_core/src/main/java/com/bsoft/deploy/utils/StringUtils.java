package com.bsoft.deploy.utils;

/**
 * desc
 * Created on 2018/8/25.
 *
 * @author yangl
 */
public class StringUtils extends org.springframework.util.StringUtils {
    public static boolean isEq(Object o1,Object o2) {
        if(o1 == null) {
            return false;
        }
        return o1.toString().equals(o2);
    }
    public static String toString(Object o) {
        return o == null ? null : o.toString();
    }
}
