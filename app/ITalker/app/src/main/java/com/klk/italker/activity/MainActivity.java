package com.klk.italker.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.klk.common.app.BaseActivity;
import com.klk.common.widget.view.PortraitView;
import com.klk.italker.R;

import net.qiujuer.genius.ui.widget.FloatActionButton;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener{


    @BindView(R.id.im_portrait)
    PortraitView imPortrait;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.im_search)
    ImageView imSearch;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.lay_container)
    FrameLayout layContainer;
    @BindView(R.id.btn_action)
    FloatActionButton btnAction;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initWeight() {
        super.initWeight();
        navigation.setOnNavigationItemSelectedListener(this);
        //设置appbar背景
        Glide.with(this)
                .load(R.drawable.bg_src_morning)
                .into(new ViewTarget<View, GlideDrawable>(appbar) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        this.view.setBackground(resource.getCurrent());
                    }
                });

    }


    @OnClick(R.id.im_search)
    public void onSearch() {
    }

    @OnClick(R.id.btn_action)
    public void onActionClick() {
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        txtTitle.setText(item.getTitle());
        return true;
    }
}
