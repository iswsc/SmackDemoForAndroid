package com.iswsc.smackdemo.vo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jacen on 2017/8/23 11:22.
 * jacen@iswsc.com
 */

public class ContactVo implements Parcelable {

    private String nickName;
    private String fullJid;
    private String avatar;
    private String status;
    private String type;

    public ContactVo() {
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

    public static final Creator<ContactVo> CREATOR = new Creator<ContactVo>() {
        @Override
        public ContactVo createFromParcel(Parcel in) {
            return new ContactVo(in);
        }

        @Override
        public ContactVo[] newArray(int size) {
            return new ContactVo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    protected ContactVo(Parcel in) {
        nickName = in.readString();
        fullJid = in.readString();
        avatar = in.readString();
        status = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nickName);
        dest.writeString(fullJid);
        dest.writeString(avatar);
        dest.writeString(status);
    }
}
