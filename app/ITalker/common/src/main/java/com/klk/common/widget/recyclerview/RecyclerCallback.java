package com.klk.common.widget.recyclerview;

/**
 * @Des
 * @Auther Administrator
 * @date 2018/3/21  17:48
 */

public interface RecyclerCallback<Data> {
    void update(Data data ,BaseRecyclerAdapter.BaseRecyclerViewHolder recyclerViewHolder);
}
