package com.qlk.message.server.service.impl;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qlk.baymax.common.exception.BusinessException;
import com.qlk.baymax.common.log.CommonLoggerFactory;
import com.qlk.message.server.bean.MessageRegist;
import com.qlk.message.server.dao.mongo.MessageRegistDao;
import com.qlk.message.server.exception.ExceptionCodes;
import com.qlk.message.server.service.IMessageSendService;
import com.qlk.message.server.utils.IMnoticeUtil;
import com.qlk.message.server.vo.MessageVO;

@Service
public class MessageSendServiceImpl implements IMessageSendService {

    /**
     * 日志对象：<code>logger</code>
     */
    private static final Logger logger = CommonLoggerFactory.getLogger(MessageSendServiceImpl.class);

    @Autowired
    private MessageRegistDao messageRegistDao;

    /*
     * 消息发送(批量)
     * IMessageSendService.sendMessage()
     */
    @Override
    public void sendMessage(String requestUri, String info) throws BusinessException {

        // 校验消息类型有效性
        MessageRegist messageRegist = this.messageRegistDao.getRegist(requestUri);
        if (null == messageRegist || !messageRegist.validation()) {
            logger.error("---------- sendMessage ; illegal messageRegist or messageRegist not exist : requestUri={},info={}",
                         requestUri, info);
            throw new BusinessException(ExceptionCodes.MESSAGE_NOT_REGISTERED);
        }

        // 组装消息类型调用IM格式
        MessageVO messageVO = new MessageVO(messageRegist, info);

        // 执行发送
        IMnoticeUtil.sendNotice(messageVO.getInfomation(), messageRegist);

    }

    /*
     * 消息发送(单人)
     * IMessageSendService.sendMessage()
     */
    @Override
    public void sendMessage(String requestUri, Long userId, String info) throws BusinessException {

        // 校验消息类型有效性
        MessageRegist messageRegist = this.messageRegistDao.getRegist(requestUri);
        if (null == messageRegist || !messageRegist.validation()) {
            logger.error("---------- sendMessage ; illegal messageRegist or messageRegist not exist : requestUri={},info={}",
                         requestUri, info);
            throw new BusinessException(ExceptionCodes.MESSAGE_NOT_REGISTERED);
        }

        // 组装消息类型调用IM格式
        MessageVO messageVO = new MessageVO(messageRegist, info);

        // 执行发送
        IMnoticeUtil.sendNotice(userId, messageVO.getInfomation(), messageRegist);
    }

    /**
     * @Author Ldl
     * @Date 2017年11月24日
     * @since 1.0.0
     * @param requestUri
     * @param doctorIdsCacheKey
     * @param info
     * @throws BusinessException
     * @see com.qlk.message.server.service.IMessageSendService#sendMessage(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void sendMessage(String requestUri, String recipientCacheKey, String info) throws BusinessException {
        // 校验消息类型有效性
        MessageRegist messageRegist = this.messageRegistDao.getRegist(requestUri);
        if (null == messageRegist || !messageRegist.validation()) {
            logger.error("---------- sendMessage ; illegal messageRegist or messageRegist not exist : requestUri={},info={}", requestUri, info);
            throw new BusinessException(ExceptionCodes.MESSAGE_NOT_REGISTERED);
        }

        // 组装消息类型调用IM格式
        MessageVO messageVO = new MessageVO(messageRegist, info);

        // 执行发送
        IMnoticeUtil.groupSendAnnouncement(recipientCacheKey, messageVO.getInfomation(), messageRegist);

    }

}
