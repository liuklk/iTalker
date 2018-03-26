package com.klk.common.widget.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.design.widget.BottomSheetDialog;
import android.view.ViewGroup;
import android.view.Window;

import com.klk.common.utils.UiTool;

import net.qiujuer.genius.ui.Ui;

/**
 * @Des  透明状态栏的BottomSheetDialog
 * @Auther Administrator
 * @date 2018/3/26  11:30
 */

public class TransStatusBottomSheetDialog extends BottomSheetDialog{

    public TransStatusBottomSheetDialog(@NonNull Context context) {
        super(context);
    }

    public TransStatusBottomSheetDialog(@NonNull Context context, @StyleRes int theme) {
        super(context, theme);
    }

    protected TransStatusBottomSheetDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if(window==null){
            return;
        }
        int screenHeight = UiTool.getScreenHeight(getOwnerActivity());
        int statusHeight = (int) Ui.dipToPx(getOwnerActivity().getResources(), UiTool.getStatusBarHeight(getOwnerActivity()));

        int dialogHeight = screenHeight - statusHeight;

        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,dialogHeight<=0?ViewGroup.LayoutParams.MATCH_PARENT:dialogHeight);


    }
}
