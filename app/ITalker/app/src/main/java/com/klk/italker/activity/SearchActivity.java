package com.klk.italker.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.klk.common.app.BaseActivity;
import com.klk.common.app.BaseFragment;
import com.klk.common.app.BaseToolbarActivity;
import com.klk.italker.R;
import com.klk.italker.fragment.search.SearchContactFragment;
import com.klk.italker.fragment.search.SearchGroupFragment;

public class SearchActivity extends BaseToolbarActivity {

    public static final String SEARCH_TYPE = "SEARCH_TYPE";
    public static final int TYPE_CONTACT =1;
    public static final int TYPE_GROUP =2;

    private int mType ;
    private SearchFragment mSearchFragment;
    /**
     * 搜索界面的入口
     * @param type  搜索的类型
     */
    public static void show(Context context , int type){
        Intent intent = new Intent(context,SearchActivity.class);
        intent.putExtra(SEARCH_TYPE,type);
        context.startActivity(intent);
    }

    @Override
    public void initWeight() {
        super.initWeight();
        BaseFragment mFragment;
        if(mType == TYPE_CONTACT){
            SearchContactFragment searchContactFragment = new SearchContactFragment();
            mFragment =searchContactFragment;
            mSearchFragment = searchContactFragment ;
        }else {
            SearchGroupFragment searchGroupFragment = new SearchGroupFragment();
            mFragment = searchGroupFragment ;
            mSearchFragment = searchGroupFragment ;
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_search_container,mFragment).commit();
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        mType = bundle.getInt(SEARCH_TYPE);
        return mType == TYPE_CONTACT||mType == TYPE_GROUP;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_items,menu);

        //找到搜索菜单
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        if(searchView!=null){
            // 拿到一个搜索管理器
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            //查询文字的监听
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    //当点击了，查询按钮
                    search(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    //当文字发生改变时
                    if(newText==null){
                        search("");
                        return true ;
                    }
                    return false;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 真正的查询操作
     * @param query
     */
    private void search(String query) {

        if(mSearchFragment==null){
            return;
        }
        mSearchFragment.search(query);
    }

    public interface SearchFragment{
        void search(String text);
    }
}
