package com.klk.factory.presenter.account;

import com.klk.common.factory.presenter.BaseContact;

/**
 * @Des 注册的契约类
 * @Auther Administrator
 * @date 2018/4/2  15:03
 */

public interface RegisterContact {

    interface IView extends BaseContact.View<IPresenter> {

        void registerSuccess();

    }

    interface IPresenter extends BaseContact.Presenter {
        void register(String phone, String password, String name);

        //校验电话号码
        void checked(String phone);

    }
}
