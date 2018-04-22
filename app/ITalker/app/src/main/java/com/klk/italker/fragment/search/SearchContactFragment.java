package com.klk.italker.fragment.search;


import android.support.annotation.StringRes;
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
import com.klk.factory.presenter.search.SearchContract;
import com.klk.factory.presenter.search.SearchContactPresenter;
import com.klk.factory.presenter.contact.UserFollowContract;
import com.klk.factory.presenter.contact.UserFollowPresenter;
import com.klk.italker.R;
import com.klk.italker.activity.SearchActivity;

import net.qiujuer.genius.ui.Ui;
import net.qiujuer.genius.ui.compat.UiCompat;
import net.qiujuer.genius.ui.drawable.LoadingCircleDrawable;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 搜索联系人的界面实现
 */
public class SearchContactFragment extends BasePresenterFragment<SearchContract.IPresenter>
        implements SearchActivity.SearchFragment, SearchContract.IContactView {
    private static final String TAG = "SearchContactFragment";

    @BindView(R.id.rv_search_contact)
    RecyclerView rvSearchContact;
    @BindView(R.id.ev_search_contact)
    EmptyView evSearchContact;


    public BaseRecyclerAdapter<UserCard> mAdapter;

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
        rvSearchContact.setAdapter(mAdapter = new BaseRecyclerAdapter<UserCard>() {
            @Override
            public BaseRecyclerViewHolder onCreateViewHolder(View root, int viewType) {
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
        Log.i(TAG, "onSearchDone: userCards" + userCards);
        //数据加载成功的时候返回数据
        mAdapter.replace(userCards);

        //如果有数据就ok，没有数据就显示空布局
        evSearchContact.triggerOkOrEmpty(userCards.size() > 0);

    }

    @Override
    protected SearchContactPresenter initPresenter() {
        return new SearchContactPresenter(this);
    }

    public class ViewHolder extends BaseRecyclerAdapter.BaseRecyclerViewHolder<UserCard>
            implements UserFollowContract.IView {

        UserFollowContract.IPresenter mPresenter;
        @BindView(R.id.pv_contact_portrait)
        PortraitView pvPortrait;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.iv_follow)
        ImageView ivFollow;

        public ViewHolder(View itemView) {
            super(itemView);


            new UserFollowPresenter(this);

        }

        @Override
        public void onBind(final UserCard userCard) {


            pvPortrait.setup( Glide.with(getContext()),userCard);
            tvName.setText(userCard.getName());
            ivFollow.setEnabled(!userCard.isFollow());

            ivFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.userFollow(userCard.getId());
                }
            });
        }

        @Override
        public void showLoading() {
            int minSize = (int) Ui.dipToPx(getResources(), 22);
            int maxSize = (int) Ui.dipToPx(getResources(), 30);
            LoadingCircleDrawable drawable = new LoadingCircleDrawable(minSize, maxSize);
            drawable.setBackgroundColor(0);

            int[] color = {UiCompat.getColor(getResources(), R.color.white_alpha_208)};

            drawable.setForegroundColor(color);

            ivFollow.setImageDrawable(drawable);

            drawable.start();
        }

        @Override
        public void followSuccess(UserCard userCard) {

            if (ivFollow.getDrawable() instanceof LoadingCircleDrawable) {
                LoadingCircleDrawable drawable = (LoadingCircleDrawable) ivFollow.getDrawable();
                //停止动画
                drawable.stop();
                //将drawable设置为默认的
                ivFollow.setImageResource(R.drawable.sel_opt_done_add);
            }
            updateData(userCard);
        }


        @Override
        public void showError(@StringRes int id) {
            if (ivFollow.getDrawable() instanceof LoadingCircleDrawable) {
                LoadingCircleDrawable drawable = (LoadingCircleDrawable) ivFollow.getDrawable();
                //
                drawable.setProgress(1);
                //停止动画
                drawable.stop();
            }

        }

        @Override
        public void setPresenter(UserFollowContract.IPresenter presenter) {

            this.mPresenter = presenter;

        }
    }
}
