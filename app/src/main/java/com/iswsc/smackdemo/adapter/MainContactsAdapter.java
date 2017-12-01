package com.iswsc.smackdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iswsc.smackdemo.R;
import com.iswsc.smackdemo.listener.OnItemClickListener;
import com.iswsc.smackdemo.vo.ContactVo;

import java.util.ArrayList;

/**
 * @version 1.0
 * @email jacen@iswsc.com
 * Created by Jacen on 2017/9/3 2:12.
 */

public class MainContactsAdapter extends RecyclerView.Adapter<MainContactsAdapter.MyHolder> {

    private Context context;
    private OnItemClickListener l;
    private ArrayList<ContactVo> mList;

    public MainContactsAdapter(Context context, ArrayList<ContactVo> mList, OnItemClickListener l) {
        this.context = context;
        this.mList = mList;
        this.l = l;
    }

    public void updateList(ArrayList<ContactVo> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mList != null)
            return mList.size();
        return 0;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_contacts, null);
        return new MyHolder(view, l);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        ContactVo vo = mList.get(position);
        holder.mUserName.setText(vo.getShowName());
        holder.mContent.setText(vo.getJid() + "[" + vo.getType() + "]");
    }


    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View view;
        OnItemClickListener l;

        ImageView mAvatar;
        TextView mUserName;
        TextView mTime;
        TextView mContent;

        MyHolder(View view, OnItemClickListener l) {
            super(view);
            view.setOnClickListener(this);
            this.l = l;
            mAvatar = (ImageView) view.findViewById(R.id.avatar);
            mUserName = (TextView) view.findViewById(R.id.username);
            mTime = (TextView) view.findViewById(R.id.time);
            mContent = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public void onClick(View v) {
            l.onItemClick(v, getLayoutPosition());
        }
    }
}
