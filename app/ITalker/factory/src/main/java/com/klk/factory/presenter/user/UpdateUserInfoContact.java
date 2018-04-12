package com.klk.factory.presenter.user;

import com.klk.common.factory.presenter.BaseContact;

/**
 * @Des
 * @Auther Administrator
 * @date 2018/4/11  15:03
 */

public interface UpdateUserInfoContact {

    interface IView extends BaseContact.View<IPresenter>{
        void updateSuccess();
    }

    interface IPresenter extends BaseContact.Presenter{
        void update(String portraitPath,String description ,boolean isMan );
    }
}
