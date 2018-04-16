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

    protected T mPresenter ;
    @Override
    public void showLoading() {
        if(mPlaceView!=null){
            mPlaceView.triggerLoading();
        }
    }

    @Override
    public void showError(@StringRes int id) {
        if(mPlaceView!=null){
            //优先使用占位布局的方法
            mPlaceView.triggerError(id);
        }else {
            MyApplication.Toast(id);
        }

    }

    @Override
    public void setPresenter(T presenter) {
        this.mPresenter = presenter ;
    }

    protected T getPresenter(){
        return mPresenter;
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
