package com.klk.italker.fragment.main;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.klk.common.app.BaseFragment;
import com.klk.common.widget.recyclerview.BaseRecyclerAdapter;
import com.klk.common.widget.view.EmptyView;
import com.klk.common.widget.view.PortraitView;
import com.klk.factory.model.card.UserCard;
import com.klk.factory.model.db.User;
import com.klk.italker.R;
import com.klk.italker.fragment.search.SearchContactFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *联系人列表界面
 */
public class ContactFragment extends BaseFragment {

    @BindView(R.id.rv_contact)
    RecyclerView rvContact;
    @BindView(R.id.ev_contact)
    EmptyView evContact;

    private BaseRecyclerAdapter<User> mAdapter ;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contact;
    }

    @Override
    protected void initWidget(View view) {
        super.initWidget(view);

        rvContact.setLayoutManager(new LinearLayoutManager(getContext()));
        rvContact.setAdapter(mAdapter = new BaseRecyclerAdapter<User>() {
            @Override
            public BaseRecyclerViewHolder onCreateViewHolder(View root, int viewType) {
                return new ContactFragment.ViewHolder(root);
            }

            @Override
            protected int getItemViewType(User user, int position) {
                return R.layout.item_contact_list;
            }
        });
        evContact.bind(rvContact);
        setPlaceView(evContact);
    }

    class ViewHolder extends BaseRecyclerAdapter.BaseRecyclerViewHolder<User>{

        @BindView(R.id.pv_portrait)
        PortraitView pvPortrait;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.iv_msg)
        ImageView ivMsg;
        public ViewHolder(View itemView) {
            super(itemView);

        }

        @Override
        public void onBind(User user) {

            pvPortrait.setup(Glide.with(getContext()),user);
            tvName.setText(user.getName());
            tvDesc.setText(user.getDescription());
        }

        @OnClick(R.id.pv_portrait)
        public void onclick(){

        }
    }
}
