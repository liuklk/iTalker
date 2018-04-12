package com.klk.factory.presenter.user;

import android.support.annotation.StringRes;
import android.text.TextUtils;

import com.klk.common.app.MyApplication;
import com.klk.common.factory.data.DataSource;
import com.klk.common.factory.presenter.BasePresenter;
import com.klk.factory.Factory;
import com.klk.factory.R;
import com.klk.factory.data.helper.UserHelper;
import com.klk.factory.model.api.user.UserUpdateModel;
import com.klk.factory.model.card.UserCard;
import com.klk.factory.model.db.User;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

/**
 * @Des
 * @Auther Administrator
 * @date 2018/4/11  15:17
 */

public class UpdateUserInfoPresenter extends BasePresenter<UpdateUserInfoContact.IView>
        implements UpdateUserInfoContact.IPresenter ,DataSource.Callback<UserCard>{
    public UpdateUserInfoPresenter(UpdateUserInfoContact.IView view) {
        super(view);
    }

    @Override
    public void update(final String portraitPath, final String description, final boolean isMan) {
        start();
        final UpdateUserInfoContact.IView view = getView();
        if(TextUtils.isEmpty(portraitPath)||TextUtils.isEmpty(description)){
            view.showError(R.string.data_account_update_invalid_parameter);
        }
        Factory.runOnAysnc(new Runnable() {
            @Override
            public void run() {
                UserHelper.update(new UserUpdateModel("",portraitPath,description,isMan? User.SEX_MAN:User.SEX_WOMEN),UpdateUserInfoPresenter.this);
            }
        });
    }

    @Override
    public void onDataLoaded(UserCard userCard) {

        final UpdateUserInfoContact.IView view = getView();
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                if(view!=null){
                    view.updateSuccess();
                }
            }
        });
    }

    @Override
    public void onDataLoadFailed(@StringRes int resId) {
        MyApplication.Toast(resId);
    }
}
