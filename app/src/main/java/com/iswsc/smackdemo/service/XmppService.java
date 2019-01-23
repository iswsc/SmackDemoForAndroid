package com.iswsc.smackdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.iswsc.smackdemo.db.ChatMessageDataBase;
import com.iswsc.smackdemo.enums.ChatType;
import com.iswsc.smackdemo.util.JacenUtils;
import com.iswsc.smackdemo.util.Logs;
import com.iswsc.smackdemo.xmpp.UserInfoExtensionElement;
import com.iswsc.smackdemo.xmpp.XmppAction;
import com.iswsc.smackdemo.xmpp.XmppUtils;
import com.iswsc.smackdemo.vo.ChatMessageVo;
import com.iswsc.smackdemo.vo.ContactVo;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.roster.RosterListener;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @version 1.0
 * @email jacen@iswsc.com
 * Created by Jacen on 2017/7/25 2:19.
 */

public class XmppService extends Service {

    private static final String TAG = "XmppService";
    private MyStanzaListener myStanzaListener;
    private MyStanzaFilter myStanzaFilter;
    private MyRosterListener mRosterListener;
    private MyConnectionListener mConnectionListener;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myStanzaListener = new MyStanzaListener();
        myStanzaFilter = new MyStanzaFilter();
        mRosterListener = new MyRosterListener();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            String account = intent.getStringExtra("account");
            String password = intent.getStringExtra("password");

            if (XmppAction.ACTION_LOGIN.equals(action)) {//登录
                try {
                    XmppUtils.getInstance().distory();
                    XmppUtils.getInstance().init(myStanzaListener, myStanzaFilter, mRosterListener, mConnectionListener);
                    new LoginThread(account, password).start();
                } catch (Exception e) {
                    e.printStackTrace();
                    JacenUtils.intentLocalBroadcastReceiver(XmppService.this, XmppAction.ACTION_SERVICE_ERROR, null);
                }

            } else if (XmppAction.ACTION_REGISTER.equals(action)) {//注册
                try {
                    XmppUtils.getInstance().distory();
                    XmppUtils.getInstance().init(myStanzaListener, myStanzaFilter, mRosterListener, mConnectionListener);
                    new RegisterThread(account, password).start();
                } catch (Exception e) {
                    e.printStackTrace();
                    JacenUtils.intentLocalBroadcastReceiver(XmppService.this, XmppAction.ACTION_SERVICE_ERROR, null);
                }
            } else if (XmppAction.ACTION_USER_CONTACT.equals(action)) {//获取联系人
                new ContactThread().start();
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        XmppUtils.getInstance().distory();
    }

    class RegisterThread extends Thread {

        private String account;
        private String password;

        public RegisterThread(String account, String password) {
            this.account = account;
            this.password = password;
        }

        @Override
        public void run() {
            String action = XmppUtils.getInstance().registerXmpp(account, password);
            JacenUtils.intentLocalBroadcastReceiver(XmppService.this, action, null);
        }
    }

    class LoginThread extends Thread {
        private String account;
        private String password;

        public LoginThread(String account, String password) {
            this.account = account;
            this.password = password;
        }

        @Override
        public void run() {
            String action = XmppUtils.getInstance().loginXmpp(account, password);
            JacenUtils.intentLocalBroadcastReceiver(XmppService.this, action, null);
        }
    }

    class ContactThread extends Thread {

        @Override
        public void run() {
            ArrayList<ContactVo> contactVoList = XmppUtils.getInstance().getContactList();
            Bundle bundle = new Bundle();
            bundle.putSerializable("contactVoList", contactVoList);
            JacenUtils.intentLocalBroadcastReceiver(XmppService.this, XmppAction.ACTION_USER_CONTACT, bundle);
        }
    }

    class MyStanzaListener implements StanzaListener {

        @Override
        public void processPacket(Stanza packet) throws SmackException.NotConnectedException {
            Log.i("XmppService", "packet = " + packet.toString());

            if (packet instanceof Message) {//消息
                Message msg = (Message) packet;
                if (Message.Type.chat.equals(msg.getType())) {//单聊
                    if (TextUtils.isEmpty(msg.getBody()))
                        return;
                    UserInfoExtensionElement element = msg.getExtension(UserInfoExtensionElement.elementName, UserInfoExtensionElement.nameSpace);
                    if(element != null){
                        Logs.i(TAG,"element.toXML() = " + element.toXML());
                    }else{
                        Logs.i(TAG,"element.toXML() = null");
                    }
                    ChatMessageVo chatMessageVo = new ChatMessageVo();
                    chatMessageVo.parseMessage(msg);
                    chatMessageVo.setChatType(ChatType.text.getId());
                    chatMessageVo.setMe(false);
                    chatMessageVo.setShowTime(ChatMessageDataBase.getInstance().isShowTime(chatMessageVo.getChatJid(), chatMessageVo.getSendTime()));
                    chatMessageVo.setUnRead(1);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("chat", chatMessageVo);
                    String action_chatting = XmppAction.ACTION_MESSAGE + "_" + chatMessageVo.getChatJid();
                    JacenUtils.intentLocalBroadcastReceiver(XmppService.this, XmppAction.ACTION_MESSAGE, bundle);
                    JacenUtils.intentLocalBroadcastReceiver(XmppService.this, action_chatting, bundle);
                    ChatMessageDataBase.getInstance().saveChatMessage(chatMessageVo);
                } else if (Message.Type.groupchat.equals(msg.getType())) {//群聊

                }
            } else if (packet instanceof Presence) {//在线状态
                Presence presence = (Presence) packet;
            }

        }
    }

    class MyRosterListener implements RosterListener {

        //有新好友请求 自动同意
        @Override
        public void entriesAdded(Collection<String> addresses) {
            Log.i(TAG, "entriesAdded = " + addresses);
            for (String jid : addresses) {
                XmppUtils.getInstance().addUserAutoAgree(jid);
            }
        }

        //好友信息更新了 vcard
        @Override
        public void entriesUpdated(Collection<String> addresses) {
            Log.i(TAG, "entriesUpdated = " + addresses);

        }

        //删除好友
        @Override
        public void entriesDeleted(Collection<String> addresses) {
            Log.i(TAG, "entriesDeleted = " + addresses);

        }

        //好友在线状态改变
        @Override
        public void presenceChanged(Presence presence) {
            Log.i(TAG, "presenceChanged = " + presence.toString());

        }
    }

    class MyConnectionListener implements ConnectionListener {

        //链接成功
        @Override
        public void connected(XMPPConnection connection) {
            Log.i("XmppService", "connected");
        }

        //登录成功
        @Override
        public void authenticated(XMPPConnection connection, boolean resumed) {
            Log.i(TAG, "authenticated" + resumed);

        }

        //连接关系
        @Override
        public void connectionClosed() {
            Log.i(TAG, "connectionClosed");

        }

        //连接报错
        @Override
        public void connectionClosedOnError(Exception e) {
            Log.i(TAG, "connectionClosedOnError " + e.toString());

        }

        //重连成功
        @Override
        public void reconnectionSuccessful() {
            Log.i(TAG, "reconnectionSuccessful");

        }

        //重连时间
        @Override
        public void reconnectingIn(int seconds) {
            Log.i(TAG, "reconnectingIn = " + seconds);

        }

        //重连失败
        @Override
        public void reconnectionFailed(Exception e) {
            Log.i(TAG, "reconnectionFailed = " + e.toString());

        }
    }

    class MyStanzaFilter implements StanzaFilter {

        @Override
        public boolean accept(Stanza stanza) {
            if (stanza instanceof Message)//拦截Message
                return true;
            return false;
        }
    }


}
