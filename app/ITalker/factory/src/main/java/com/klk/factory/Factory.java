package com.klk.factory;

import android.app.Application;

import com.klk.common.app.MyApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Des
 * @Auther Administrator
 * @date 2018/3/27  15:23
 */

public class Factory {
    private static final Factory instance;
    private final ExecutorService executor;
    static {
        instance = new Factory();
    }

    /**
     * 构造方法
     */
    private Factory(){
        executor = Executors.newFixedThreadPool(4);//创建一个含有4个线程的线程池
    }

    /**
     * 获取全局Application的方法
     * @return
     */
    public static Application getApplication(){
        return MyApplication.getInstance();
    }

    /**
     * 异步执行的方法
     * @param runnable
     */
    public static void runOnAysnc(Runnable runnable){
        instance.executor.execute(runnable);
    }
}
