package com.klk.factory.presenter.search;

import com.klk.common.factory.presenter.BasePresenter;

/**
 * @Des 查找群的presenter实现
 * @Auther klkliu
 * @date 2018/4/16  10:09
 */

public class SearchGroupPresenter extends BasePresenter<SearchContract.IGroupView>
        implements SearchContract.IPresenter {
    public SearchGroupPresenter(SearchContract.IGroupView view) {
        super(view);
    }

    @Override
    public void search(String text) {

    }
}
