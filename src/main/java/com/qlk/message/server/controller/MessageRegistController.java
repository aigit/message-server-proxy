package com.qlk.message.server.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qlk.baymax.common.exception.BusinessException;
import com.qlk.baymax.common.log.CommonLoggerFactory;
import com.qlk.message.server.bean.MessageRegist;
import com.qlk.message.server.exception.ExceptionCodes;
import com.qlk.message.server.service.IMessageRegistService;
import com.qlk.message.server.utils.PublicService;

@Controller
@RequestMapping("register")
public class MessageRegistController {

    /**
     * 日志对象 ：<code>logger</code>
     */
    private static final Logger logger = CommonLoggerFactory.getLogger(MessageRegistController.class);

    @Autowired
    private IMessageRegistService messageRegistService;

    /**
     * 注册
     * MessageRegistController.register()
     * @Author Gaoll mudou@7lk.com
     * @Date 2016年3月30日
     * @since 2.3.0
     * @param client
     * @param type
     * @param requestUri
     * @return
     */
    @RequestMapping("register")
    @ResponseBody
    public String register(Integer client, Integer type, String requestUri, String userType) {

        try {
            // 空参校验
            if (null == client || null == type || null == requestUri) {
                logger.error("---------- messageRegister register ERROR ; illegal param : client={},type={},formats={},requestUri={},userType={}",
                             client, type, requestUri, userType);
            }
            this.messageRegistService.registMessage(client, type, requestUri, userType);
            return PublicService.returnValue(ExceptionCodes.SUCCESS);
        } catch (BusinessException be) {
            logger.error("---------- messageRegister register ERROR ; service error : client={},type={},requestUri={}", client, type, requestUri);
            return PublicService.returnValue(be.getCode(), be.getMessage(), null);
        } catch (Exception e) {
            logger.error("---------- messageRegister register ERROR ; illegal param : client={},type={},requestUri={}", client, type, requestUri, e);
            return PublicService.returnValue(ExceptionCodes.FAILED);
        }
    }

    /**
     * 删除
     * MessageRegistController.removeRegister()
     * @Author Gaoll mudou@7lk.com
     * @Date 2016年4月6日
     * @since 2.3.0
     * @param client
     * @param type
     * @param requestUri
     * @return
     */
    @RequestMapping("remove")
    @ResponseBody
    public String removeRegister(Integer client, Integer type, String requestUri) {

        try {
            // 空参校验
            if (null == client || null == type || StringUtils.isEmpty(requestUri)) {
                logger.error("---------- messageRegister removeRegister ERROR ; illegal param : client={},type={},requestUri={}", client, type,
                             requestUri);
            }

            this.messageRegistService.removeMessage(client, type, requestUri);
            return PublicService.returnValue(ExceptionCodes.SUCCESS);
        } catch (BusinessException be) {
            logger.error("---------- messageRegister removeRegister ERROR ; service error : client={},type={},requestUri={}", client, type,
                         requestUri);
            return PublicService.returnValue(be.getCode(), be.getMessage(), null);

        } catch (Exception e) {
            logger.error("---------- messageRegister removeRegister ERROR ; client={},type={},requestUri={}", client, type, requestUri, e);
            return PublicService.returnValue(ExceptionCodes.FAILED);
        }
    }

    /**
     * 消息类型列表
     * MessageRegistController.registerList()
     * @Author Gaoll mudou@7lk.com
     * @Date 2016年4月8日
     * @since 2.3.0
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public String registerList() {

        try {

            List<MessageRegist> registerList = this.messageRegistService.getMessageRegister();
            return PublicService.returnValue(ExceptionCodes.SUCCESS, registerList);
        } catch (Exception e) {
            logger.error("---------- messageRegister registerList ERROR ;", e);
            return PublicService.returnValue(ExceptionCodes.FAILED);
        }
    }
}
