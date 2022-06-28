package com.qlk.message.server.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.qlk.baymax.common.exception.BusinessException;
import com.qlk.message.server.bean.MessageRegist;
import com.qlk.message.server.exception.ExceptionCodes;
import com.qlk.message.server.utils.JacksonMapper;

/**
 * 消息Bean
 * @author Gaoll mudou@7lk.com
 * @since 2.3.0
 */
public class MessageVO implements Serializable {

    /**
     * 序列化ID：<code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 2679059465175024172L;

    private Integer client;
    private Integer type;
    private String infomation;

    /**
     * 构造函数
     * @param messageRegist
     * @param info
     * @throws BusinessException
     */
    public MessageVO(MessageRegist messageRegist, String info) throws BusinessException {

        if (null == messageRegist) {
            throw new BusinessException(ExceptionCodes.PARAM_ERROR);
        }
        this.client = messageRegist.getClient();
        this.type = messageRegist.getType();
        this.infomation = info;
    }

    /**
     * @return Integer 取得域 client 的方法。
     */
    public Integer getClient() {
        return this.client;
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
        return this.type;
    }

    /**
     * @param type 对域 type 的设置方法.
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @return String 取得域 infomation 的方法。
     */
    public String getInfomation() {
        return this.infomation;
    }

    /**
     * @param infomation 对域 infomation 的设置方法.
     */
    public void setInfomation(String infomation) {
        this.infomation = infomation;
    }

    public String buildMsgJson() throws JsonProcessingException {

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("c", this.client);
        paramMap.put("t", this.type);
        paramMap.put("i", this.infomation);

        // System.out.println(JacksonMapper.toJson(paramMap));

        return JacksonMapper.toJson(paramMap);
    }

}
