package com.qlk.message.server.bean;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.qlk.message.server.utils.Validatioin;

/**
 * 消息注册Bean
 * @author Gaoll mudou@7lk.com
 * @since 2.3.0
 */
public class MessageRegist implements Serializable, Validatioin {

    /* 消息类型是否可用状态 */
    /**
     * 消息注册状态-可用：<code>NOTICE_REGIST_VALID</code>
     */
    public static final int NOTICE_REGIST_VALID = 1;
    /**
     * 消息注册状态-不可用：<code>NOTICE_REGIST_NOT_VALID</code>
     */
    public static final int NOTICE_REGIST_NOT_VALID = 0;

    /**
     * 用户类型-医生：<code>USER_TYPE_DOCTOR</code>
     */
    public static final String USER_TYPE_DOCTOR = "d";
    /**
     * 用户类型-患者：<code>USER_TYPE_PATIENT</code>
     */
    public static final String USER_TYPE_PATIENT = "p";

    /**
     * 序列号：<code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 5080972782921812253L;

    /**
     * <主键>
     */
    @Id
    @JsonInclude(Include.NON_NULL)
    private String id;
    /**
     * 消息接收方：<code>client</code>
     */
    private Integer client;
    /**
     * 消息类型：<code>type</code>
     */
    private Integer type;
    /**
     * ：<code>isValid</code>
     */
    private Integer isValid;
    /**
     * 消息发送请求连接：<code>requestUri</code>
     */
    private String requestUri;

    /**
     * 用户类型：<code>userType</code>
     */
    private String userType;

    /**
     * @return String 取得域 id 的方法。
     */
    public String getId() {
        return id;
    }

    /**
     * @param id 对域 id 的设置方法.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return Integer 取得域 client 的方法。
     */
    public Integer getClient() {
        return client;
    }

    /**
     * @param client 对域 client 的设置方法.
     */
    public void setClient(Integer client) {
        this.client = client;
    }

    /**
     * @return Integer 取得域 type 的方法。
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type 对域 type 的设置方法.
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @return Integer 取得域 isValid 的方法。
     */
    public Integer getIsValid() {
        return isValid;
    }

    /**
     * @param isValid 对域 isValid 的设置方法.
     */
    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    /**
     * @return String 取得域 requestUri 的方法。
     */
    public String getRequestUri() {
        return requestUri;
    }

    /**
     * @param requestUri 对域 requestUri 的设置方法.
     */
    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    /**
     * @return String 取得域 userType 的方法。
     */
    public String getUserType() {
        return this.userType;
    }

    /**
     * @param userType 对域 userType 的设置方法.
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * 消息注册类型校验
     * @Author Gaoll mudou@7lk.com
     * @Date 2016年4月8日
     * @since 2.3.0
     * @param validateParam
     * @return
     * @see com.qlk.message.server.utils.Validatioin#validation(java.lang.Object[])
     */
    @Override
    public boolean validation(Object... validateParam) {

        if (null != this.isValid && NOTICE_REGIST_VALID == this.isValid) {
            return true;
        } else {
            return false;
        }
    }
}
