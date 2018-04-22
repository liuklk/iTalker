package com.klk.factory.presenter.user;

import com.klk.common.factory.presenter.BaseContract;

/**
 * @Des
 * @Auther Administrator
 * @date 2018/4/11  15:03
 */

public interface UpdateUserInfoContract {

    interface IView extends BaseContract.View<IPresenter>{
        void updateSuccess();
    }

    interface IPresenter extends BaseContract.Presenter{
        void update(String portraitPath,String description ,boolean isMan );
    }
}
