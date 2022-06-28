package com.qlk.message.server.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.qlk.baymax.common.exception.BusinessException;
import com.qlk.baymax.common.log.CommonLoggerFactory;
import com.qlk.baymax.common.utils.lang.ConfigUtil;
import com.qlk.baymax.common.utils.net.HttpClientUtil;
import com.qlk.message.server.bean.MessageRegist;
import com.qlk.message.server.exception.ExceptionCodes;

/**
 * IM消息推送工具类
 * @author Gaoll mudou@7lk.com
 * @since 2.3.0
 */
public class IMnoticeUtil {

    /**
     * 日志对象：<code>logger</code>
     */
    private static Logger logger = CommonLoggerFactory.getLogger(IMnoticeUtil.class);

    /**
     * 公共IM通知发送：<code>IM_NOTICE_PUBLIC_URL</code>
     */
    private static final String IM_NOTICE_PUBLIC_URL = ConfigUtil.getString("im.notice.public");
    /**
     * 私人IM通知发送：<code>IM_NOTICE_PRIVATE_URL</code>
     */
    private static final String IM_NOTICE_PRIVATE_URL = ConfigUtil.getString("im.notice.private");

    /**
     * 批量发送私人公告
     */
    private static final String IM_NOTICE_GROUP_URL = ConfigUtil.getString("im.notice.group");

    /**
     * IM通知类消息推送(批量)
     * IMnoticeUtil.sendNotice()
     * @Author Gaoll mudou@7lk.com
     * @Date 2016年4月7日
     * @since 2.3.0
     * @param message
     * @param messageRegist
     * @return
     * @throws BusinessException
     */
    public static String sendNotice(String message, MessageRegist messageRegist) throws BusinessException {

        if (StringUtils.isEmpty(message)) {
            logger.error("notice send ERROR ; message={}", message);
            throw new BusinessException(ExceptionCodes.MESSAGE_TARGET_OR_CONTENT_EMPTY);
        }

        try {

            Map<String, Object> msgMap = new HashMap<>();
            msgMap.put("i", message);
            msgMap.put("c", messageRegist.getClient());
            msgMap.put("t", messageRegist.getType());

            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("message", JacksonMapper.toJson(msgMap));
            paramMap.put("userType", messageRegist.getUserType());

            HttpClientUtil.HttpVO resultVO = HttpClientUtil.doMapParamPost(IM_NOTICE_PUBLIC_URL, paramMap);
            if (resultVO == null || resultVO.getStatus() != 200) {
                logger.error("IM通知类消息推送(批量) ERROR ; HTTP ERROR : message={},userType={}", message, messageRegist.getUserType());
                throw new BusinessException(ExceptionCodes.HTTP_ERROR);
            } else {
                logger.info("IM通知类消息推送(批量) SUCCESS ; message={},userType={},resultData={}", message, messageRegist.getUserType(),
                            resultVO.getData());
            }
            return resultVO.getData();
        } catch (JsonProcessingException e) {
            logger.error(" sendNotice ERROR ; IM通知类消息发送异常 ", e);
            throw new BusinessException(ExceptionCodes.JSON_ERROR);
        }
    }

    /**
     * IM通知类消息推送(个人)
     * IMnoticeUtil.sendNotice()
     * @Author Gaoll mudou@7lk.com
     * @Date 2016年4月7日
     * @since 2.3.0
     * @param userId
     * @param message
     * @param messageRegist
     * @return
     * @throws BusinessException
     * @throws Exception
     */
    public static String sendNotice(Long userId, String message, MessageRegist messageRegist) throws BusinessException {

        if (null == userId || StringUtils.isEmpty(message)) {
            logger.error(" notice send ERROR ; userId={},userType={},message={}", userId, messageRegist.getUserType(), message);
            throw new BusinessException(ExceptionCodes.MESSAGE_TARGET_OR_CONTENT_EMPTY);
        }

        try {

            Map<String, Object> msgMap = new HashMap<>();
            msgMap.put("i", message);
            msgMap.put("c", messageRegist.getClient());
            msgMap.put("t", messageRegist.getType());

            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("message", JacksonMapper.toJson(msgMap));
            paramMap.put("userId", userId.toString());
            paramMap.put("userType", messageRegist.getUserType());

            HttpClientUtil.HttpVO resultVO = HttpClientUtil.doMapParamPost(IM_NOTICE_PRIVATE_URL, paramMap);
            if (resultVO == null || resultVO.getStatus() != 200) {
                logger.error("IM通知类消息推送(个人) ERROR ; HTTP ERROR : doctorId={},message={}", userId, message);
                throw new BusinessException(ExceptionCodes.HTTP_ERROR);
            } else {
                logger.info("IM通知类消息推送(个人) SUCCESS ; userId={},userType={},message={},resultData={}", userId, messageRegist.getUserType(),
                            message, resultVO.getData());
            }
            return resultVO.getData();
        } catch (JsonProcessingException e) {
            logger.error(" sendNotice ERROR ; IM通知类消息发送异常 ", e);
            throw new BusinessException(ExceptionCodes.JSON_ERROR);
        }
    }

    /**
     * 批量发送个人公告
     * IMnoticeUtil.sendNotice()
     * @Author Ldl
     * @Date 2017年11月24日
     * @since 1.0.0
     * @param doctorIdsCacheKey 发送目标列表对应的缓存key
     * @param message 公告内容
     * @param messageRegist
     * @return
     * @throws BusinessException
     */
    public static String groupSendAnnouncement(String recipientCacheKey, String message, MessageRegist messageRegist) throws BusinessException {

        if (null == recipientCacheKey || StringUtils.isEmpty(message)) {
            logger.error(" groupSendAnnouncement  ERROR ; doctorIdsCacheKey={},userType={},message={}", recipientCacheKey,
                         messageRegist.getUserType(),
                         message);
            throw new BusinessException(ExceptionCodes.MESSAGE_TARGET_OR_CONTENT_EMPTY);
        }

        try {
            Map<String, Object> msgMap = new HashMap<>();
            msgMap.put("i", message);
            msgMap.put("c", messageRegist.getClient());
            msgMap.put("t", messageRegist.getType());

            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("message", JacksonMapper.toJson(msgMap));
            paramMap.put("recipientCacheKey", recipientCacheKey);
            paramMap.put("userType", messageRegist.getUserType());

            HttpClientUtil.HttpVO resultVO = HttpClientUtil.doMapParamPost(IM_NOTICE_GROUP_URL, paramMap);
            if (resultVO == null || resultVO.getStatus() != 200) {
                logger.error("按标签发送个人公告 ERROR ; HTTP ERROR : doctorId={},message={}", recipientCacheKey, message);
                throw new BusinessException(ExceptionCodes.HTTP_ERROR);
            } else {
                logger.info("按标签发送个人公告 SUCCESS ; userId={},userType={},message={},resultData={}", recipientCacheKey,
                            messageRegist.getUserType(),
                            message, resultVO.getData());
            }
            return resultVO.getData();
        } catch (JsonProcessingException e) {
            logger.error(" groupSendAnnouncement ERROR ; 按标签发送个人公告异常 ", e);
            throw new BusinessException(ExceptionCodes.JSON_ERROR);
        }
    }
}
