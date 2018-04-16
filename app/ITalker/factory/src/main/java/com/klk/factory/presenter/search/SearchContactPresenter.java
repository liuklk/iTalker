package com.klk.factory.presenter.search;

import android.support.annotation.StringRes;
import android.util.Log;

import com.klk.common.factory.data.DataSource;
import com.klk.common.factory.presenter.BasePresenter;
import com.klk.factory.data.helper.UserHelper;
import com.klk.factory.model.card.UserCard;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.List;

import retrofit2.Call;

/**
 * @Des 查找人的presenter实现
 * @Auther klkliu
 * @date 2018/4/16  10:06
 */

public class SearchContactPresenter extends BasePresenter<SearchContact.IContactView>
        implements SearchContact.IPresenter ,DataSource.Callback<List<UserCard>> {

    private static final String TAG = "SearchContactPresenter";
    private Call mCall ;
    public SearchContactPresenter(SearchContact.IContactView view) {
        super(view);
    }

    @Override
    public void search(String text) {
        start();
        Call call = mCall;
        if(call!=null && !call.isCanceled()){
            call.cancel();
        }
        mCall = UserHelper.searchContact(text, this);
    }

    @Override
    public void onDataLoaded(final List<UserCard> userCards) {
        Log.i(TAG, "onDataLoaded: userCards"+userCards.toString());
        final SearchContact.IContactView view = getView();
        Log.i(TAG, "onDataLoaded: view"+view);
        if(view!=null){
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    view.onSearchDone(userCards);
                }
            });
        }

    }

    @Override
    public void onDataLoadFailed(@StringRes final int resId) {

        final SearchContact.IContactView view = getView();
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
