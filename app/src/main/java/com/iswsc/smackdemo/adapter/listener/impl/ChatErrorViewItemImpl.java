package com.iswsc.smackdemo.adapter.listener.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iswsc.smackdemo.R;
import com.iswsc.smackdemo.adapter.listener.IViewItem;
import com.iswsc.smackdemo.listener.OnItemClickListener;
import com.iswsc.smackdemo.util.JacenUtils;
import com.iswsc.smackdemo.vo.ChatMessageVo;

/**
 * @version 1.0
 * @email jacen@iswsc.com
 * Created by Jacen on 2017/12/28 22:24.
 */

public class ChatErrorViewItemImpl implements IViewItem {
    
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(Context context, ViewGroup parent, OnItemClickListener l) {
        View left_text = LayoutInflater.from(context).inflate( R.layout.item_chat_text_error, parent,false);
        return new ErrorHolder(left_text, l);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, Object data) {
        ErrorHolder h = (ErrorHolder) holder;
        ChatMessageVo vo = (ChatMessageVo) data;
        h.mTime.setText(JacenUtils.parseChatTimer(vo.getSendTime()));
        h.mTime.setVisibility(vo.isShowTime() ? View.VISIBLE : View.GONE);
        h.mContent.setText(vo.getContent());
        Log.e("ChatAdapter" ,"unknow message " + vo.getContent());
    }

    class ErrorHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTime;
        TextView mContent;
        OnItemClickListener l;

        ErrorHolder(View view, OnItemClickListener l) {
            super(view);
            view.setOnClickListener(this);
            this.l = l;
            mTime = (TextView) view.findViewById(R.id.time);
            mContent = (TextView) view.findViewById(R.id.content);

        }

        @Override
        public void onClick(View v) {
            l.onItemClick(v, getLayoutPosition());
        }
    }
}
