package com.iswsc.smackdemo.vo;

import com.iswsc.smackdemo.enums.ChatType;

import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Stanza;
import org.jxmpp.util.XmppStringUtils;

import java.io.Serializable;

/**
 * Created by Jacen on 2017/10/20 17:12.
 * jacen@iswsc.com
 */

public class ChatMessageVo implements Serializable {

    private String messageID;
    private String chatJid;
    private String content;//消息内容
    private int chatType;//消息体类型 文字 语音 图片
    private long sendTime;//发送时间戳
    private boolean showTime = false;//显示时间
    private boolean isMe = false;//是不是我的发的信息
    private int messageStatus;//发送状态
    private int unRead;//未读

    private int imagePercent;//图片上传百分比

    public int getUnRead() {
        return unRead;
    }

    public void setUnRead(int unRead) {
        this.unRead = unRead;
    }

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

    public ChatMessageVo parseMessage(Message msg) {
        setMessageID(msg.getStanzaId());
        setChatJid(XmppStringUtils.parseBareJid(msg.getFrom()));
        setContent(msg.getBody());
        setSendTime(System.currentTimeMillis());
        return this;
    }
}
