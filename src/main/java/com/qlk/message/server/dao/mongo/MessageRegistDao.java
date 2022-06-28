package com.qlk.message.server.dao.mongo;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.qlk.baymax.common.redis.RedisUtil;
import com.qlk.message.server.bean.MessageRegist;
import com.qlk.message.server.utils.PropertyValueConstants;

/**
 * 通知消息注册mongoDao
 * @author Gaoll mudou@7lk.com
 * @since 2.3.0
 */
@Repository
public class MessageRegistDao extends MongoBaseDao {

    /**
     * 消息记录的collectionName
     */
    private static final String COLLECTION_NAME = "m_message_regist";

    /**
     * 给定C、T获取
     * MessageRegistDao.getRegist()
     * @Author Gaoll mudou@7lk.com
     * @Date 2016年4月6日
     * @since 2.3.0
     * @param client
     * @param type
     * @return
     */
    public MessageRegist getRegist(Integer client, Integer type) {

        Criteria criteria = Criteria.where("client").is(client).and("type").is(type);
        Query query = Query.query(criteria);
        List<MessageRegist> results = super.find(query, MessageRegist.class, COLLECTION_NAME);
        return results.size() > 0 ? results.get(0) : null;
    }

    /**
     * 给定RequestUri获取
     * MessageRegistDao.getRegist()
     * @Author Gaoll mudou@7lk.com
     * @Date 2016年4月6日
     * @since 2.3.0
     * @param requestUri
     * @return
     */
    public MessageRegist getRegist(String requestUri) {

        MessageRegist messageRegist = null;
        // 优先缓存(编辑修改时维护缓存数据)
        if (RedisUtil.keyOps().existsKey(PropertyValueConstants.MESSAGE_REGISTER_PRE + requestUri)) {
            messageRegist = (MessageRegist) RedisUtil.valueOps().getObject(PropertyValueConstants.MESSAGE_REGISTER_PRE + requestUri);
        } else {
            Criteria criteria = Criteria.where("requestUri").is(requestUri);
            Query query = Query.query(criteria);
            List<MessageRegist> results = super.find(query, MessageRegist.class, COLLECTION_NAME);
            messageRegist = results.size() > 0 ? results.get(0) : null;
            if (null != messageRegist) {
                RedisUtil.valueOps().set(PropertyValueConstants.MESSAGE_REGISTER_PRE + requestUri, messageRegist);
            }
        }
        return messageRegist;
    }

    /**
     * 给定C、T、RequestUri获取
     * MessageRegistDao.getRegist()
     * @Author Gaoll mudou@7lk.com
     * @Date 2016年4月6日
     * @since 2.3.0
     * @param client
     * @param type
     * @param requestUri
     * @return
     */
    public MessageRegist getRegist(Integer client, Integer type, String requestUri) {

        Criteria criteria = Criteria.where("client").is(client).and("type").is(type).and("requestUri").is(requestUri);
        Query query = Query.query(criteria);
        List<MessageRegist> results = super.find(query, MessageRegist.class, COLLECTION_NAME);
        return results.size() > 0 ? results.get(0) : null;
    }

    /**
     * 保存
     * MessageRegistDao.saveRegist()
     * @Author Gaoll mudou@7lk.com
     * @Date 2016年4月6日
     * @since 2.3.0
     * @param client
     * @param type
     * @param requestUri
     */
    public void saveRegist(Integer client, Integer type, String requestUri, String userType) {

        MessageRegist messageRegist = new MessageRegist();
        messageRegist.setClient(client);
        messageRegist.setType(type);
        messageRegist.setRequestUri(requestUri);
        messageRegist.setUserType(userType);
        messageRegist.setIsValid(MessageRegist.NOTICE_REGIST_VALID);// 默认有效
        super.save(messageRegist, COLLECTION_NAME);
        RedisUtil.valueOps().set(PropertyValueConstants.MESSAGE_REGISTER_PRE + requestUri, messageRegist);
    }

    /**
     * 更新
     * MessageRegistDao.updateRegist()
     * @Author Gaoll mudou@7lk.com
     * @Date 2016年4月6日
     * @since 2.3.0
     * @param messageRegist
     */
    public void updateRegist(MessageRegist messageRegist) {

        super.remove(messageRegist, COLLECTION_NAME);
        messageRegist.setIsValid(MessageRegist.NOTICE_REGIST_NOT_VALID);
        super.save(messageRegist, COLLECTION_NAME);
        String registerKey = PropertyValueConstants.MESSAGE_REGISTER_PRE + messageRegist.getRequestUri();
        RedisUtil.keyOps().delete(registerKey);
        RedisUtil.valueOps().set(registerKey, messageRegist);
    }

    /**
     * 删除
     * MessageRegistDao.removeRegist()
     * @Author Gaoll mudou@7lk.com
     * @Date 2016年4月6日
     * @since 2.3.0
     * @param messageRegist
     */
    public void removeRegist(MessageRegist messageRegist) {

        super.remove(messageRegist, COLLECTION_NAME);
        RedisUtil.keyOps().delete(PropertyValueConstants.MESSAGE_REGISTER_PRE + messageRegist.getRequestUri());

    }

    /**
     * 获取消息类型列表
     * MessageRegistDao.getRegisterList()
     * @Author Gaoll mudou@7lk.com
     * @Date 2016年4月8日
     * @since 2.3.0
     * @return
     */
    public List<MessageRegist> getRegisterList() {

        List<MessageRegist> registerList = super.findAll(MessageRegist.class, COLLECTION_NAME);
        return registerList;
    }
}
