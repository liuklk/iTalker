package com.klk.common.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Des
 * @Auther Administrator
 * @date 2018/3/20  17:52
 */

public abstract class BaseFragment extends Fragment {

    private View mRootView;
    protected Unbinder mBinder;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //初始化参数
        initArgs(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mRootView==null){
            int layoutId = getLayoutId();
            View root = inflater.inflate(layoutId, container);
            initWidget(root);
            mRootView = root;
        }else {
            if(mRootView.getParent()!=null){
                //把当前root从父控件移除
                ((ViewGroup)mRootView.getParent()).removeView(mRootView);
            }
        }


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    protected void initArgs(Bundle bundle){

    }
    protected abstract int getLayoutId();

    protected void initData(){

    }

    protected void initWidget(View view){
        mBinder = ButterKnife.bind(this, view);
    }

    /**
     * 返回键点击的时候调用
     * @return true 表示自己已经处理逻辑，Activity 不用finish
     * false 表示自己没有处理，交给Activity处理
     */
    protected boolean onBackPressed(){
        return false ;
    }
}
