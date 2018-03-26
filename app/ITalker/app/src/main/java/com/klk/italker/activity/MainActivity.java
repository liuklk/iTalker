package com.klk.italker.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
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
import com.klk.italker.fragment.main.ContactFragment;
import com.klk.italker.fragment.main.GroupFragment;
import com.klk.italker.fragment.main.HomeFragment;
import com.klk.italker.helper.NavHelper;

import net.qiujuer.genius.res.Resource;
import net.qiujuer.genius.ui.Ui;
import net.qiujuer.genius.ui.widget.FloatActionButton;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener
        , NavHelper.onTabChangedListener<Integer> {


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
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.btn_action)
    FloatActionButton btnAction;
    private NavHelper<Integer> mNavHelper;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initWeight() {
        super.initWeight();

        mNavHelper = new NavHelper<>(getSupportFragmentManager(), this, R.id.lay_container, this);
        mNavHelper.addTab(R.id.action_home, new NavHelper.Tab<>(HomeFragment.class, R.string.action_home))
                .addTab(R.id.action_group, new NavHelper.Tab<>(GroupFragment.class, R.string.action_group))
                .addTab(R.id.action_contact, new NavHelper.Tab<>(ContactFragment.class, R.string.action_contact));
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

    @Override
    protected void initData() {
        super.initData();
        //触发刚进入首次
        Menu menu = navigation.getMenu();
        menu.performIdentifierAction(R.id.action_home, 0);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //转接事件流到NavHelper
        return mNavHelper.performOnItemSelected(item.getItemId());
    }


    @Override
    public void onTabChanged(NavHelper.Tab<Integer> oldTab, NavHelper.Tab<Integer> newTab) {
        txtTitle.setText(newTab.extal);

        float transY =0;
        float rotation = 0;
        if(Objects.equals(R.string.action_home,newTab.extal)){
            transY = Ui.dipToPx(getResources(),76);
        }else if(Objects.equals(R.string.action_group,newTab.extal)) {
            rotation = -360;
            btnAction.setImageResource(R.drawable.ic_group_add);
        }else{
            rotation = 360;
            btnAction.setImageResource(R.drawable.ic_contact_add);
        }
        btnAction.animate()
                .translationY(transY)
                .rotation(rotation)
                .setDuration(480)
                .setInterpolator(new AnticipateOvershootInterpolator(1))
                .start();
    }


    @OnClick(R.id.im_search)
    public void onSearchClicked() {
    }

    @OnClick(R.id.btn_action)
    public void onActionClicked() {
        AccountActivity.show(this);
    }
}
