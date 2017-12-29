package com.iswsc.smackdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.iswsc.smackdemo.adapter.listener.IViewItem;
import com.iswsc.smackdemo.adapter.listener.impl.ChatLeftTextViewItemImpl;
import com.iswsc.smackdemo.adapter.listener.impl.ChatRightTextViewItemImpl;
import com.iswsc.smackdemo.listener.OnItemClickListener;
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

    //    private final int MESSAGE_ERROR = -1;
    private final int MESSAGE_LEFT_TEXT = 1;
    private final int MESSAGE_RIGHT_TEXT = 2;

    private SparseArray<IViewItem> sparseArray;


    public ChatAdapter(Context context, List<ChatMessageVo> mList, OnItemClickListener l) {
        this.context = context;
        this.mList = mList;
        this.l = l;
        sparseArray = new SparseArray<IViewItem>();
        sparseArray.put(MESSAGE_LEFT_TEXT,new ChatLeftTextViewItemImpl());
        sparseArray.put(MESSAGE_RIGHT_TEXT,new ChatRightTextViewItemImpl());
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
//        notifyItemInserted(mList.size() - 1);
        notifyItemChanged(mList.size() - 1);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessageVo vo = mList.get(position);
        return vo.isMe() ? vo.getChatType().getRight() : vo.getChatType().getLeft();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return sparseArray.get(viewType).onCreateViewHolder(context,parent,l);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatMessageVo vo = mList.get(position);
        sparseArray.get(getItemViewType(position)).onBindViewHolder(holder,vo);
    }
}
