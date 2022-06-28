package com.qlk.message.server.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qlk.baymax.common.exception.BusinessException;
import com.qlk.baymax.common.log.CommonLoggerFactory;
import com.qlk.message.server.bean.MessageRegist;
import com.qlk.message.server.dao.mongo.MessageRegistDao;
import com.qlk.message.server.exception.ExceptionCodes;
import com.qlk.message.server.service.IMessageRegistService;

@Service
public class MessageRegistServiceImpl implements IMessageRegistService {

    private static final Logger logger = CommonLoggerFactory.getLogger(MessageRegistServiceImpl.class);

    @Autowired
    private MessageRegistDao noticeRegistDao;

    /*
     * 注册
     * IMessageRegistService.registMessage()
     */
    @Override
    public void registMessage(Integer client, Integer type, String requestUri, String userType) throws BusinessException {

        MessageRegist registByCT = this.noticeRegistDao.getRegist(client, type, userType);
        MessageRegist registByUri = this.noticeRegistDao.getRegist(requestUri);
        if (null != registByCT || null != registByUri) {
            logger.error("---------- registMessage ERROR ; notice already exist : client={},type={},requestUri={},userType={}", client, type,
                         requestUri, userType);
            throw new BusinessException(ExceptionCodes.PARAM_ERROR);
        }
        this.noticeRegistDao.saveRegist(client, type, requestUri, userType);
    }

    /*
     * 注销
     * IMessageRegistService.unregistMessage()
     */
    /*@Override
    public void unregistMessage(Integer client, Integer type, String requestUri) throws BusinessException {
    
        MessageRegist registByCTU = this.noticeRegistDao.getRegist(client, type, requestUri);
        if (null == registByCTU) {
            logger.error("---------- registMessage ERROR ; notice not exist : client={},type={},requestUri={}", client, type, requestUri);
            throw new BusinessException(ExceptionCode.PARAM_ERROR);
        }
        this.noticeRegistDao.updateRegist(registByCTU);
    
    }*/

    /*
     * 删除
     * IMessageRegistService.removeMessage()
     */
    @Override
    public void removeMessage(Integer client, Integer type, String requestUri) throws BusinessException {

        MessageRegist registByCTU = this.noticeRegistDao.getRegist(client, type, requestUri);
        if (null == registByCTU) {
            logger.error("---------- registMessage ERROR ; notice not exist : client={},type={},requestUri={}", client, type, requestUri);
            throw new BusinessException(ExceptionCodes.PARAM_ERROR);
        }
        this.noticeRegistDao.removeRegist(registByCTU);
    }

    /*
     * 消息类型列表
     * IMessageRegistService.getMessageRegister()
     */
    @Override
    public List<MessageRegist> getMessageRegister() {

        List<MessageRegist> registList = this.noticeRegistDao.getRegisterList();
        return registList;
    }
}
