package com.qlk.message.server.vo;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.qlk.baymax.common.log.CommonLoggerFactory;
import com.qlk.baymax.common.message.MessageContext;
import com.qlk.message.server.utils.JacksonMapper;

/**
 * 通用数据响应对象
 * 用于service向controller的请求响应
 * <P>File name : ResponseVO.java </P>
 * <P>Author : zhouyanxin </P>
 * <P>Date : 2014-10-2 </P>
 */
public class ResponseVO {

    private static final Logger LOGGER = CommonLoggerFactory.getLogger(ResponseVO.class);

    /**
     * 状态码
     * 0：表示正常
     * 其余数值表示出现错误，该值对应ExceptionCode中定义的异常码
     */
    private int code;

    /**
     * 信息内容
     */
    private String msg;

    /**
     * 数据信息
     */
    private List<?> data;

    /**
     * 
     */
    public ResponseVO() {
        this.code = 0;
        this.msg = "success";
    }

    /**
     * @param code
     * @param data
     */
    public ResponseVO(int code, List<?> data) {
        this(code, MessageContext.message(code), data);
    }

    /**
     * @param code
     * @param data
     */
    public ResponseVO(int code, Object data) {
        this(code, MessageContext.message(code), Arrays.asList(data));
    }

    /**
     * @param code
     * @param message
     * @param data
     */
    public ResponseVO(int code, String message, List<?> data) {
        super();
        this.code = code;
        this.msg = message;
        this.data = data;
    }

    public String toString() {
        String json = null;
        try {
            json = JacksonMapper.toJson(this);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * @return String 取得域 message 的方法。
     */
    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @return List<?> 取得域 data 的方法。
     */
    public List<?> getData() {
        return this.data;
    }

    /**
     * @param data 对域 data 的设置方法.
     */
    public void setData(List<?> data) {
        this.data = data;
    }

    /**
     * @return int 取得域 code 的方法。
     */
    public int getCode() {
        return this.code;
    }

    /**
     * @param code 对域 code 的设置方法.
     */
    public void setCode(int code) {
        this.code = code;
    }
}
