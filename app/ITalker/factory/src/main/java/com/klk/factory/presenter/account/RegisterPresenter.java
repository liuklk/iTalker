package com.klk.factory.presenter.account;

import com.klk.common.factory.presenter.BasePresenter;

/**
 * @Des
 * @Auther Administrator
 * @date 2018/4/2  16:47
 */

public class RegisterPresenter extends BasePresenter<RegisterContact.IView> implements RegisterContact.IPresenter {
    public RegisterPresenter(RegisterContact.IView view) {
        super(view);
    }

    @Override
    public void register(String phone, String password, String name) {
        
    }

    @Override
    public void checked(String phone) {

    }
}
