package com.klk.factory.presenter.account;

import com.klk.common.factory.presenter.BaseContact;

/**
 * @Des 登录的契约类
 * @Auther Administrator
 * @date 2018/4/2  15:03
 */

public interface LoginContact extends BaseContact {

    interface IView extends BaseContact.View<IPresenter> {

        void loginSuccess();
    }

    interface IPresenter extends BaseContact.Presenter {
        void login(String phone, String password);
    }
}
