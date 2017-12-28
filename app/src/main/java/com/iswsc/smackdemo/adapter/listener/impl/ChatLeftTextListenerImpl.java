package com.iswsc.smackdemo.adapter.listener.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.method.HideReturnsTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iswsc.smackdemo.R;
import com.iswsc.smackdemo.adapter.listener.ChatAdapterListener;
import com.iswsc.smackdemo.listener.OnItemClickListener;
import com.iswsc.smackdemo.util.JacenUtils;
import com.iswsc.smackdemo.vo.ChatMessageVo;

/**
 * @version 1.0
 * @email jacen@iswsc.com
 * Created by Jacen on 2017/12/28 22:24.
 */

public class ChatLeftTextListenerImpl implements ChatAdapterListener {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(Context context, LayoutInflater mInflater, ViewGroup parent, OnItemClickListener l) {
        View left_text = mInflater.inflate( R.layout.item_chat_text_left, parent,false);
        return new LeftTextHolder(left_text, l);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, ChatMessageVo vo) {
        LeftTextHolder h = (LeftTextHolder) holder;
        h.mTime.setText(JacenUtils.parseChatTimer(vo.getSendTime()));
        h.mTime.setVisibility(vo.isShowTime() ? View.VISIBLE : View.GONE);
        h.mContent.setText(vo.getContent());
        Log.d("ChatAdapter" ,"LeftTextHolder " + vo.getContent());
    }

    class LeftTextHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTime;
        ImageView mAvatar;
        TextView mContent;
        OnItemClickListener l;

        LeftTextHolder(View view, OnItemClickListener l) {
            super(view);
            view.setOnClickListener(this);
            this.l = l;
            mTime = (TextView) view.findViewById(R.id.time);
            mAvatar = (ImageView) view.findViewById(R.id.avatar);
            mContent = (TextView) view.findViewById(R.id.content);

        }

        @Override
        public void onClick(View v) {
            l.onItemClick(v, getLayoutPosition());
        }
    }
}
