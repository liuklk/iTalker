package com.klk.factory.presenter.contact;

import com.klk.common.factory.presenter.BaseContract;
import com.klk.factory.model.card.UserCard;

/**
 * @Des
 * @Auther klkliu
 * @date 2018/4/18  15:10
 */

public interface UserFollowContract {

    interface IView extends BaseContract.View<IPresenter>{
        void followSuccess(UserCard userCard);
    }

    interface IPresenter extends BaseContract.Presenter{
        void userFollow(String followId);
    }
}
