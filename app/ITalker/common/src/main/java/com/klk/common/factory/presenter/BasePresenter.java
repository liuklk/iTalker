package com.klk.common.factory.presenter;

/**
 * @Des
 * @Auther Administrator
 * @date 2018/4/2  16:19
 */

public class BasePresenter<T extends BaseContact.View> implements BaseContact.Presenter {

    private T mView;

    public BasePresenter(T view) {
        setView(view);
    }


    @SuppressWarnings("unchecked")
    protected void setView(T view){
        this.mView = view ;
        mView.setPresenter(this);
    }

    /**
     * 不能被继承 ，获取view的方法
     * @return
     */
    protected final T getView(){
        return mView ;
    }

    @Override
    public void start() {
        T view = mView ;
        if(view!=null){
            //在开始被触发时显示进度条
            view.showLoading();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void destroy() {
        T view = mView ;
        mView = null ;
        if(view!=null){
            //将presenter置为null
            view.setPresenter(null);
        }
    }
}
