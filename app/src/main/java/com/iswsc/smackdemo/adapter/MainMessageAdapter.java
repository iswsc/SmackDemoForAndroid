package com.iswsc.smackdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iswsc.smackdemo.R;
import com.iswsc.smackdemo.listener.OnItemClickListener;
import com.iswsc.smackdemo.util.JacenUtils;
import com.iswsc.smackdemo.vo.ChatMessageVo;

import java.util.ArrayList;

/**
 * @version 1.0
 * @email jacen@iswsc.com
 * Created by Jacen on 2017/9/3 2:12.
 */

public class MainMessageAdapter extends RecyclerView.Adapter<MainMessageAdapter.MyHolder> {

    private Context context;
    private OnItemClickListener l;
    private ArrayList<ChatMessageVo> mList;

    public MainMessageAdapter(Context context, ArrayList<ChatMessageVo> mList, OnItemClickListener l) {
        this.context = context;
        this.mList = mList;
        this.l = l;
    }

    public void updateList(ArrayList<ChatMessageVo> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }
    public ChatMessageVo getItem(int position){
        return mList.get(position);
    }

    public void updateUnRead(int position){
        mList.get(position).setUnRead(0);
        notifyItemChanged(position);
    }

    public synchronized void updateChatMessage(ChatMessageVo msg){
        for (int i = 0; i < mList.size(); i++) {
            ChatMessageVo vo = mList.get(i);
            if(TextUtils.equals(vo.getChatJid(),msg.getChatJid())){
                msg.setUnRead(vo.getUnRead() + 1);
                mList.remove(i);
                mList.add(0,msg);
                if(i == 0){
                    notifyItemChanged(0);
                }else{
                    notifyItemRemoved(i);
                    notifyItemInserted(0);
                    notifyItemChanged(i);
                    notifyItemChanged(0);
                }
                return;
            }
        }
        mList.add(0,msg);
        notifyItemInserted(0);
        notifyItemChanged(0);
    }

    @Override
    public int getItemCount() {
        if (mList != null)
            return mList.size();
        return 0;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_message, null);
        return new MyHolder(view, l);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        ChatMessageVo vo = mList.get(position);
        holder.mUserName.setText(vo.getChatJid());
        holder.mContent.setText(vo.getContent());
        holder.mUnRead.setText(vo.getUnRead() + "");
        holder.mTime.setText(JacenUtils.parseChatTimer(vo.getSendTime()));
        if (vo.getUnRead() == 0) {
            holder.mUnRead.setVisibility(View.GONE);
        }else{
            holder.mUnRead.setVisibility(View.VISIBLE);
        }
    }


    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnItemClickListener l;

        ImageView mAvatar;
        TextView mUserName;
        TextView mTime;
        TextView mContent;
        TextView mUnRead;

        MyHolder(View view, OnItemClickListener l) {
            super(view);
            view.setOnClickListener(this);
            this.l = l;
            mAvatar = (ImageView) view.findViewById(R.id.avatar);
            mUserName = (TextView) view.findViewById(R.id.username);
            mTime = (TextView) view.findViewById(R.id.time);
            mContent = (TextView) view.findViewById(R.id.content);
            mUnRead = (TextView) view.findViewById(R.id.unread);
        }

        @Override
        public void onClick(View v) {
            l.onItemClick(v, getLayoutPosition());
        }
    }
}
