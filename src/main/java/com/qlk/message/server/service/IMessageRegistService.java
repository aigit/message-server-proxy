package com.qlk.message.server.service;

import java.util.List;

import com.qlk.baymax.common.exception.BusinessException;
import com.qlk.message.server.bean.MessageRegist;

/**
 * 消息注册Service
 * @author Gaoll mudou@7lk.com
 * @since 2.3.0
 */
public interface IMessageRegistService {

    /**
     * 注册
     * IMessageRegistService.registMessage()
     * @Author Gaoll mudou@7lk.com
     * @Date 2016年3月30日
     * @since 2.3.0
     * @param client(C)
     * @param type(T)
     * @param requestUri
     * @param userType
     * @throws BusinessException
     */
    public void registMessage(Integer client, Integer type, String requestUri, String userType) throws BusinessException;

    /**
     * 注销
     * IMessageRegistService.unregistMessage()
     * @Author Gaoll mudou@7lk.com
     * @Date 2016年4月6日
     * @since 2.3.0
     * @param client
     * @param type
     * @param requestUri
     * @throws BusinessException
     *//*
      public void unregistMessage(Integer client, Integer type, String requestUri) throws BusinessException;*/

    /**
     * 删除
     * IMessageRegistService.removeMessage()
     * @Author Gaoll mudou@7lk.com
     * @Date 2016年4月6日
     * @since 2.3.0
     * @param client
     * @param type
     * @param requestUri
     * @throws BusinessException
     */
    public void removeMessage(Integer client, Integer type, String requestUri) throws BusinessException;

    /**
     * 消息类型列表
     * IMessageRegistService.getMessageRegister()
     * @Author Gaoll mudou@7lk.com
     * @Date 2016年4月8日
     * @since 2.3.0
     * @return
     */
    public List<MessageRegist> getMessageRegister();

}
