package com.fzdkx.utils;

import com.fzdkx.model.media.bean.MediaUser;

/**
 * @author 发着呆看星
 * @create 2024/2/6
 */
public class MediaThreadLocalUtil {
    private static final ThreadLocal<MediaUser> MEDIA_THREAD_LOCAL_USER = new ThreadLocal<>();

    /**
     * 添加用户
     */
    public static void setUser(MediaUser user){
        MEDIA_THREAD_LOCAL_USER.set(user);
    }

    /**
     * 获取用户
     */
    public static MediaUser getUser(){
        return MEDIA_THREAD_LOCAL_USER.get();
    }

    /**
     * 清除用户
     */
    public static void clear(){
        if (MEDIA_THREAD_LOCAL_USER.get() != null){
            MEDIA_THREAD_LOCAL_USER.remove();
        }
    }
}
