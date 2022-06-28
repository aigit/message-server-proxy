package com.qlk.message.server.utils;

/**
 * @Description 属性常量文件(公共属性)
 * @author guoyongxiang E-mail: xianshu@qlk.com
 * @version 2015-6-23 下午10:43:49 by guoyongxiang
 */
public abstract class PropertyValueConstants {

    /**
     * 用户类型区分:医生端d
     */
    public static final String USER_TYPE_DOCTOR = "d";
    /**
     * 用户类型区分:患者端p
     */
    public static final String USER_TYPE_PATIENT = "p";
    /**
     * task push 通知前缀
     */
    public static final String NOTICE_PUSH_URL_PRE = "task.push.";
    /**
     * redis key 消息类型注册详情redis前缀
     */
    public static final String MESSAGE_REGISTER_PRE = "baymax-msp|register_";

}