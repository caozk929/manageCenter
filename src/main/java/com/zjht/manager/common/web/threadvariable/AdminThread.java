package com.zjht.manager.common.web.threadvariable;

import com.zjht.manager.entity.User;

public class AdminThread {

    private static ThreadLocal<User> instance = new ThreadLocal<User>();


    public static User get() {
        return instance.get();
    }

    public static void set(User group) {
        instance.set(group);
    }

    public static void remove() {
        instance.remove();
    }
}
