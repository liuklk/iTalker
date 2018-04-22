package com.klk.italker.fragment.user;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.klk.common.app.BasePresenterFragment;
import com.klk.common.app.MyApplication;
import com.klk.common.widget.view.PortraitView;
import com.klk.factory.Factory;
import com.klk.factory.net.UpLoadHelper;
import com.klk.factory.presenter.user.UpdateUserInfoContract;
import com.klk.factory.presenter.user.UpdateUserInfoPresenter;
import com.klk.italker.R;
import com.klk.italker.activity.MainActivity;
import com.klk.italker.fragment.media.GalleryFragment;
import com.yalantis.ucrop.UCrop;

import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.EditText;
import net.qiujuer.genius.ui.widget.Loading;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateFragment extends BasePresenterFragment<UpdateUserInfoContract.IPresenter>
        implements UpdateUserInfoContract.IView {
    private static final String TAG = "UpdateFragment";


    @BindView(R.id.pv_account)
    PortraitView pvAccount;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.edit_desc)
    EditText editDesc;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.loading)
    Loading loading;

    private boolean isMan =true ;
    private String desc ;
    private String pvAccountPath;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_update;
    }

    @Override
    protected void initWidget(View view) {
        super.initWidget(view);

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
            pvAccountPath = resultUri.getPath() ;
            if (resultUri != null) {
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
                        Log.e(TAG, "run: path:" + path);
                        Log.e(TAG, "run: url:" + url);
                    }
                });
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            MyApplication.Toast(R.string.data_rsp_error_unknown);
        }
    }

    @Override
    public void updateSuccess() {
        MainActivity.show(getContext());
        getActivity().finish();
    }

    @Override
    protected UpdateUserInfoContract.IPresenter initPresenter() {
        return new UpdateUserInfoPresenter(this);
    }

    @Override
    public void showError(@StringRes int id) {
        super.showError(id);


        //进度条停止
        loading.stop();
        pvAccount.setEnabled(true);
        editDesc.setEnabled(true);
        ivSex.setEnabled(true);
        btnSubmit.setEnabled(true);


    }

    @Override
    public void showLoading() {
        super.showLoading();
        loading.start();

        pvAccount.setEnabled(false);
        editDesc.setEnabled(false);
        ivSex.setEnabled(false);
        btnSubmit.setEnabled(false);
    }


    @OnClick({R.id.pv_account, R.id.iv_sex, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pv_account:
                new GalleryFragment().setSelectedListener(new GalleryFragment.SelectedListener() {
                    @Override
                    public void onSelected(String path) {
                        cropImage(path);
                    }
                }).show(getChildFragmentManager(), GalleryFragment.class.getName());
                break;
            case R.id.iv_sex:
                isMan =!isMan;
                Drawable drawable = getResources()
                        .getDrawable(isMan?R.drawable.ic_sex_man:R.drawable.ic_sex_woman);
                ivSex.setImageDrawable(drawable);
                ivSex.getBackground().setLevel(isMan?0:1);
                break;
            case R.id.btn_submit:
                desc = editDesc.getText().toString();
                mPresenter.update(pvAccountPath,desc,isMan);
                break;
        }
    }
}
