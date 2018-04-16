package com.klk.common.app;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.klk.common.R;

/**
 * @Des
 * @Auther klkliu
 * @date 2018/4/15  18:56
 */
public abstract class BaseToolbarActivity extends BaseActivity {

    private Toolbar mToolbar ;
    @Override
    public void initWeight() {
        super.initWeight();

        initToolbar((Toolbar)findViewById(R.id.toolbar));
    }

    private void initToolbar(Toolbar toolbar) {
        this.mToolbar = toolbar ;
        if(toolbar!=null){
            setSupportActionBar(toolbar);
        }
        initToolbarOnBack();
    }

    protected void initToolbarOnBack(){
        ActionBar actionBar = getSupportActionBar();

        if(actionBar!=null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

}
