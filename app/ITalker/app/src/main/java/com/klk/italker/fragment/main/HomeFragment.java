package com.klk.italker.fragment.main;


import com.klk.common.app.BaseFragment;
import com.klk.common.widget.view.GalleryView;
import com.klk.italker.R;

import butterknife.BindView;

/**
 *
 */
public class HomeFragment extends BaseFragment {


    @BindView(R.id.gv_home)
    GalleryView gvHome;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {
        super.initData();

//        gvHome.setup(getLoaderManager(), new GalleryView.SelectedChangedListener() {
//            @Override
//            public void onSelectedChanged(int imageListCount) {
//
//            }
//        });
    }

}
