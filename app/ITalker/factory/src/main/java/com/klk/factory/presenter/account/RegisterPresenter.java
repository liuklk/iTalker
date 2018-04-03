package com.klk.factory.presenter.account;

import android.support.annotation.StringRes;
import android.text.TextUtils;

import com.klk.common.Common;
import com.klk.common.factory.data.DataSource;
import com.klk.common.factory.presenter.BasePresenter;
import com.klk.factory.data.helper.AccountHelper;
import com.klk.factory.model.api.RegisterModel;
import com.klk.factory.model.db.User;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.regex.Pattern;

/**
 * @Des
 * @Auther Administrator
 * @date 2018/4/2  16:47
 */

public class RegisterPresenter extends BasePresenter<RegisterContact.IView>
        implements RegisterContact.IPresenter ,DataSource.Callback<User>{
    public RegisterPresenter(RegisterContact.IView view) {
        super(view);
    }

    @Override
    public void register(String phone, String password, String name) {

        RegisterContact.IView view = getView();
        if(!checked(phone)){
            //号码不合法
            view.showError(com.klk.lang.R.string.data_account_register_invalid_parameter_mobile);
        }else if(name.length()<3){
            view.showError(com.klk.lang.R.string.data_account_register_invalid_parameter_name);
            //账户名不合法
        }if(password.length()<6){
            //密码太短
            view.showError(com.klk.lang.R.string.data_account_register_invalid_parameter_password);

        }else {
            RegisterModel model = new RegisterModel(phone, name, password);
            //进行网络请求，设置回调
            AccountHelper.register(model,this);
        }
    }

    @Override
    public boolean checked(String phone) {

        return !TextUtils.isEmpty(phone)
                && Pattern.matches(Common.Constance.REGEX_MOBILE,phone);
    }

    @Override
    public void onDataLoaded(User user) {
        //数据请求成功
        final RegisterContact.IView view = getView();
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                if(view!=null){
                    view.registerSuccess();
                }
            }
        });
    }

    @Override
    public void onDataLoadFailed(@StringRes final int resId) {
        //数据请求失败
        final RegisterContact.IView view = getView();
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                if(view!=null){
                    view.showError(resId);
                }
            }
        });
    }
}
