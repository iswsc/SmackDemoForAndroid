package com.iswsc.smackdemo.util;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.parsing.ExceptionLoggingCallback;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import java.io.IOException;

/**
 * @version 1.0
 * @email jacen@iswsc.com
 * Created by Jacen on 2017/7/19 23:36.
 */

public class XmppUtils {

    private static XmppUtils instance;
    private XMPPTCPConnection connection;

    private String host;
    private int port;
    private String resource;
    private String serviceName;

    public static final String ACTION_LOGIN = "com.iswsc.smackdemo.login";

    private XmppUtils() {
    }

    public void init(String host, int port, String serviceName, String resource) {
        this.host = host;
        this.port = port;
        this.serviceName = serviceName;
        this.resource = resource;
    }

    public static XmppUtils getInstance() {
        if (instance == null) {
            instance = new XmppUtils();
        }
        return instance;
    }

    private XMPPTCPConnection createConnection() {
        XMPPTCPConnectionConfiguration configuration = null;
        try {
            configuration = XMPPTCPConnectionConfiguration.builder()
                    .setHost(host)
                    .setPort(port)
                    .setResource(resource)
                    .setServiceName(serviceName)
//                    .setUsernameAndPassword("a", "a")
                    .setDebuggerEnabled(true)
                    .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                    .build();
            SASLAuthentication.blacklistSASLMechanism("DIGEST-MD5");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new XMPPTCPConnection(configuration);
    }

    public void loginXmpp(final String userName, final String password, final StanzaListener packetListener, final StanzaFilter packetFilter) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                connection = createConnection();
                connection.setParsingExceptionCallback(new ExceptionLoggingCallback());
                connection.addPacketSendingListener(packetListener, packetFilter);
                try {
                    connection.connect();
                    connection.login(userName, password, resource);
                } catch (SmackException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XMPPException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void distory() {
        if (connection != null) {
            connection.disconnect();
            connection = null;
        }
        instance = null;
    }
}
