package com.iswsc.smackdemo.util;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ReconnectionManager;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.packet.XMPPError;
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
                    .setDebuggerEnabled(true)
                    .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                    .build();
            SASLAuthentication.blacklistSASLMechanism("DIGEST-MD5");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new XMPPTCPConnection(configuration);
    }

    public int loginXmpp(final String userName, final String password, final StanzaListener packetListener, final StanzaFilter packetFilter) {
        int result = 0;
        connection = createConnection();
        connection.setParsingExceptionCallback(new ExceptionLoggingCallback());
        connection.addPacketSendingListener(packetListener, packetFilter);

        try {
            connection.connect();
            connection.login(userName, password, resource);
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().contains("not-authorized")) {//用户名密码错误
                errorMessage = Res.getString("message.invalid.username.password");

            } else if (e.getMessage().contains("java.net.UnknownHostException:") || e.getMessage().contains("Network is unreachable") || e.getMessage().contains("java.net.ConnectException: Connection refused:")) {
                //无法连接到服务器: 不可达的主机名或地址.
                errorMessage = Res.getString("message.server.unavailable");

            } else if (e.getMessage().contains("Hostname verification of certificate failed")) {
                errorMessage = Res.getString("message.cert.hostname.verification.failed");
                ReconnectionManager.getInstanceFor(connection).disableAutomaticReconnection();

            } else if (e.getMessage().contains("unable to find valid certification path to requested target")) {
                errorMessage = Res.getString("message.cert.verification.failed");
                ReconnectionManager.getInstanceFor(connection).disableAutomaticReconnection();

            } else if (e.getMessage().contains("XMPPError: conflict")) {//账号已经登录 无法重复登录
                errorMessage = Res.getString("label.conflict.error");

            } else errorMessage = Res.getString("message.unrecoverable.error");//用户名密码错误
        }
        return result;
    }

    public void distory() {
        if (connection != null) {
            connection.disconnect();
            connection = null;
        }
        instance = null;
    }
}
