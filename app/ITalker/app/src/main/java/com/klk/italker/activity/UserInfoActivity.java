package com.klk.italker.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.klk.common.app.BaseActivity;
import com.klk.italker.R;
import com.klk.italker.fragment.user.UpdateFragment;

import net.qiujuer.genius.ui.compat.UiCompat;

import butterknife.BindView;

public class UserInfoActivity extends BaseActivity {


    @BindView(R.id.iv_bg)
    ImageView ivBg;

    private Fragment currentFragment ;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info;
    }

    public static void show(Context context){
        context.startActivity(new Intent(context,UserInfoActivity.class));
    }

    @Override
    public void initWeight() {
        super.initWeight();

        currentFragment = new UpdateFragment();
        //初始化fragment
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_account_container, currentFragment)
                .commit();

        //初始化背景
        Glide.with(this)
                .load(R.drawable.bg_src_tianjin)
                .centerCrop()
                .into(new ViewTarget<ImageView, GlideDrawable>(ivBg) {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        currentFragment.onActivityResult(requestCode,resultCode,data);

    }
}
