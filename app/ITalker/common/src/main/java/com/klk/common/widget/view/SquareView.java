package com.klk.common.widget.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * @Des
 * @Auther Administrator
 * @date 2018/3/23  16:42
 */

public class SquareView extends FrameLayout {
    public SquareView(Context context) {
        super(context);
    }

    public SquareView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //测量是高度宽度都设置为宽度，就为正方形
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
