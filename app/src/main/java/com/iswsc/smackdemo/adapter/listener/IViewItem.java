package com.iswsc.smackdemo.adapter.listener;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.iswsc.smackdemo.listener.OnItemClickListener;
import com.iswsc.smackdemo.vo.ChatMessageVo;

/**
 * @version 1.0
 * @email jacen@iswsc.com
 * Created by Jacen on 2017/12/28 22:21.
 */

public interface IViewItem<D,H extends RecyclerView.ViewHolder> {
    /**
     *
     * @param context
     * @param parent
     * @param l 这个是做类似于ListView OnItemClickListener 如需长按时间 多添加一个OnItemLongClickListener
     * @return
     */
    RecyclerView.ViewHolder onCreateViewHolder(Context context,ViewGroup parent,OnItemClickListener l);
    void onBindViewHolder(RecyclerView.ViewHolder holder,D data);
}
