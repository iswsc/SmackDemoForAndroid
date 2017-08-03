package com.iswsc.smackdemo.service;

import android.app.Service;
import android.content.Intent;
import android.gesture.GestureUtils;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.iswsc.smackdemo.util.JacenUtils;
import com.iswsc.smackdemo.util.MySP;
import com.iswsc.smackdemo.util.XmppAction;
import com.iswsc.smackdemo.util.XmppUtils;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.json.JSONObject;

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
            try {
                XmppUtils.getInstance().init(myStanzaListener, myStanzaFilter);
            } catch (Exception e) {
                e.printStackTrace();
                JacenUtils.intentLocalBroadcastReceiver(XmppService.this, XmppAction.ACTION_SERVICE_ERROR, null);
                return super.onStartCommand(intent, flags, startId);
            }

            if (XmppAction.ACTION_LOGIN.equals(action)) {
                new LoginThread(account, password).start();
            } else if (XmppAction.ACTION_REGISTER.equals(action)) {
                new RegisterThread(account, password).start();
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
            try {
                AccountManager accountManager = AccountManager.getInstance(XmppUtils.getInstance().getConnection());
                accountManager.createAccount(account, password);
                JacenUtils.intentLocalBroadcastReceiver(XmppService.this, XmppAction.ACTION_REGISTER_SUCCESS, null);
            } catch (Exception e) {
                e.printStackTrace();
                if (e instanceof XMPPException.XMPPErrorException) {
                    Log.e("XmppService", ((XMPPException.XMPPErrorException) e).getXMPPError().getCondition().toString());
                    //TODO 错误日志待处理
                }
                JacenUtils.intentLocalBroadcastReceiver(XmppService.this, XmppAction.ACTION_REGISTER_ERROR, null);
            }
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

    class MyStanzaListener implements StanzaListener {

        @Override
        public void processPacket(Stanza packet) throws SmackException.NotConnectedException {

            if (packet instanceof Message) {//消息
                Message msg = (Message) packet;
                Log.i("XmppService", "Message = " + msg.toString());
            } else if (packet instanceof Presence) {//在线状态
                Presence presence = (Presence) packet;
                Log.i("XmppService", "Presence = " + presence.toString());
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
