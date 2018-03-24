package com.klk.common.widget.view;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.klk.common.R;
import com.klk.common.widget.recyclerview.BaseRecyclerAdapter;
import com.klk.common.widget.recyclerview.BaseRecyclerAdapter.BaseRecyclerViewHolder;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 图片选择器的控件
 */
public class GalleryView extends RecyclerView {
    private static final String TAG = "klk";
    private static final int MAX_SELECTED = 3;//最大选中图片的数量
    private static final int LOADER_ID = 0x0100;
    private static final long IMAGE_SIZE = 10 * 1024;

    private SelectedChangedListener mChangedListener;

    private MyLoaderCallback loaderCallback = new MyLoaderCallback();
    //所有选中的图片
    private List<Image> mSelectedImage = new LinkedList<>();
    private GalleryAdapter mAdapter = new GalleryAdapter(mSelectedImage);

    public GalleryView(Context context) {
        super(context);
        init();
    }

    public GalleryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GalleryView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setLayoutManager(new GridLayoutManager(getContext(), 4));
        Log.e(TAG, "init: 1111111111111111" );
        setAdapter(mAdapter);
        mAdapter.setListener(new BaseRecyclerAdapter.AdapterListenerImpl<Image>() {
            @Override
            public void onItemClick(BaseRecyclerViewHolder recyclerViewHolder, Image image) {
                if (onItemSelectedClick(image)) {
                    recyclerViewHolder.update(image);
                }

            }
        });

    }

    /**
     * 当item被点击，判断是否进行item状态刷新
     *
     * @return
     */
    private boolean onItemSelectedClick(Image image) {
        boolean isNotifyUpdate;
        if (mSelectedImage.contains(image)) {
            image.isSelected = false;
            mSelectedImage.remove(image);
            isNotifyUpdate = true;
        } else {
            if (mSelectedImage.size() >= MAX_SELECTED) {
                //// TODO: 2018/3/24   Toast
                isNotifyUpdate = false;

            } else {
                image.isSelected = true;
                mSelectedImage.add(image);
                isNotifyUpdate = true;
            }
        }
        if (isNotifyUpdate) {
            notifySelectedChanged();
        }

        return isNotifyUpdate;
    }

    /**
     * 提醒选中的图片发生了改变
     */
    private void notifySelectedChanged() {
        if (mChangedListener != null) {
            mChangedListener.onSelectedChanged(mSelectedImage.size());
        }
    }

    /**
     * 初始化方法
     *
     * @param loaderManager
     * @param mListener
     * @return loader的id ,可用于销毁loader
     */
    public int setup(LoaderManager loaderManager, SelectedChangedListener mListener) {
        this.mChangedListener = mListener;
        Log.e(TAG, "setup: 0000000000000000");
        loaderManager.initLoader(LOADER_ID, null, loaderCallback);
        return LOADER_ID;
    }

    /**
     * 获取所有的选中图片的路径
     *
     * @return
     */
    public String[] getSelectedImagePath() {
        String[] paths = new String[mSelectedImage.size()];
        int id = 0;
        for (Image image : mSelectedImage) {
            paths[id++] = image.path;
        }

        return paths;
    }

    /**
     * 清空选中的图片
     */
    public void clear() {
        for (Image image : mSelectedImage) {
            image.isSelected = false;
        }
        mSelectedImage.clear();
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 通知数据进行了加载
     *
     * @param images
     */
    private void updateSource(ArrayList<Image> images) {
        mAdapter.replace(images);
    }

    /**
     * 选中图片发生改变的监听器
     */
    public interface SelectedChangedListener {
        void onSelectedChanged(int imageListCount);
    }

    /**
     * 图片的bean类
     */
    private class Image {
        public int id;
        public String path;
        public boolean isSelected;
        public long date;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Image image = (Image) o;

            return path != null ? path.equals(image.path) : image.path == null;

        }

        @Override
        public int hashCode() {
            return path != null ? path.hashCode() : 0;
        }
    }

    /**
     * loader的回调
     */
    private class MyLoaderCallback implements LoaderManager.LoaderCallbacks<Cursor> {

        private final String[] IMAGE_PROJECTION = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_ADDED
        };

        @Override
        public Loader onCreateLoader(int id, Bundle args) {
            Log.e(TAG, "onCreateLoader: "+"1111111111111111111111111" );
            if (LOADER_ID == id) {
                return new CursorLoader(getContext()
                        , MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        , IMAGE_PROJECTION //要查询的列
                        , null
                        , null
                        , IMAGE_PROJECTION[2] + " DESC");//按时间倒序查询
            }
            return null;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            Log.e(TAG, "onCreateLoader: "+"22222222222222222222" );
            ArrayList<Image> images = new ArrayList<>();
            //当数据load完成
            if (data != null && data.getCount() > 0) {
                data.moveToFirst();//将游标移到首位
                do {
                    int id = data.getInt(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                    String path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
                    long date = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));

                    File file = new File(path);
                    //如果图片不存在，或者太小，直接跳过
                    if (!file.exists() || file.length() < IMAGE_SIZE) {
                        continue;
                    }

                    Image image = new Image();
                    image.id = id;
                    image.path = path;
                    image.date = date;
                    images.add(image);
                } while (data.moveToNext());
            }
            Log.e(TAG, "onLoadFinished: images5555555555555555555"+images);
            updateSource(images);
        }

        @Override
        public void onLoaderReset(Loader loader) {
            //刷新
            updateSource(null);
        }
    }

    /**
     * 图片选择界面的适配器
     */
    private class GalleryAdapter extends BaseRecyclerAdapter<Image> {
        public GalleryAdapter(List<Image> imageList) {
            super(imageList);
        }

        @Override
        protected BaseRecyclerViewHolder onCreateViewHolder(View root, int viewType) {
            return new ViewHolder(root);
        }

        @Override
        protected int getItemViewType(Image image, int position) {
            return R.layout.cell_gallery;
        }


    }

    /**
     * item 的holder
     */
    private class ViewHolder extends BaseRecyclerViewHolder<Image> {


        View viewShadow;
        CheckBox cbSelected;
        ImageView ivImage;
        public ViewHolder(View itemView) {
            super(itemView);
            viewShadow = findViewById(R.id.view_shadow);
            cbSelected = findViewById(R.id.cb_selected);
            ivImage = findViewById(R.id.iv_image);
        }

        @Override
        public void onBind(Image image) {
            Glide.with(getContext())
                    .load(image.path)
                    .centerCrop()
                    .diskCacheStrategy(null)//不使用缓存，直接从本地加载
                    .placeholder(R.color.grey_200)
                    .into(ivImage);

            cbSelected.setChecked(image.isSelected);
            viewShadow.setVisibility(VISIBLE);
        }
    }


}
