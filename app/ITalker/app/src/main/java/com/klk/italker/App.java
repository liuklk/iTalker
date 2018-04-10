package com.klk.italker;

import com.igexin.sdk.PushManager;
import com.klk.common.app.MyApplication;
import com.klk.factory.Factory;

/**
 * @Des
 * @Auther Administrator
 * @date 2018/3/26  14:51
 */

public class App extends MyApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //Factory初始化
        Factory.setup();
        //个推的初始化
        PushManager.getInstance().initialize(this);
    }
}
