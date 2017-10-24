package com.iswsc.smackdemo.vo;

import com.iswsc.smackdemo.enums.ChatType;

/**
 * Created by Jacen on 2017/10/20 17:12.
 * jacen@iswsc.com
 */

public class ChatMessageVo {
    private String messageID;
    private String chatJid;
    private boolean showTime = false;//显示时间
    private int messageStatus;//发送状态
    private boolean isMe = false;//是不是我的发的信息
    private int imagePercent;//图片上传百分比
    private int chatType;//消息体类型 文字 语音 图片
    private String content;//消息内容
    private long sendTime;//发送时间戳

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getChatJid() {
        return chatJid;
    }

    public void setChatJid(String chatJid) {
        this.chatJid = chatJid;
    }

    public boolean isShowTime() {
        return showTime;
    }

    public void setShowTime(boolean showTime) {
        this.showTime = showTime;
    }

    public int getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(int messageStatus) {
        this.messageStatus = messageStatus;
    }

    public boolean isMe() {
        return isMe;
    }

    public void setMe(boolean me) {
        isMe = me;
    }

    public int getImagePercent() {
        return imagePercent;
    }

    public void setImagePercent(int imagePercent) {
        this.imagePercent = imagePercent;
    }

    public ChatType getChatType() {
        return ChatType.fromCode(chatType);
    }

    public void setChatType(int chatType) {
        this.chatType = chatType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }
}
