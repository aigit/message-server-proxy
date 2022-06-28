package com.qlk.message.server.utils;

/**
 * 自校验接口
 * @author Gaoll mudou@7lk.com
 * @since 2.3.0
 */
public interface Validatioin {

    /**
     * 校验
     * Validatioin.validation()
     * @Author Gaoll mudou@7lk.com
     * @Date 2016年4月8日
     * @since 1.0.0
     * @param validateParam
     * @return
     */
    boolean validation(Object... validateParam);
}
