package com.klk.common.widget.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.klk.common.R;
import com.klk.common.widget.recyclerview.BaseRecyclerAdapter;
import com.klk.common.widget.recyclerview.BaseRecyclerAdapter.BaseRecyclerViewHolder;

/**
 * 图片选择器的控件
 */
public class GalleryView extends RecyclerView {

    private GalleryAdapter mAdapter= new GalleryAdapter();

    public GalleryView(Context context) {
        super(context,null);
    }

    public GalleryView(Context context, AttributeSet attrs) {
        super(context, attrs,-1);
    }

    public GalleryView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
            setLayoutManager(new GridLayoutManager(getContext(),4));
            setAdapter(mAdapter);
            mAdapter.setListener(new BaseRecyclerAdapter.AdapterListenerImpl<Image>() {
                @Override
                public void onItemClick(BaseRecyclerViewHolder recyclerViewHolder, Image image) {
                    recyclerViewHolder.update(image);
                }
            });
    }
    private class Image{
            public int id ;
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
    private class GalleryAdapter extends BaseRecyclerAdapter<Image>{

        @Override
        protected BaseRecyclerViewHolder onCreateViewHolder(View root, int viewType) {
            return new ViewHolder(root);
        }

        @Override
        protected int getItemViewType(Image image, int position) {
            return R.layout.cell_gallery;
        }
    }

    private class ViewHolder extends BaseRecyclerViewHolder<Image>{

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(Image image) {

        }
    }
}
