package com.iswsc.smackdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.iswsc.smackdemo.util.JacenUtils;
import com.iswsc.smackdemo.util.XmppAction;
import com.iswsc.smackdemo.util.XmppUtils;
import com.iswsc.smackdemo.vo.ChatMessageVo;
import com.iswsc.smackdemo.vo.ContactVo;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Stanza;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @email jacen@iswsc.com
 * Created by Jacen on 2017/7/25 2:19.
 */

public class XmppService extends Service {

    private MyStanzaListener myStanzaListener;
    private MyStanzaFilter myStanzaFilter;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myStanzaListener = new MyStanzaListener();
        myStanzaFilter = new MyStanzaFilter();
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
                    XmppUtils.getInstance().init(myStanzaListener, myStanzaFilter);
                    new LoginThread(account, password).start();
                } catch (Exception e) {
                    e.printStackTrace();
                    JacenUtils.intentLocalBroadcastReceiver(XmppService.this, XmppAction.ACTION_SERVICE_ERROR, null);
                }

            } else if (XmppAction.ACTION_REGISTER.equals(action)) {//注册
                try {
                    XmppUtils.getInstance().distory();
                    XmppUtils.getInstance().init(myStanzaListener, myStanzaFilter);
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
                if(Message.Type.chat.equals(msg.getType())){//单聊
                    ChatMessageVo chatMessageVo = new ChatMessageVo();
                    chatMessageVo.parseMessage(msg);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("chat",chatMessageVo);
                    JacenUtils.intentLocalBroadcastReceiver(XmppService.this,XmppAction.ACTION_MESSAGE,bundle);
                }else if (Message.Type.groupchat.equals(msg.getType())){//群聊

                }
            } else if (packet instanceof Presence) {//在线状态
                Presence presence = (Presence) packet;
            }

        }
    }

    class MyStanzaFilter implements StanzaFilter {

        @Override
        public boolean accept(Stanza stanza) {
            if (stanza instanceof Message)//拦截Message
                return true;
            if (stanza instanceof Presence)//Presence
                return true;
            return false;
        }
    }

}
