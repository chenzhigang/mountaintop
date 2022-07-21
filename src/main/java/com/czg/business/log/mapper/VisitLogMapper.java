package com.czg.business.log.mapper;

import com.czg.business.log.model.VisitLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 访问日志mapper接口类
 */
@Mapper
public interface VisitLogMapper {

    /**
     * 保存数据
     *
     * @param visitLog 访问日志信息
     * @return 保存是否成功：1成功，0失败
     */
    int insert(VisitLog visitLog);
}
