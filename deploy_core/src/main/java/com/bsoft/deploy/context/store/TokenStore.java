package com.bsoft.deploy.context.store;

import com.bsoft.deploy.dao.entity.Token;
import org.springframework.beans.factory.annotation.Value;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * token缓存,简单过期策略
 * Created on 2018/8/24.
 *
 * @author yangl
 */
public class TokenStore {
    private Map<String, Token> CACHE_TOKEN = new ConcurrentHashMap<>();

    /**
     * 默认过期时间,单位:秒
     */
    @Value("${security.expireSec}")
    private String expireSec;

    public Token get(String key) {
        Token token = CACHE_TOKEN.get(key);
        if (token != null) {
            if (!token.isExpire()) {
                token.setLastActive(System.currentTimeMillis());
                return token;
            } else {
                CACHE_TOKEN.remove(key);
            }
        }
        return null;
    }

    public void set(Token token) {
        token.setExpire(Integer.parseInt(expireSec));
        // 批量清理过期的token
        if (CACHE_TOKEN.size() > 4096) {
            clearExpire();
        }
        CACHE_TOKEN.put(token.getTicket(), token);
    }

    /**
     * 设置token
     *
     * @param key token票根,唯一识别
     */
    public void set(String key) {
        set(key, Integer.parseInt(expireSec));
    }

    /**
     * 设置token
     *
     * @param key    token票根,唯一识别
     * @param expire 过期时间,单位:秒
     */
    public void set(String key, int expire) {
        // 批量清理过期的token
        if (CACHE_TOKEN.size() > 4096) {
            clearExpire();
        }
        CACHE_TOKEN.put(key, new Token(key, expire));
    }

    /**
     * 移除token(登出)
     *
     * @param key token票根,唯一识别
     */
    public void clear(String key) {
        CACHE_TOKEN.remove(key);
    }

    public void clearExpire() {
        Iterator<Map.Entry<String, Token>> iterator = CACHE_TOKEN.entrySet().iterator();
        while (iterator.hasNext()) {
            Token token = iterator.next().getValue();
            if (token.isExpire()) {
                iterator.remove();
            }
        }
    }

}
