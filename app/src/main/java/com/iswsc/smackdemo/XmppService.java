package com.iswsc.smackdemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;

import com.iswsc.smackdemo.util.MySP;
import com.iswsc.smackdemo.util.XmppUtils;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Stanza;
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
            if (XmppUtils.ACTION_LOGIN.equals(action)) {
                String account = intent.getStringExtra("account");
                String password = intent.getStringExtra("password");
                toLoginXmpp(account,password);
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void toLoginXmpp(String account,String password) {
        String serverInfo = MySP.readString(this, MySP.FILE_APPLICATION, MySP.KEY_SERVER);
        if (!TextUtils.isEmpty(serverInfo)) {
            try {
                JSONObject jObj = new JSONObject(serverInfo);
                String host          = jObj.optString("host");
                String port          = jObj.optString("port");
                String serverName    = jObj.optString("serverName");
                String resource      = jObj.optString("resource");
                XmppUtils.getInstance().distory();
                XmppUtils.getInstance().init(host,Integer.parseInt(port),serverName,resource);
                XmppUtils.getInstance().loginXmpp(account,password,myStanzaListener,myStanzaFilter);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
            if(stanza instanceof Message)//拦截Message
                return true;
            if(stanza instanceof Presence)//Presence
                return true;
            return false;
        }
    }

}
