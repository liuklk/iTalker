package com.klk.factory.presenter.account;

import com.klk.common.factory.presenter.BaseContract;

/**
 * @Des 注册的契约类
 * @Auther Administrator
 * @date 2018/4/2  15:03
 */

public interface RegisterContract {

    interface IView extends BaseContract.View<IPresenter> {

        void registerSuccess();

    }

    interface IPresenter extends BaseContract.Presenter {
        void register(String phone, String password, String name);

        //校验电话号码
        boolean checked(String phone);

    }
}
