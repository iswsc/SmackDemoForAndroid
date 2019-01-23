package com.iswsc.smackdemo.xmpp;

import android.text.TextUtils;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.packet.Element;
import org.jivesoftware.smack.provider.ExtensionElementProvider;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;


/**
 * @author jacen
 * @date 2019/1/23 19:19
 * @email jacen@iswsc.com
 */
public class UserInfoPacketExtensionProvider extends ExtensionElementProvider {

    @Override
    public Element parse(XmlPullParser parser, int initialDepth) throws XmlPullParserException, IOException, SmackException {
        boolean done = false;
        UserInfoExtensionElement element = new UserInfoExtensionElement();
        while (!done) {
            int eventType = parser.next();
            String name = parser.getName();
            if (eventType == XmlPullParser.START_TAG) {
                if (TextUtils.equals(name, UserInfoExtensionElement.avatarElement)) {
                    element.setAvatarUrl(parser.nextText());
                } else if (TextUtils.equals(name, UserInfoExtensionElement.nickNameElement)) {
                    element.setNickName(parser.nextText());
                } else if (TextUtils.equals(name, UserInfoExtensionElement.timeElement)) {
                    element.setMsgTime(Long.parseLong(parser.nextText()));
                }
            }
            if(eventType == XmlPullParser.END_TAG) {
                if(TextUtils.equals(name, UserInfoExtensionElement.elementName)){
                    done = true;
                }
            }
        }
            return element;
        }
    }
