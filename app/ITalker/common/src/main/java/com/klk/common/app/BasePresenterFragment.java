package com.klk.common.app;

import android.content.Context;
import android.support.annotation.StringRes;

import com.klk.common.factory.presenter.BaseContact;

/**
 * @Des
 * @Auther Administrator
 * @date 2018/4/2  15:43
 */

public abstract class BasePresenterFragment<T extends BaseContact.Presenter> extends BaseFragment implements BaseContact.View<T> {

    private T presenter ;
    @Override
    public void showLoading() {
        //// TODO: 2018/4/2
    }

    @Override
    public void showError(@StringRes int id) {

        MyApplication.Toast(id);
    }

    @Override
    public void setPresenter(T presenter) {
        this.presenter = presenter ;
    }

    //初始化presenter
    protected abstract T initPresenter();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //初始化presenter
        initPresenter();
    }
}
