package com.klk.italker.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.klk.common.app.BaseActivity;
import com.klk.common.app.BaseFragment;
import com.klk.common.factory.data.DataSource;
import com.klk.factory.model.db.User;
import com.klk.italker.R;
import com.klk.italker.fragment.account.AccountTrigger;
import com.klk.italker.fragment.account.LoginFragment;
import com.klk.italker.fragment.account.RegisterFragment;

import net.qiujuer.genius.ui.compat.UiCompat;

import butterknife.BindView;


public class AccountActivity extends BaseActivity implements AccountTrigger
 ,DataSource.SuccessCallback<User>{

    private BaseFragment mCurrentFragment;
    private BaseFragment loginFragment;
    private BaseFragment registerFragment;

    private static final String TAG = "klk";

    @BindView(R.id.iv_bg)
    ImageView iv_bg;

    /**
     * 显示当前Activity的方法
     *
     * @param context
     */
    public static void show(Context context) {
        context.startActivity(new Intent(context, AccountActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account;
    }

    @Override
    public void initWeight() {
        super.initWeight();
        //初始化fragment
        mCurrentFragment = loginFragment = new LoginFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_account_container, mCurrentFragment)
                .commit();

        //初始化背景
        Glide.with(this)
                .load(R.drawable.bg_src_tianjin)
                .centerCrop()
                .into(new ViewTarget<ImageView, GlideDrawable>(iv_bg) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        //从glide中取出drawable
                        Drawable drawable = resource.getCurrent();
                        //使用适配类进行包装
                        drawable = DrawableCompat.wrap(drawable);
                        //设置drawable着色效果，颜色，蒙板模式
                        drawable.setColorFilter(UiCompat.getColor(getResources(), R.color.colorAccent),
                                PorterDuff.Mode.SCREEN);
                        //把drawable
                        this.view.setImageDrawable(drawable);

                    }
                });
    }


    @Override
    public void triggerAccount() {
        BaseFragment fragment;

        if (mCurrentFragment == loginFragment) {
            if (registerFragment == null) {
                registerFragment = new RegisterFragment();
            }
            fragment = registerFragment;
        } else {

            fragment = loginFragment;
        }

        mCurrentFragment = fragment;

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_account_container, mCurrentFragment)
                .commit();
    }

    @Override
    public void onDataLoaded(User user) {
        finish();
        MainActivity.show(this);
    }

}
