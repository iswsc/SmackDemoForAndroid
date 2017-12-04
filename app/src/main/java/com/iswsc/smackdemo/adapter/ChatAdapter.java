package com.iswsc.smackdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iswsc.smackdemo.R;
import com.iswsc.smackdemo.listener.OnItemClickListener;
import com.iswsc.smackdemo.util.JacenUtils;
import com.iswsc.smackdemo.vo.ChatMessageVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @email jacen@iswsc.com
 * Created by Jacen on 2017/9/3 2:12.
 */

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private OnItemClickListener l;
    private List<ChatMessageVo> mList;
    private LayoutInflater mInflater;

    //    private final int MESSAGE_ERROR = -1;
    private final int MESSAGE_LEFT_TEXT = 1;
    private final int MESSAGE_RIGHT_TEXT = 2;


    public ChatAdapter(Context context, List<ChatMessageVo> mList, OnItemClickListener l) {
        this.context = context;
        this.mList = mList;
        this.l = l;
        mInflater = LayoutInflater.from(context);
    }

    public void updateList(ArrayList<ChatMessageVo> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void addChatMessage(ChatMessageVo vo){
        if(mList == null){
            mList = new ArrayList<>();
        }
        mList.add(vo);
        notifyItemInserted(mList.size() - 1);
        notifyItemChanged(mList.size() - 1);
    }

    @Override
    public int getItemCount() {
        if (mList != null)
            return mList.size();
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessageVo vo = mList.get(position);
        if (vo.isMe()) {
            return MESSAGE_RIGHT_TEXT;
        } else {
            return MESSAGE_LEFT_TEXT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case MESSAGE_LEFT_TEXT:
                View left_text = mInflater.from(context).inflate(R.layout.item_chat_text_left, parent, false);
                return new LeftTextHolder(left_text, l);
            case MESSAGE_RIGHT_TEXT:
                View right_text = mInflater.from(context).inflate(R.layout.item_chat_text_right, parent, false);
                return new RightTextHolder(right_text, l);
            default:
                View view = View.inflate(context, R.layout.item_chat_text_error, null);
                return new LeftTextHolder(view, l);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatMessageVo vo = mList.get(position);
        if(holder instanceof LeftTextHolder){
            leftTextContent((LeftTextHolder) holder,vo);
        }else if(holder instanceof RightTextHolder){
            rightTextContent((RightTextHolder) holder,vo);

        }else{
            errorTextContent((ErrorHolder) holder,vo);
        }
    }

    private void leftTextContent(LeftTextHolder holder, ChatMessageVo vo){
        holder.mTime.setText(JacenUtils.parseChatTimer(vo.getSendTime()));
        holder.mTime.setVisibility(vo.isShowTime() ? View.VISIBLE : View.GONE);
        holder.mContent.setText(vo.getContent());
    }

    private void rightTextContent(RightTextHolder holder, ChatMessageVo vo){
        holder.mTime.setText(JacenUtils.parseChatTimer(vo.getSendTime()));
        holder.mTime.setVisibility(vo.isShowTime() ? View.VISIBLE : View.GONE);
        holder.mContent.setText(vo.getContent());
    }

    private void errorTextContent(ErrorHolder holder, ChatMessageVo vo){
        holder.mTime.setText(JacenUtils.parseChatTimer(vo.getSendTime()));
        holder.mTime.setVisibility(vo.isShowTime() ? View.VISIBLE : View.GONE);
        holder.mContent.setText(vo.getContent());
    }

    class RightTextHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTime;
        TextView mContent;
        ImageView mAvatar;
        OnItemClickListener l;

        RightTextHolder(View view, OnItemClickListener l) {
            super(view);
            view.setOnClickListener(this);
            this.l = l;
            mContent = (TextView) view.findViewById(R.id.content);
            mAvatar = (ImageView) view.findViewById(R.id.avatar);
            mTime = (TextView) view.findViewById(R.id.time);
        }

        @Override
        public void onClick(View v) {
            l.onItemClick(v, getLayoutPosition());
        }
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
