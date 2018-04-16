package com.klk.italker.fragment.search;


import com.klk.common.app.BasePresenterFragment;
import com.klk.factory.model.card.GroupCard;
import com.klk.factory.presenter.search.SearchContact;
import com.klk.factory.presenter.search.SearchGroupPresenter;
import com.klk.italker.R;
import com.klk.italker.activity.SearchActivity;

import java.util.List;

/**
 * 搜索群组的界面实现.
 */
public class SearchGroupFragment extends BasePresenterFragment<SearchContact.IPresenter>
        implements SearchActivity.SearchFragment,SearchContact.IGroupView{


    public SearchGroupFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_group;
    }

    @Override
    public void search(String text) {
        //触发presenter中的search方法
        mPresenter.search(text);
    }

    @Override
    public void onSearchDone(List<GroupCard> userCards) {

    }

    @Override
    protected SearchContact.IPresenter initPresenter() {
        return new SearchGroupPresenter(this);
    }
}
