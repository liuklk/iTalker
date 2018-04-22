package com.klk.factory.presenter.account;

import com.klk.common.factory.presenter.BaseContract;

/**
 * @Des 登录的契约类
 * @Auther Administrator
 * @date 2018/4/2  15:03
 */

public interface LoginContract extends BaseContract {

    interface IView extends BaseContract.View<IPresenter> {

        void loginSuccess();
    }

    interface IPresenter extends BaseContract.Presenter {
        void login(String phone, String password);
    }
}
