package com.iswsc.smackdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.iswsc.smackdemo.adapter.listener.IViewItem;
import com.iswsc.smackdemo.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @email jacen@iswsc.com
 * Created by Jacen on 2017/12/31 0:51.
 */

public class JacenRecylerViewAdapter<T> extends RecyclerView.Adapter {
    protected Context context;
    protected OnItemClickListener l;
    protected List<T> mList;

    private SparseArray<IViewItem> sparseArray;

    /**
     * 如果是单布局 则sparseArray.put(0,IViewItemImpl);
     * 否则复写getItemViewType(int position)方法 对应的布局 对应的key
     * @param context
     * @param l
     * @param mList
     * @param sparseArray
     */
    public JacenRecylerViewAdapter(Context context, List<T> mList, SparseArray<IViewItem> sparseArray, OnItemClickListener l) {
        this.context = context;
        this.l = l;
        this.mList = mList;
        this.sparseArray = sparseArray;
    }

    public void updateList(List<T> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void addData(T data,int position){
        if(mList == null){
            mList = new ArrayList<T>();
        }
        mList.add(position,data);

        notifyItemInserted(position);
        notifyItemChanged(position);
    }

    public void updateData(T data, int position){
        if(mList == null) return;
        mList.remove(position);
        mList.add(position,data);
    }



    public T getData(int position){
        return mList != null ? mList.get(position) : null ;
    }

    public void removeData(int position){
        mList.remove(position);

        notifyItemRemoved(position);
        notifyItemChanged(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return sparseArray.get(viewType).onCreateViewHolder(context,parent,l);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        sparseArray.get(getItemViewType(position)).onBindViewHolder(holder,mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }
}
