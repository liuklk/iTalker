package com.klk.common.widget.recyclerview;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.klk.common.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Des
 * @Auther Administrator
 * @date 2018/3/21  16:55
 */
public abstract class BaseRecyclerAdapter<Data>
        extends RecyclerView.Adapter<BaseRecyclerAdapter.BaseRecyclerViewHolder>
        implements View.OnClickListener,View.OnLongClickListener ,RecyclerCallback<Data>{

    private final List<Data> mDataList ;
    private AdapterListener mListener;

    /**
     * 构造函数
     */
    public BaseRecyclerAdapter() {
        this(null);
    }

    public BaseRecyclerAdapter(AdapterListener mListener) {
        this(new ArrayList<Data>(),mListener);
    }

    public BaseRecyclerAdapter(List<Data> dataList, AdapterListener mListener) {
        this.mDataList = dataList;
        this.mListener = mListener;
    }

    /**
     * 当创建ViewHolderS
     * @param parent RecyclerView
     * @param viewType  布局的类型 ，这里约定为子布局layoutId
     * @return  BaseRecyclerViewHolder
     */
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //获取item的根布局
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(viewType, parent, false);
        //创建viewHolder
        BaseRecyclerViewHolder viewHolder = onCreateViewHolder(root, viewType);
        //设置点击事件
        root.setOnClickListener(this);
        root.setOnLongClickListener(this);
        //设置注解
        viewHolder.mUnbinder = ButterKnife.bind(root, parent);
        //进行界面与viewHolder绑定
        root.setTag(R.id.tag_recycler_holder,viewHolder);
        //绑定callback
        viewHolder.mCallback = this ;
        return viewHolder;
    }


    /**
     * 创建viewHolder
     * @param root item 的跟布局
     * @param viewType  item的视图类型
     * @return  veiwHolder
     */
    protected abstract BaseRecyclerViewHolder onCreateViewHolder(View root , int viewType);

    /**
     * 获取子布局的layoutId
     * @param position 坐标
     * @return  子布局的layoutId
     */
    @Override
    public int getItemViewType(int position) {
        return getItemViewType(mDataList.get(position),position);
    }

    /**
     * 获取子布局的layoutId
     * @param data  数据
     * @param position  坐标
     * @return  子布局的layoutId
     */
    @LayoutRes
    protected abstract int getItemViewType(Data data ,int position);

    /**
     * 绑定数据到viewHolder
     * @param holder  BaseRecyclerViewHolder
     * @param position 坐标
     */
    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        //获取数据
        Data data = mDataList.get(position);
        //触发绑定
        holder.bind(data);
    }

    /**
     * 获取数据的数量
     * @return  数量
     */
    private static final String TAG = "klk";
    @Override
    public int getItemCount() {
        Log.e(TAG, "mDataList: "+mDataList.size());
        return mDataList.size();
    }

    /**
     * 插入一个数据
     * @param data
     */
    public void add(Data data){
        mDataList.add(data);
        notifyItemInserted(mDataList.size()-1);
    }

    /**
     * 插入一堆数据
     * @param datas
     */
    public void  add(Data...datas){
        if(mDataList!=null&&mDataList.size()>0){
            int startPos = mDataList.size();
            Collections.addAll(mDataList,datas);
            notifyItemRangeInserted(startPos,datas.length);
        }

    }
    /**
     *插入一个集合数据
     * @param datas
     */
    public void add(ArrayList<Data> datas){
        if(mDataList!=null&&mDataList.size()>0){
            int startPos = mDataList.size();
            mDataList.addAll(mDataList.size(),datas);
            notifyItemRangeInserted(startPos,datas.size());
        }

    }

    /**
     * 清除所有数据
     */
    public void clear(){
        mDataList.clear();
        notifyDataSetChanged();
    }

    /**
     * 清除原来集合，替换原有集合
     * @param datas
     */
    public void replace(ArrayList<Data> datas){
        mDataList.clear();
        if(datas==null||datas.size()==0){
            return;
        }
        mDataList.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 进行数据的更新
     * @param data
     * @param recyclerViewHolder
     */
    @Override
    public void update(Data data, BaseRecyclerViewHolder recyclerViewHolder) {
        //得到当前的坐标
        int position = recyclerViewHolder.getAdapterPosition();
        Log.e(TAG, "update: mDataList"+mDataList);
        Log.e(TAG, "update: mDataList.size"+mDataList.size());
        Log.e(TAG, "update: position"+position);

        if(position>=0){
            mDataList.remove(position);
            mDataList.add(position,data);
            notifyItemChanged(position);
        }

    }
    /**
     * 当点击item
     * @param view
     */
    @Override
    public void onClick(View view) {
        BaseRecyclerViewHolder holder = (BaseRecyclerViewHolder) view.getTag(R.id.tag_recycler_holder);
        int pos = holder.getAdapterPosition() ;
        if(mListener!=null){
            mListener.onItemClick(holder,mDataList.get(pos));
        }

    }

    /**
     * 当长按item
     * @param view
     * @return
     */
    @Override
    public boolean onLongClick(View view) {
        BaseRecyclerViewHolder holder = (BaseRecyclerViewHolder) view.getTag(R.id.tag_recycler_holder);
        int pos = holder.getAdapterPosition() ;
        if(mListener!=null){
            mListener.onItemLongClick(holder,mDataList.get(pos));
            return true ;
        }
        return false;
    }

    /**
     * 设置监听器
     * @param listener
     */
    public void setListener(AdapterListener listener){
        this.mListener = listener;
    }
    public interface AdapterListener<Data>{
        void onItemClick(BaseRecyclerViewHolder recyclerViewHolder , Data data);
        void onItemLongClick(BaseRecyclerViewHolder recyclerViewHolder , Data data);
    }

    /**
     * 监听器的实现类
     */
    public static abstract class AdapterListenerImpl<Data> implements AdapterListener<Data>{

        @Override
        public void onItemClick(BaseRecyclerViewHolder recyclerViewHolder, Data data) {

        }

        @Override
        public void onItemLongClick(BaseRecyclerViewHolder recyclerViewHolder, Data data) {

        }
    }

    /**
     * 自定以viewHolder
     * @param <Data>  泛型
     */
    public static abstract class BaseRecyclerViewHolder<Data> extends RecyclerView.ViewHolder{
        private Data mData ;
        private Unbinder mUnbinder ;
        private RecyclerCallback mCallback;
        public BaseRecyclerViewHolder(View itemView) {
            super(itemView);
        }

        /**
         *      绑定数据
         * @param data 数据
         */
         void  bind(Data data){
             mData = data;
             //触发绑定事件
             onBind(data);
         }

        /**
         * 当数据绑定时
         * @param data 数据
         */
         public abstract void onBind(Data data);

         public void updateData(Data data){
             if(this.mCallback!=null){
                 this.mCallback.update(data,this);
             }

        }
    }
}
