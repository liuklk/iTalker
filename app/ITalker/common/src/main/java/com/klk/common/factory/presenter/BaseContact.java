package com.klk.common.factory.presenter;

import android.support.annotation.StringRes;

/**
 * @Des MVP模式公共的契约类
 * @Auther Administrator
 * @date 2018/4/2  15:03
 */

public interface BaseContact {

    interface View<T extends Presenter>{

        //公用的显示进度条的方法
        void showLoading();

        //公用的显示错误的方法
        void showError(@StringRes int id);

        //公用的设置presenter
        void setPresenter(T presenter);
    }

    interface Presenter {

        //开始的触发
        void start();

        //销毁的触发
        void destroy();
    }
}
