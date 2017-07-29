package com.iswsc.smackdemo.util;

/**
 * @version 1.0
 * @email jacen@iswsc.com
 * Created by Jacen on 2017/7/30 0:59.
 */

public class XmppAction {
    public static String XMPP_LOGIN_SUCCESS = "com.iswsc.smack.login.success";//登录成功
    public static String XMPP_LOGIN_ERROR = "com.iswsc.smack.login.error";//登录失败
    public static String XMPP_LOGIN_ERROR_NOT_AUTHORIZED = "com.iswsc.smack.login.not-authorized";//密码错误
    public static String XMPP_LOGIN_ERROR_UNKNOWNHOST = "com.iswsc.smack.login.unknownhost"; //无法连接到服务器: 不可达的主机名或地址.
    public static String XMPP_LOGIN_ERROR_CONFLICT = "com.iswsc.smack.login.conflict";//账号已经登录 无法重复登录

}
