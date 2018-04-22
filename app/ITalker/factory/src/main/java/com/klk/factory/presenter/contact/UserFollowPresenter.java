package com.klk.factory.presenter.contact;

import android.support.annotation.StringRes;

import com.klk.common.factory.data.DataSource;
import com.klk.common.factory.presenter.BasePresenter;
import com.klk.factory.data.helper.UserHelper;
import com.klk.factory.model.card.UserCard;
import com.klk.factory.presenter.contact.UserFollowContract;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

/**
 * @Des
 * @Auther klkliu
 * @date 2018/4/18  15:15
 */

public class UserFollowPresenter extends BasePresenter<UserFollowContract.IView>
        implements UserFollowContract.IPresenter ,DataSource.Callback<UserCard>{
    public UserFollowPresenter(UserFollowContract.IView view) {
        super(view);
    }

    @Override
    public void userFollow(String followId) {
        start();
        UserHelper.userFollow(followId,this);
    }


    @Override
    public void onDataLoaded(final UserCard userCard) {
        final UserFollowContract.IView view = getView();
        if(view!=null){
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    view.followSuccess(userCard);
                }
            });
        }
    }

    @Override
    public void onDataLoadFailed(@StringRes final int resId) {
        final UserFollowContract.IView view = getView();
        if(view!=null){
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    view.showError(resId);
                }
            });
        }
    }
}
