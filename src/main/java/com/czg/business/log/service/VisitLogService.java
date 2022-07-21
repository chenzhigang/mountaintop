package com.czg.business.log.service;

import com.czg.business.log.model.VisitLog;

/**
 * 访问日志接口类
 */
public interface VisitLogService {

    /**
     * 保存数据
     *
     * @param visitLog 访问日志信息
     */
    void save(VisitLog visitLog);
}
