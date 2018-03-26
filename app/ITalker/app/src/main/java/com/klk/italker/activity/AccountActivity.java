package com.klk.italker.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.klk.common.app.BaseActivity;
import com.klk.italker.R;
import com.klk.italker.fragment.account.UpdateFragment;

public class AccountActivity extends BaseActivity {

    private Fragment mFragment;
    /**
     * 显示当前Activity的方法
     * @param context
     */
    public static void show(Context context){
        context.startActivity(new Intent(context,AccountActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account;
    }

    @Override
    public void initWeight() {
        super.initWeight();
        mFragment = new UpdateFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_account_container, mFragment)
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mFragment.onActivityResult(requestCode,resultCode,data);
    }
}
