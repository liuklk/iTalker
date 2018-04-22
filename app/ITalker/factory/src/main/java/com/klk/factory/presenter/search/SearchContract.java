package com.klk.factory.presenter.search;

import com.klk.common.factory.presenter.BaseContract;
import com.klk.factory.model.card.GroupCard;
import com.klk.factory.model.card.UserCard;

import java.util.List;

/**
 * @Des
 * @Auther klkliu
 * @date 2018/4/16  9:59
 */

public interface SearchContract {
     interface IPresenter extends BaseContract.Presenter{
        void search(String text);
    }

    interface IContactView extends BaseContract.View<IPresenter>{
        void onSearchDone(List<UserCard> userCards);
    }

    interface IGroupView extends BaseContract.View<IPresenter>{
        void onSearchDone(List<GroupCard> userCards);
    }
}
