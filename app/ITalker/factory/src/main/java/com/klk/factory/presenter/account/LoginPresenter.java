package com.klk.factory.presenter.account;

import android.support.annotation.StringRes;
import android.text.TextUtils;

import com.klk.common.factory.data.DataSource;
import com.klk.common.factory.presenter.BasePresenter;
import com.klk.factory.data.helper.AccountHelper;
import com.klk.factory.model.api.account.LoginModel;
import com.klk.factory.model.db.User;
import com.klk.factory.persistence.Account;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

/**
 * @Des
 * @Auther Administrator
 * @date 2018/4/2  16:17
 */

public class LoginPresenter extends BasePresenter <LoginContact.IView>implements LoginContact.IPresenter,DataSource.Callback<User> {

    public LoginPresenter(LoginContact.IView view) {
        super(view);
    }

    @Override
    public void login(String phone, String password) {

        LoginContact.IView view = getView();

        if(TextUtils.isEmpty(phone)||TextUtils.isEmpty(password)){
            view.showError(com.klk.lang.R.string.data_account_login_invalid_parameter);
        }else {
            //尝试传入pushId
            LoginModel loginModel = new LoginModel(phone, password, Account.getPushId());
            AccountHelper.login(loginModel,this);
        }
    }


    @Override
    public void onDataLoaded(User user) {

        //数据请求成功
        final LoginContact.IView view = getView();
        //在主线程中进行
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                if(view!=null){
                    view.loginSuccess();
                }
            }
        });
    }

    @Override
    public void onDataLoadFailed(@StringRes final int resId) {

        //数据请求成功
        final LoginContact.IView view = getView();
        //在主线程中进行
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                if(view!=null){
                    view.showError( resId);
                }
            }
        });
    }
}
