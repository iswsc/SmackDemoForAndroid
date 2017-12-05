package com.iswsc.smackdemo.util;

import android.text.TextUtils;

import com.iswsc.smackdemo.app.MyApp;
import com.iswsc.smackdemo.db.ChatMessageDataBase;
import com.iswsc.smackdemo.enums.ChatType;
import com.iswsc.smackdemo.enums.MessageStatus;
import com.iswsc.smackdemo.vo.ChatMessageVo;
import com.iswsc.smackdemo.vo.ContactVo;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.XMPPError;
import org.jivesoftware.smack.parsing.ExceptionLoggingCallback;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jivesoftware.smackx.vcardtemp.VCardManager;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

/**
 * @version 1.0
 * @email jacen@iswsc.com
 * Created by Jacen on 2017/7/19 23:36.
 */

public class XmppUtils {

    private static final boolean DEBUG = true;

    private static XmppUtils instance;
    private XMPPTCPConnection connection;

    private String host;
    private int port;
    private String resource;
    private String serviceName;
    private StanzaListener packetListener;
    private StanzaFilter packetFilter;
    private RosterListener rosterListener;
    private ConnectionListener connectionListener;

    private XmppUtils() {
    }

    public void init(StanzaListener packetListener, StanzaFilter packetFilter,RosterListener rosterListener,ConnectionListener connectionListener) throws Exception {
        String serverInfo = MySP.readString(MyApp.mContext, MySP.FILE_APPLICATION, MySP.KEY_SERVER);
        JSONObject jObj = new JSONObject(serverInfo);
        host = jObj.optString("host");
        port = jObj.optInt("port");
        serviceName = jObj.optString("serviceName");
        resource = jObj.optString("resource");
        this.packetListener = packetListener;
        this.packetFilter = packetFilter;
        this.rosterListener = rosterListener;
        this.connectionListener = connectionListener;
    }

    public static XmppUtils getInstance() {
        if (instance == null) {
            instance = new XmppUtils();
        }
        return instance;
    }

    private void createConnection() {
        XMPPTCPConnectionConfiguration configuration = null;
        try {
            configuration = XMPPTCPConnectionConfiguration.builder()
                    .setHost(host)
                    .setPort(port)
                    .setResource(resource)
                    .setServiceName(serviceName)
                    .setDebuggerEnabled(DEBUG)
                    .setSendPresence(false)//不发在线状态 等UI处理完以后再发
                    .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                    .build();
            SASLAuthentication.blacklistSASLMechanism("DIGEST-MD5");
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection = new XMPPTCPConnection(configuration);
        connection.setParsingExceptionCallback(new ExceptionLoggingCallback());
    }

    public XMPPTCPConnection getConnection() throws IOException, XMPPException, SmackException {
        if (connection != null) {
            if (connection.isConnected()) {
                return connection;
            } else {
                connection = null;
            }
        }

        return connection;
    }


    /**
     * 是否已经登录
     *
     * @return
     * @author by_wsc
     * @email wscnydx@gmail.com
     * @date 日期：2013-4-17 时间：下午11:03:14
     */
    public boolean isLogin() {
        if (connection == null) return false;//连接未生成
        else if (!connection.isConnected()) return false;//连接未生效
        else if (!connection.isAuthenticated()) return false;//连接未认证
        return true;
    }


    public String loginXmpp(final String userName, final String password) {
        String result = XmppAction.ACTION_LOGIN_SUCCESS;

        try {
            createConnection();
            connection.connect();
            connection.login(userName, password, resource);
            if (packetListener != null && packetFilter != null) {
                connection.addSyncStanzaListener(packetListener, packetFilter);
            }
            Roster.getInstanceFor(connection).addRosterListener(rosterListener);
            connection.addConnectionListener(connectionListener);
            //处理完所有监听器 发送在线状态 接收离线 也可在UI处理完毕后发送在线状态
            //所有的离线信息 在没有发送在线状态的时候 服务器是不会推送给客户端的
            // 除非服务器做过特殊处理 或者配置connectoin配置的时候选择了发送在线状态
            Presence presence = new Presence(Presence.Type.available);
            connection.sendStanza(presence);
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().contains("not-authorized")) {//用户名密码错误
                result = XmppAction.ACTION_LOGIN_ERROR_NOT_AUTHORIZED;

            } else if (e.getMessage().contains("java.net.UnknownHostException:") || e.getMessage().contains("Network is unreachable") || e.getMessage().contains("java.net.ConnectException: Connection refused:")) {
                //无法连接到服务器: 不可达的主机名或地址.
                result = XmppAction.ACTION_LOGIN_ERROR_UNKNOWNHOST;

            } else if (e.getMessage().contains("Hostname verification of certificate failed")) {
                result = XmppAction.ACTION_LOGIN_ERROR;

            } else if (e.getMessage().contains("unable to find valid certification path to requested target")) {
                result = XmppAction.ACTION_LOGIN_ERROR;

            } else if (e.getMessage().contains("XMPPError: conflict")) {//账号已经登录 无法重复登录
                result = XmppAction.ACTION_LOGIN_ERROR_CONFLICT;

            } else result = XmppAction.ACTION_LOGIN_ERROR_NOT_AUTHORIZED;//用户名密码错误
        }
        return result;
    }

