package com.klk.italker.fragment.media;


import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.klk.common.widget.view.GalleryView;
import com.klk.common.widget.view.TransStatusBottomSheetDialog;
import com.klk.italker.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends BottomSheetDialogFragment implements GalleryView.SelectedChangedListener {

    private SelectedListener  mSelectedListener;
    private GalleryView gallery;

    public GalleryFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TransStatusBottomSheetDialog(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        gallery = root.findViewById(R.id.gallery);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        //初始化Gallery
        gallery.setup(getLoaderManager(),this);
    }

    @Override
    public void onSelectedChanged(int imageListCount) {
        if(imageListCount>0){
            dismiss();
            if(mSelectedListener!=null){
                //获取选中图片的路径
                String[] paths = gallery.getSelectedImagePath();
                //设置路径
                mSelectedListener.onSelected(paths[0]);
                //listener置为null,加速回收
                mSelectedListener=null;
            }
        }


    }


    public GalleryFragment setSelectedListener(SelectedListener selectedListener){
        this.mSelectedListener = selectedListener;
        return this ;
    }

    /**
     * 选中图片的监听器
     */
    public interface SelectedListener{
        void onSelected(String path);
    }
}
