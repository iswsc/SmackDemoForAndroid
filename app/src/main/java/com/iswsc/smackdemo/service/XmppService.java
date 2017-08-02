package com.iswsc.smackdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;

import com.iswsc.smackdemo.util.JacenUtils;
import com.iswsc.smackdemo.util.MySP;
import com.iswsc.smackdemo.util.XmppAction;
import com.iswsc.smackdemo.util.XmppUtils;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.XMPPConnection;
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
            String serverInfo = MySP.readString(this, MySP.FILE_APPLICATION, MySP.KEY_SERVER);

            //String host,String port,String serverName,String resource,
            if (!TextUtils.isEmpty(serverInfo)) {
                try {
                    JSONObject jObj = new JSONObject(serverInfo);
                    host = jObj.optString("host");
                    port = jObj.optString("port");
                    serverName = jObj.optString("serverName");
                    resource = jObj.optString("resource");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            XmppUtils.getInstance().init(myStanzaListener,myStanzaFilter);

            if (XmppAction.ACTION_LOGIN.equals(action)) {
                toLoginXmpp(account, password);
            }else if (XmppAction.ACTION_REGISTER.equals(action)) {
                toRegisterXmpp(account, password);
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void toRegisterXmpp(String account, String password){

        if (!TextUtils.isEmpty(serverInfo)) {
            try {
                JSONObject jObj = new JSONObject(serverInfo);
                String host = jObj.optString("host");
                String port = jObj.optString("port");
                String serverName = jObj.optString("serverName");
                String resource = jObj.optString("resource");
                new RegisterThread(host, port, serverName, resource, account, password).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class RegisterThread extends Thread {

        private String host;
        private String port;
        private String serverName;
        private String resource;
        private String account;
        private String password;

        public RegisterThread(String host, String port, String serverName, String resource, String account, String password) {
            this.host = host;
            this.port = port;
            this.serverName = serverName;
            this.resource = resource;
            this.account = account;
            this.password = password;
        }

        @Override
        public void run() {
            try {

                AccountManager accountManager = AccountManager.getInstance(XmppUtils.getInstance().getConnection());
                accountManager.createAccount(account, password);

            } catch (Exception e) {
                e.printStackTrace();
                if (e instanceof XMPPException.XMPPErrorException) {
                    ((XMPPException.XMPPErrorException) e).getXMPPError().getCondition().toString();

                }
            }
        }
    }

    private void toLoginXmpp(String account, String password) {
                new LoginThread(host, port, serverName, resource, account, password).start();
    }

    class LoginThread extends Thread {
        private String host;
        private String port;
        private String serverName;
        private String resource;
        private String account;
        private String password;

        public LoginThread(String host, String port, String serverName, String resource, String account, String password) {
            this.host = host;
            this.port = port;
            this.serverName = serverName;
            this.resource = resource;
            this.account = account;
            this.password = password;
        }

        @Override
        public void run() {
            XmppUtils.getInstance().distory();
            XmppUtils.getInstance().init(host, Integer.parseInt(port), serverName, resource);
            XmppUtils.getInstance().loginXmpp(account, password, myStanzaListener, myStanzaFilter);
            JacenUtils.intentLocalBroadcastReceiver(XmppService.this, XmppAction.ACTION_LOGIN_SUCCESS, null);
        }
    }

    class MyStanzaListener implements StanzaListener {

        @Override
        public void processPacket(Stanza packet) throws SmackException.NotConnectedException {

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
