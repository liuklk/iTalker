package com.klk.italker.fragment.search;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.klk.common.app.BasePresenterFragment;
import com.klk.common.widget.recyclerview.BaseRecyclerAdapter;
import com.klk.common.widget.view.EmptyView;
import com.klk.common.widget.view.PortraitView;
import com.klk.factory.model.card.UserCard;
import com.klk.factory.presenter.search.SearchContact;
import com.klk.factory.presenter.search.SearchContactPresenter;
import com.klk.italker.R;
import com.klk.italker.activity.SearchActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * 搜索联系人的界面实现
 */
public class SearchContactFragment extends BasePresenterFragment<SearchContact.IPresenter>
        implements SearchActivity.SearchFragment, SearchContact.IContactView {
    private static final String TAG = "SearchContactFragment";

    @BindView(R.id.rv_search_contact)
    RecyclerView rvSearchContact;
    @BindView(R.id.ev_search_contact)
    EmptyView evSearchContact;


    private BaseRecyclerAdapter<UserCard> mAdapter;
    public SearchContactFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_contact;
    }

    @Override
    protected void initData() {
        super.initData();
        search("");
    }

    @Override
    public void search(String text) {
        //触发presenter中的search方法
        mPresenter.search(text);
    }

    @Override
    protected void initWidget(View view) {
        super.initWidget(view);
        rvSearchContact.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSearchContact.setAdapter(mAdapter =new BaseRecyclerAdapter<UserCard>() {
            @Override
            protected BaseRecyclerViewHolder onCreateViewHolder(View root, int viewType) {
                return new SearchContactFragment.ViewHolder(root);
            }

            @Override
            protected int getItemViewType(UserCard userCard, int position) {
                return R.layout.item_search_contact;
            }
        });
        evSearchContact.bind(rvSearchContact);
        setPlaceView(evSearchContact);
    }

    @Override
    public void onSearchDone(List<UserCard> userCards) {
        Log.i(TAG, "onSearchDone: userCards"+userCards);
        //数据加载成功的时候返回数据
        mAdapter.replace(userCards);

        //如果有数据就ok，没有数据就显示空布局
        evSearchContact.triggerOkOrEmpty(userCards.size()>0);

    }

    @Override
    protected SearchContactPresenter initPresenter() {
        return new SearchContactPresenter(this);
    }

   class ViewHolder extends BaseRecyclerAdapter.BaseRecyclerViewHolder<UserCard>{

       @BindView(R.id.pv_contact_portrait)
       PortraitView pvPortrait;
       @BindView(R.id.tv_name)
       TextView tvName;
       @BindView(R.id.iv_follow)
       ImageView ivFollow;

       public ViewHolder(View itemView) {
           super(itemView);
           pvPortrait = itemView.findViewById(R.id.pv_contact_portrait);
           tvName = itemView.findViewById(R.id.tv_name);
           ivFollow = itemView.findViewById(R.id.iv_follow);
       }

       @Override
       public void onBind(UserCard userCard) {


           Glide.with(getContext())
                   .load(userCard.getPortrait())
                   .into(pvPortrait);

           tvName.setText(userCard.getName());
           ivFollow.setEnabled(!userCard.isFollow());
       }
   }
}
