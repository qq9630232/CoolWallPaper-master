package com.coolwallpaper.constant;

/**
 * 全局常量
 * Created by fuchao on 2016/4/28.
 */
public class Constant {

    //友盟的AppKey
    public static final String UMENG_APPKEY = "5721d261e0f55ac016001d53";
    //Bmob的Appid
    public static final String BMOB_APPID = "227d9a75eef249ce5ea16c89a094212c";

    //登录类型:本应用(现阶段没有做注册功能)
    public static final int USER_TYPE_APP = 0;
    //登录类型:QQ登录
    public static final int USER_TYPE_QQ = 1;
    //登录类型:新浪微博登录(目前放弃新浪微博登录功能，因为它的审核太麻烦)
    public static final int USER_TYPE_SINA = 2;

    //0 表示性别男
    public static final int SEX_MAN = 0;
    //1 表示性别女
    public static final int SEX_GIRL = 1;
}
