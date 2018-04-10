package com.klk.factory.presenter.account;

import com.klk.common.factory.presenter.BasePresenter;

/**
 * @Des
 * @Auther Administrator
 * @date 2018/4/2  16:17
 */

public class LoginPresenter extends BasePresenter <LoginContact.IView>implements LoginContact.IPresenter {

    public LoginPresenter(LoginContact.IView view) {
        super(view);
    }

    @Override
    public void login(String phone, String password) {

        LoginContact.IView view = getView();

    }




}
