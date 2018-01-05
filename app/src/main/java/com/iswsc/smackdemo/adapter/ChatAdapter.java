package com.iswsc.smackdemo.adapter;

import android.content.Context;
import android.util.SparseArray;

import com.iswsc.smackdemo.listener.OnItemClickListener;
import com.iswsc.smackdemo.vo.ChatMessageVo;

import java.util.List;

/**
 * @version 1.0
 * @email jacen@iswsc.com
 * Created by Jacen on 2017/9/3 2:12.
 */

public class ChatAdapter extends JacenRecylerViewAdapter {


    /**
     * 如果是单布局 则sparseArray.put(0,IViewItemImpl);
     * 否则复写getItemViewType(int position)方法 对应的布局 对应的key
     *
     * @param context
     * @param mList
     * @param sparseArray
     * @param l
     */
    public ChatAdapter(Context context, List mList, SparseArray sparseArray, OnItemClickListener l) {
        super(context, mList, sparseArray, l);
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessageVo vo = (ChatMessageVo) mList.get(position);
        return vo.isMe() ? vo.getChatType().getRight() : vo.getChatType().getLeft();
    }

}
