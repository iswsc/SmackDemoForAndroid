package com.iswsc.smackdemo.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by Jacen on 2017/8/23 11:22.
 * jacen@iswsc.com
 */

public class ContactVo implements Serializable/* implements Parcelable */{

    private String nickName;
    private String name;
    private String fullName;
    private String fullJid;
    private String avatar;
    private String status;//在线状态
    private String type;//好友关系类型

    public ContactVo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFullJid() {
        return fullJid;
    }

    public void setFullJid(String fullJid) {
        this.fullJid = fullJid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShowName() {
        if(!TextUtils.isEmpty(nickName)){
            return nickName;
        } else if (!TextUtils.isEmpty(name))
            return name;
        return fullJid;
    }

//    public static final Creator<ContactVo> CREATOR = new Creator<ContactVo>() {
//        @Override
//        public ContactVo createFromParcel(Parcel in) {
//            return new ContactVo(in);
//        }
//
//        @Override
//        public ContactVo[] newArray(int size) {
//            return new ContactVo[size];
//        }
//    };
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    protected ContactVo(Parcel in) {
//        nickName = in.readString();
//        name = in.readString();
//        fullName = in.readString();
//        fullJid = in.readString();
//        avatar = in.readString();
//        status = in.readString();
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(nickName);
//        dest.writeString(name);
//        dest.writeString(fullName);
//        dest.writeString(fullJid);
//        dest.writeString(avatar);
//        dest.writeString(status);
//    }
}
