package com.klk.italker;

import com.klk.common.app.BaseActivity;
import com.klk.italker.activity.MainActivity;
import com.klk.italker.fragment.other.PermissionFragment;

public class LauncherActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_launcher;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(PermissionFragment.haveAll(this,getSupportFragmentManager())){
            MainActivity.show(this);
            finish();
        }
    }
}
