package com.klk.italker.helper;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;


/**
 * @Des  完成对fragment的调度与重用问题，达到最优的fragment切换
 * @Auther Administrator
 * @date 2018/3/22  17:28
 */

public class NavHelper<T>{
    private static final String TAG = "klk";
    private final FragmentManager fragmentManager;
    private final Context mContext;
    private final int containerId;
    private final onTabChangedListener<T> tabChangedListener;
    //所有的tab
    private final SparseArray<Tab<T>> tabs = new SparseArray<>();
    //当前的tab
    private Tab<T> currentTab;

    public NavHelper(FragmentManager fragmentManager, Context mContext, int containerId,
                     onTabChangedListener<T> tabChangedListener) {
        this.fragmentManager = fragmentManager;
        this.mContext = mContext;
        this.containerId = containerId;
        this.tabChangedListener = tabChangedListener;
    }

    /**
     * 添加一个tab
     * @param tab
     * @param menuId
     * @return
     */
    public NavHelper<T> addTab(int menuId,Tab<T> tab){
        tabs.put(menuId,tab);
        return this;
    }

    public Tab<T> getCurrentTab(){
        return currentTab ;
    }
    /**
     * 处理tab被选中的事件
     * @param menuId
     * @return
     */
    public boolean performOnItemSelected(int menuId){
        Tab<T> tab = tabs.get(menuId);
        if(tab!=null){
            doSelectedTab(tab);
            return true ;
        }
        return false;
    }

    /**
     * 选中当前tab
     * @param tab
     */
    private void doSelectedTab(Tab<T> tab) {
        Tab<T> oldTab = null;
        if(currentTab!=null){
            oldTab = currentTab;
            if(currentTab == tab){
                //重复点击当前tab,通知界面进行刷新处理
                doNotifyReSelectedTab(tab);
                return;
            }
        }
        currentTab = tab ;
        //触发tab切换
        doTabChaned(oldTab ,currentTab);

    }

    /**
     * tab发生切换，进行fragment调度的操作
     * @param oldTab
     * @param newTab
     */
    private void doTabChaned(Tab<T> oldTab, Tab<T> newTab) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if(oldTab!=null){
            if(oldTab.fragment!=null){
                //从界面移除，还存在缓存中
                ft.detach(oldTab.fragment);
            }
        }
        if(newTab!=null){
            if(newTab.fragment==null){
                //如果newTab中fragment为null，创建一个fragment，添加进去
                Fragment fragment = Fragment.instantiate(mContext, newTab.clx.getName(), null);
                newTab.fragment = fragment;
                ft.add(containerId,fragment,newTab.clx.getName());
            }else {
                //如果不为null，从缓存中取出显示
                ft.attach(newTab.fragment);
            }
        }
        ft.commit();
        doNotifySelectedTab(oldTab,newTab);
    }

    /**
     * 通知界面 tab 发生了切换 的回调
     */
    private void doNotifySelectedTab(Tab<T> oldTab,Tab<T> newTab) {
        if(tabChangedListener!=null){
            tabChangedListener.onTabChanged(oldTab,newTab);
        }

    }
    /**
     * 通知界面进行刷新操作
     */
    private void doNotifyReSelectedTab(Tab<T> tab) {
        //// TODO: 2018/3/23
    }

    /**
     * tab 的属性
     * @param <T>  泛型 ，额外参数
     */
    public static class Tab<T>{
        public Tab(Class<?> clx, T extal) {
            this.clx = clx;
            this.extal = extal;
        }

        public Class<?> clx;
        //自己定义的字段
        public  T extal;
       Fragment fragment;
    }

    /**
     * Tab发生变化的接口
     * @param <T>
     */
    public interface onTabChangedListener<T>{
        void onTabChanged(Tab<T> oldTab ,Tab<T> newTab );
    }

}
