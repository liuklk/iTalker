package com.klk.italker.fragment.account;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.klk.common.app.BaseFragment;
import com.klk.common.app.MyApplication;
import com.klk.common.widget.view.PortraitView;
import com.klk.factory.Factory;
import com.klk.factory.net.UpLoadHelper;
import com.klk.italker.R;
import com.klk.italker.fragment.media.GalleryFragment;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.R.attr.maxHeight;
import static android.R.attr.maxWidth;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateFragment extends BaseFragment {
    private static final String TAG = "UpdateFragment";

    @BindView(R.id.pv_account)
    PortraitView pvAccount;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_update;
    }

    @Override
    protected void initWidget(View view) {
        super.initWidget(view);

    }


    @OnClick(R.id.pv_account)
    public void onPvAccountClicked() {

        new GalleryFragment().setSelectedListener(new GalleryFragment.SelectedListener() {
            @Override
            public void onSelected(String path) {
                cropImage(path);
            }
        }).show(getChildFragmentManager(),GalleryFragment.class.getName());
    }


    private void cropImage(String path) {

        UCrop.Options options = new UCrop.Options();
        //设置处理之后图片的格式为jpg
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        //设置图片的精度
        options.setCompressionQuality(96);
        //获取头像文件缓存的目录
        File portraitViewFile = MyApplication.getPortraitViewFile();
        UCrop.of(Uri.fromFile(new File(path)), Uri.fromFile(portraitViewFile))
                .withAspectRatio(1, 1)
                .withMaxResultSize(520, 520)
                .withOptions(options)
                .start(getActivity());
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            if(resultUri!=null){
                //设置图片
                Glide.with(this)
                        .load(resultUri)
                        .asBitmap()
                        .centerCrop()
                        .into(pvAccount);

                final String path = resultUri.getPath();
                Factory.runOnAysnc(new Runnable() {
                    @Override
                    public void run() {
                        String url = UpLoadHelper.upLoadPortrait(path);
                        Log.e(TAG, "run: path:"+path);
                        Log.e(TAG, "run: url:"+url);
                    }
                });
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }
}
