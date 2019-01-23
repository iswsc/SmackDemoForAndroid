package com.iswsc.smackdemo.xmpp;

import org.jivesoftware.smack.packet.ExtensionElement;

/**
 * @author jacen
 * @date 2019/1/23 18:43
 * @email jacen@iswsc.com
 */
public class UserInfoExtensionElement implements ExtensionElement {

    public static final String elementName = "userInfo";
    public static final  String nameSpace = "jabber:client";

    public static final String nickNameElement = "nickName";
    public static final String avatarElement = "avatarUrl";
    public static final String timeElement = "msgTime";

    private String nickName;
    private String avatarUrl;
    private long msgTime;

    public UserInfoExtensionElement(String nickName, String avatarUrl, long msgTime) {
        this.nickName = nickName;
        this.avatarUrl = avatarUrl;
        this.msgTime = msgTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public long getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(long msgTime) {
        this.msgTime = msgTime;
    }

    public UserInfoExtensionElement() {
    }

    @Override
    public String getNamespace() {
        return nameSpace;
    }

    @Override
    public String getElementName() {
        return elementName;
    }

    @Override
    public CharSequence toXML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<")
                .append(elementName)
                .append(">")

                .append("<")
                .append(nickNameElement)
                .append(">")

                .append(nickName)

                .append("</")
                .append(nickNameElement)
                .append(">")

                .append("<")
                .append(avatarElement)
                .append(">")

                .append(avatarUrl)

                .append("</")
                .append(avatarElement)
                .append(">")

                .append("<")
                .append(timeElement)
                .append(">")

                .append(msgTime)

                .append("</")
                .append(timeElement)
                .append(">")

                .append("</")
                .append(elementName)
                .append(">")

        ;
        return sb.toString();
    }
}
