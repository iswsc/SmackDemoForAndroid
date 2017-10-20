package com.iswsc.smackdemo.util;

/**
 * @version 1.0
 * @email jacen@iswsc.com
 * Created by Jacen on 2017/7/30 0:59.
 */

public class XmppAction {
    public static final String ACTION_SERVICE_ERROR = "com.iswsc.smackdemo.service.error";
    public static final String ACTION_LOGIN = "com.iswsc.smackdemo.login";
    public static final String ACTION_LOGIN_SUCCESS = "com.iswsc.smack.login.success";//登录成功
    public static final String ACTION_LOGIN_ERROR = "com.iswsc.smack.login.error";//登录失败
    public static final String ACTION_LOGIN_ERROR_NOT_AUTHORIZED = "com.iswsc.smack.login.not-authorized";//账号或密码错误
    public static final String ACTION_LOGIN_ERROR_UNKNOWNHOST = "com.iswsc.smack.login.unknownhost"; //无法连接到服务器: 不可达的主机名或地址.
    public static final String ACTION_LOGIN_ERROR_CONFLICT = "com.iswsc.smack.login.conflict";//账号已经登录 无法重复登录
    public static final String ACTION_REGISTER = "com.iswsc.smackdemo.register";
    public static final String ACTION_REGISTER_SUCCESS = "com.iswsc.smack.register.success";//注册成功
    public static final String ACTION_REGISTER_ERROR = "com.iswsc.smack.register.error";//注册失败
    public static final String ACTION_REGISTER_ERROR_FORBIDDEN = "com.iswsc.smack.register.not_authorized";//禁止注册
    public static final String ACTION_REGISTER_ERROR_JID_MALFORMED = "com.iswsc.smack.register.jid_malformed"; //账号格式不正确
    public static final String ACTION_REGISTER_ERROR_CONFLICT = "com.iswsc.smack.register.conflict";//账号已存在
    public static final String ACTION_USER_CONTACT = "com.iswsc.smackdemo.contact";


}
