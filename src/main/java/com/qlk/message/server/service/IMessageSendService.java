package com.qlk.message.server.service;

import com.qlk.baymax.common.exception.BusinessException;

/**
 * 消息发送Service
 * @author Gaoll mudou@7lk.com
 * @since 2.3.0
 */
public interface IMessageSendService {

    /**
     * 消息发送(批量)
     * IMessageSendService.sendMessage()
     * @Author Gaoll mudou@7lk.com
     * @Date 2016年4月6日
     * @since 2.3.0
     * @param requestUri
     * @param info
     * @throws BusinessException
     */
    void sendMessage(String requestUri, String info) throws BusinessException;

    /**
     * 消息发送(单人)
     * IMessageSendService.sendMessage()
     * @Author Gaoll mudou@7lk.com
     * @Date 2016年4月6日
     * @since 2.3.0
     * @param requestUri
     * @param info
     * @throws BusinessException
     */
    void sendMessage(String requestUri, Long userId, String info) throws BusinessException;

    /**
     * 批量发送单人消息
     * IMessageSendService.sendMessage()
     * @Author Ldl
     * @Date 2017年11月24日
     * @since 1.0.0
     * @param requestUri
     * @param doctorIdsCacheKey 
     * @param info
     * @throws BusinessException
     */
    void sendMessage(String requestUri, String recipientCacheKey, String info) throws BusinessException;

}
