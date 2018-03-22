package com.klk.common.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import butterknife.ButterKnife;

/**
 * @Des
 * @Auther Administrator
 * @date 2018/3/20  17:51
 */

public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在界面未初始化之前初始化窗口
        initWindow();
        if(initArgs(getIntent().getExtras())){
            int layoutId =getLayoutId();
            setContentView(layoutId);
            initWeight();
            initData();
        }else {
            finish();
        }

    }

    /**
     * 初始化窗口
     */
    protected void initWindow(){

    }

    /**
     * 初始化参数
     * @param bundle  参数
     * @return 参数正确返回true ，参数错误返回false
     */
    protected boolean initArgs(Bundle bundle){
        return true ;
    }
    /**
     * 获取资源文件id
     * @return 资源文件id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化控件
     */
    public void initWeight(){
        ButterKnife.bind(this);
    }

    /**
     * 初始化数据
     */
    protected void initData(){

    }


    @Override
    public boolean onSupportNavigateUp() {
        //当界面导航返回键被点击
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {

        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if(fragments!=null&&fragments.size()>0){
            for (Fragment fragment : fragments){
                if(fragment instanceof BaseFragment){
                    if(((BaseFragment) fragment).onBackPressed()){
                        //如果fragment拦截处理了back点击
                        return;
                    }
                }
            }
        }
        //当实体返回键被点击
        super.onBackPressed();
        finish();

    }
}