    public ChatMessageVo sendMessage(String chatId, String content) {
        ChatMessageVo chatMessageVo = new ChatMessageVo();
        Message msg = new Message();
        try {
            msg.setType(Message.Type.chat);
            msg.setTo(chatId);
            msg.setFrom(getConnection().getUser());
            msg.setBody(content);
            chatMessageVo.parseMessage(msg);
            chatMessageVo.setChatJid(chatId);
            chatMessageVo.setChatType(ChatType.text.getId());
            chatMessageVo.setMessageStatus(MessageStatus.success.getId());
            chatMessageVo.setMe(true);
            chatMessageVo.setShowTime(ChatMessageDataBase.getInstance().isShowTime(chatId,chatMessageVo.getSendTime()));
            connection.sendStanza(msg);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return chatMessageVo;
    }

    public String registerXmpp(final String account, final String password) {
        String result = XmppAction.ACTION_REGISTER_SUCCESS;

        try {
            if(connection == null){
                createConnection();
                connection.connect();
            }
            AccountManager accountManager = AccountManager.getInstance(connection);
            accountManager.createAccount(account, password);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof XMPPException.XMPPErrorException) {
                if (((XMPPException.XMPPErrorException) e).getXMPPError() != null
                        && ((XMPPException.XMPPErrorException) e).getXMPPError().getCondition() != null) {
                    XMPPError.Condition condition = ((XMPPException.XMPPErrorException) e).getXMPPError().getCondition();
                    if (TextUtils.equals(XMPPError.Condition.conflict.toString(), condition.toString())) {
                        result = XmppAction.ACTION_REGISTER_ERROR_CONFLICT;
                    } else if (TextUtils.equals(XMPPError.Condition.forbidden.toString(), condition.toString())) {
                        result = XmppAction.ACTION_REGISTER_ERROR_FORBIDDEN;
                    } else if (TextUtils.equals(XMPPError.Condition.jid_malformed.toString(), condition.toString())) {
                        result = XmppAction.ACTION_REGISTER_ERROR_JID_MALFORMED;
                    } else {
                        result = XmppAction.ACTION_REGISTER_ERROR;
                    }
                } else {
                    result = XmppAction.ACTION_REGISTER_ERROR;
                }
            } else {
                result = XmppAction.ACTION_REGISTER_ERROR;
            }
        }
        return result;
    }

    public ArrayList<ContactVo> getContactList() {
        ArrayList<ContactVo> list = new ArrayList<ContactVo>();
        Roster roster = Roster.getInstanceFor(connection);
        Set<RosterEntry> entrys = roster.getEntries();
        ContactVo vo;
        for (RosterEntry entry : entrys) {
            vo = new ContactVo();
            vo.setJid(entry.getUser());
            vo.setName(entry.getName());
            try {
                VCard vCard = VCardManager.getInstanceFor(getConnection()).loadVCard(entry.getUser());
                vo.setFullName(vCard.getFirstName() + " " + vCard.getMiddleName() + " " + vCard.getLastName());
                vo.setNickName(vCard.getNickName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            vo.setStatus(entry.getStatus() != null ? entry.getStatus().toString() : "");
            vo.setType(entry.getType().toString());
            list.add(vo);
        }
        return list;
    }

    public void distory() {
        if (connection != null) {
            connection.disconnect();
            connection = null;
        }
        instance = null;
    }
}
