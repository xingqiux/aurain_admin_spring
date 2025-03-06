package top.xkqq.util;

import top.xkqq.entity.system.SysUser;

public class AuthContextUtil {

    // 创建一个 ThreadLocal 对象
    private static ThreadLocal<SysUser> threadLocal = new ThreadLocal<>();

    // 定义存储数据静态方法
    public static void set(SysUser sysUser){
        threadLocal.set(sysUser);
    }

    // 定义获取数据静态方法
    public static SysUser get(){
        return threadLocal.get();
    }

    // 定义删除数据静态方法
    public static void remove(){
        threadLocal.remove();
    }

}



