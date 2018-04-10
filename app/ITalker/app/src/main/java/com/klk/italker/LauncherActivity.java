package com.klk.italker;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.util.Property;
import android.view.View;

import com.klk.common.app.BaseActivity;
import com.klk.factory.persistence.Account;
import com.klk.italker.activity.AccountActivity;
import com.klk.italker.activity.MainActivity;
import com.klk.italker.fragment.other.PermissionFragment;

import net.qiujuer.genius.res.Resource;
import net.qiujuer.genius.ui.compat.UiCompat;

/**
 * 闪屏界面
 */
public class LauncherActivity extends BaseActivity {
    private static final String TAG = "LauncherActivity";

    private ColorDrawable mBgDrawable;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_launcher;
    }

    @Override
    protected void initData() {
        super.initData();

        startAnim(0.5f, new Runnable() {
            @Override
            public void run() {
                waitPushReceiveId();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void skip() {
        startAnim(1f, new Runnable() {
            @Override
            public void run() {
                reallySkip();
            }
        });

    }

    private void waitPushReceiveId() {
        //已经登录判断是否绑定
        //如果没有绑定
        Log.i(TAG, "waitPushReceiveId:         Account.isLogin()        "+Account.isLogin());
        Log.i(TAG, "waitPushReceiveId: Account.isBind()       "+Account.isBind());
        if (Account.isLogin()) {
            if (Account.isBind()) {
                //跳转
                skip();
                return;
            }
        } else {
            //没有登录
            //但是拿到pushId,没有登录是不能绑定pushId的
            Log.i(TAG, "waitPushReceiveId: "+Account.getPushId());
            if (!TextUtils.isEmpty(Account.getPushId())) {
                //跳转
                skip();
                return;
            }
        }

        //循环等待
        getWindow().getDecorView()
                .postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        waitPushReceiveId();
                    }
                }, 500);

    }

    /**
     * 真实的跳转方法
     */
    private void reallySkip() {
        //
        if (PermissionFragment.haveAll(this, getSupportFragmentManager())) {
            if (Account.isLogin()) {
                MainActivity.show(this);
            } else {
                AccountActivity.show(this);
            }

            finish();
        }
    }

    @Override
    public void initWeight() {
        super.initWeight();
        //拿到跟布局
        View root = findViewById(R.id.activity_launch);
        //获取颜色
        int color = UiCompat.getColor(getResources(), R.color.colorPrimary);
        //创建一个Drawable
        ColorDrawable drawable = new ColorDrawable(color);
        //设置给背景
        root.setBackground(drawable);
        mBgDrawable = drawable;

    }

    private void startAnim(float endProgress, final Runnable endCallback) {
        //获取一个最终的颜色
        int finalColor = Resource.Color.WHITE;
        //运算当前的进的的颜色
        ArgbEvaluator evaluator = new ArgbEvaluator();
        int endColor = (int) evaluator.evaluate(endProgress, mBgDrawable.getColor(), finalColor);
        //构建一个属性动画
        ValueAnimator valueAnimator = ObjectAnimator.ofObject(this, property, evaluator, endColor);

        valueAnimator.setDuration(1500);//动画时间
        valueAnimator.setIntValues(mBgDrawable.getColor(), endColor);
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //动画结束时触发
                endCallback.run();
            }
        });
        valueAnimator.start();
    }


    private final Property<LauncherActivity, Object> property = new Property<LauncherActivity, Object>(Object.class, "color") {
        @Override
        public Object get(LauncherActivity launcherActivity) {
            return launcherActivity.mBgDrawable.getColor();
        }

        @Override
        public void set(LauncherActivity object, Object value) {

            object.mBgDrawable.setColor((Integer) value);
        }
    };

}
