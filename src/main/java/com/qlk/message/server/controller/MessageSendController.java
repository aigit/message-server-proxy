package com.qlk.message.server.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qlk.baymax.common.exception.BusinessException;
import com.qlk.baymax.common.log.CommonLoggerFactory;
import com.qlk.message.server.exception.ExceptionCodes;
import com.qlk.message.server.service.IMessageSendService;
import com.qlk.message.server.utils.PublicService;

/**
 * 消息发送
 * @author Gaoll mudou@7lk.com
 * @since 2.3.0
 */
@Controller
@RequestMapping("send")
public class MessageSendController {

    /**
     * 日志对象 ：<code>logger</code>
     */
    private static final Logger logger = CommonLoggerFactory.getLogger(MessageSendController.class);

    @Autowired
    IMessageSendService messageSendService;

    /**
     * 批量消息发送
     * MessageSendController.batchSend()
     * @Author Gaoll mudou@7lk.com
     * @Date 2016年4月6日
     * @since 2.3.0
     * @param requestUri
     * @param info
     * @return
     */
    @RequestMapping("batch/{requestUri}")
    @ResponseBody
    public String batchSend(@PathVariable String requestUri, String info) {

        try {
            // 空参校验
            if (StringUtils.isEmpty(info)) {
                logger.error("---------- batchSend ERROR ; message info is empty : requestUri={},info={}", requestUri, info);
                throw new BusinessException(ExceptionCodes.MESSAGE_TARGET_OR_CONTENT_EMPTY);
            }
            this.messageSendService.sendMessage(requestUri, info);
            return PublicService.returnValue(ExceptionCodes.SUCCESS);
        } catch (BusinessException be) {
            logger.error("---------- batchSend ERROR ; service error : requestUri={},info={}", requestUri, info);
            return PublicService.returnValue(be.getCode(), be.getMessage(), null);
        } catch (Exception e) {
            logger.error("---------- batchSend ERROR ; requestUri={},info={}", requestUri, info, e);
            return PublicService.returnValue(ExceptionCodes.FAILED);
        }
    }

    /**
     * 批量发送私人主题通知
     * MessageSendController.batchSendPrivate()
     * @Author Ldl
     * @Date 2017年11月27日
     * @since 1.0.0
     * @param requestUri
     * @param info
     * @return
     */
    @RequestMapping("group/{requestUri}")
    @ResponseBody
    public String groupSendNotice(@PathVariable String requestUri, String recipientCacheKey, String info) {

        try {
            // 空参校验
            if (StringUtils.isEmpty(info)) {
                logger.error("---------- groupSendNotice ERROR ; message info is empty : requestUri={},info={}", requestUri, info);
                throw new BusinessException(ExceptionCodes.MESSAGE_TARGET_OR_CONTENT_EMPTY);
            }
            this.messageSendService.sendMessage(requestUri, recipientCacheKey, info);
            return PublicService.returnValue(ExceptionCodes.SUCCESS);
        } catch (BusinessException be) {
            logger.error("---------- groupSendNotice ERROR ; service error : requestUri={},info={}", requestUri, info);
            return PublicService.returnValue(be.getCode(), be.getMessage(), null);
        } catch (Exception e) {
            logger.error("---------- groupSendNotice ERROR ; requestUri={},info={}", requestUri, info, e);
            return PublicService.returnValue(ExceptionCodes.FAILED);
        }
    }

    /**
     * 单条消息发送
     * MessageSendController.singleSend()
     * @Author Gaoll mudou@7lk.com
     * @Date 2016年4月6日
     * @since 2.3.0
     * @param requestUri
     * @param info
     * @param userId
     * @return
     */
    @RequestMapping("single/{requestUri}")
    @ResponseBody
    public String singleSend(@PathVariable String requestUri, String info, Long userId) {

        try {
            // 空参校验
            if (StringUtils.isEmpty(info) || null == userId) {
                logger.error("---------- singleSend ERROR ; message info is empty : requestUri={},info={},userId={}", requestUri, info, userId);
                throw new BusinessException(ExceptionCodes.MESSAGE_TARGET_OR_CONTENT_EMPTY);
            }
            // UserType 待处理
            this.messageSendService.sendMessage(requestUri, userId, info);
            return PublicService.returnValue(ExceptionCodes.SUCCESS);

        } catch (BusinessException be) {
            logger.error("---------- singleSend ERROR ; service error : requestUri={},info={},userId={}", requestUri, info, userId);
            return PublicService.returnValue(be.getCode(), be.getMessage(), null);

        } catch (Exception e) {
            logger.error("---------- singleSend ERROR ; requestUri={},info={},userId={}", requestUri, info, userId, e);
            return PublicService.returnValue(ExceptionCodes.FAILED);
        }
    }

}
