package com.iswsc.smackdemo.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.iswsc.smackdemo.app.MyApp;
import com.iswsc.smackdemo.vo.ChatMessageVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacen on 2017/3/24 11:21.
 * jacen@iswsc.com
 */

public class ChatMessageDataBase {
    private static ChatMessageDataBase instance;
    private DbHelper helper;
    private SQLiteDatabase db;

    private ChatMessageDataBase() {
        helper = new DbHelper(MyApp.mContext, "chat.db");//数据库名固定写死，仅为方便测试
        db = helper.getWritableDatabase();
    }

    public static ChatMessageDataBase getInstance() {
        if (instance == null) {
            instance = new ChatMessageDataBase();
        }
        return instance;
    }

    public void saveChatMessage(ChatMessageVo vo) {
        ContentValues values = new ContentValues();
        values.put(TableField._FIELD_MESSAGE_ID, vo.getMessageID());
        values.put(TableField._FIELD_CHAT_JID, vo.getChatJid());
        values.put(TableField._FIELD_CONTENT, vo.getContent());
        values.put(TableField._FIELD_CHAT_TYPE, vo.getChatType().getId());
        values.put(TableField._FIELD_SEND_TIME, vo.getSendTime());
        values.put(TableField._FIELD_SHOW_TIME, vo.isShowTime() ? 1 : 0);
        values.put(TableField._FIELD_IS_ME, vo.isMe() ? 1 : 0);
        values.put(TableField._FIELD_MESSAGE_STATUS, vo.getMessageStatus());
        values.put(TableField._FIELD_UNREAD, vo.getUnRead());
        db.insert(TableField._TABLE_CHAT, TableField._ID, values);
    }

    public boolean isShowTime(String chatJid, long msgTime) {
        boolean result = false;
        String sql = "select max(" + TableField._FIELD_SEND_TIME + ") from " + TableField._TABLE_CHAT
                + " where " + TableField._FIELD_CHAT_JID + "=? and " + TableField._FIELD_SHOW_TIME + "=1";
        Cursor cursor = db.rawQuery(sql, new String[]{chatJid});
        if (cursor.moveToNext()) {
            long time = cursor.getLong(0);
            if (msgTime - time > 1000 * 60 * 3) {
                result = true;
            }
        } else {
            result = true;
        }
        cursor.close();
        return result;
    }


    public List<ChatMessageVo> getChatMessageListByChatJid(String chatJid) {
        List<ChatMessageVo> list = new ArrayList<>();
        String sql = "select * from chat where chatjid=? order by sendtime";
        Cursor cursor = db.rawQuery(sql, new String[]{chatJid});
        while (cursor.moveToNext()) {
            list.add(getChatMessageVoByCursor(cursor));
        }
        cursor.close();
        return list;
    }

    public void clearUnReadByJid(String chatJid){
        ContentValues values = new ContentValues();
        values.put(TableField._FIELD_UNREAD,0);
        db.update(TableField._TABLE_CHAT,values,TableField._FIELD_CHAT_JID + "=?" ,new String[]{chatJid});
    }

    public ArrayList<ChatMessageVo> getChatMessageListEvent() {
        ArrayList<ChatMessageVo> list = new ArrayList<>();
        String sql = "select * ,sum(_unread) total_unread from chat group by chatjid order by sendtime desc";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            ChatMessageVo msg = getChatMessageVoByCursor(cursor);
            msg.setUnRead(cursor.getInt(cursor.getColumnIndex("total_unread")));
            list.add(msg);
        }
        cursor.close();
        return list;
    }

    @NonNull
    private ChatMessageVo getChatMessageVoByCursor(Cursor cursor) {
        ChatMessageVo msg = new ChatMessageVo();
        msg.setMessageID(cursor.getString(cursor.getColumnIndex(TableField._FIELD_MESSAGE_ID)));
        msg.setChatJid(cursor.getString(cursor.getColumnIndex(TableField._FIELD_CHAT_JID)));
        msg.setContent(cursor.getString(cursor.getColumnIndex(TableField._FIELD_CONTENT)));
        msg.setChatType(cursor.getInt(cursor.getColumnIndex(TableField._FIELD_CHAT_TYPE)));
        msg.setSendTime(cursor.getLong(cursor.getColumnIndex(TableField._FIELD_SEND_TIME)));
        msg.setShowTime(cursor.getInt(cursor.getColumnIndex(TableField._FIELD_SHOW_TIME)) == 1 ? true : false);
        msg.setMe(cursor.getInt(cursor.getColumnIndex(TableField._FIELD_IS_ME)) == 1 ? true : false);
        msg.setMessageStatus(cursor.getInt(cursor.getColumnIndex(TableField._FIELD_MESSAGE_STATUS)));
        msg.setUnRead(cursor.getInt(cursor.getColumnIndex(TableField._FIELD_UNREAD)));
        return msg;
    }

    public void deleteChatMessageByChatJid(String chatJid) {
        db.delete(TableField._TABLE_CHAT, TableField._FIELD_CHAT_JID + "=?", new String[]{chatJid});
    }

    public synchronized void close() {
        try {
            if (db != null) {
                db.close();
            }
            if (helper != null) {
                helper.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            instance = null;
        }
    }
}
