package com.iswsc.smackdemo;

import android.app.Activity;
import android.os.Bundle;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.IOException;

public class MainUI extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_main);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                XMPPTCPConnectionConfiguration configuration = null;
//                try {
//                    configuration = XMPPTCPConnectionConfiguration.builder().setHost("xmpp.iswsc.com")
//                            .setPort(5222)
//                            .setResource("wsc")
//                            .setServiceName("xmpp")
//                            .setUsernameAndPassword("a", "a")
//                            .setDebuggerEnabled(true)
//                            .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
//                            .build();
//                    SASLAuthentication.blacklistSASLMechanism("DIGEST-MD5");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                XMPPTCPConnection connection = new XMPPTCPConnection(configuration);
//                try {
//                    connection.connect();
//                    connection.login();
//                } catch (SmackException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (XMPPException e) {
//                    e.printStackTrace();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
        XmppUtils.getInstance().loginXmpp("a","a","wsc");
    }

}
