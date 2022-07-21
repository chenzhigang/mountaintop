package com.czg.business.log.service.impl;

import com.czg.business.log.mapper.VisitLogMapper;
import com.czg.business.log.model.VisitLog;
import com.czg.business.log.service.VisitLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;

/**
 * 访问日志接口实现类
 */
@RequiredArgsConstructor
@Slf4j
@Service(value = "visitLogService")
public class VisitLogServiceImpl implements VisitLogService {

    private final VisitLogMapper visitLogMapper;

    /**
     * 保存数据
     *
     * @param visitLog 访问日志信息
     */
    @Override
    public void save(VisitLog visitLog) {
        // 获取当前年月
        LocalDate localDate = LocalDate.now();
        String yearMonth = Integer.toString(localDate.getYear());
        int month = localDate.getMonthValue();
        if (month < 10) {
            yearMonth += "0";
        }
        yearMonth += month;
        visitLog.setYearMonth(yearMonth);
        int count = visitLogMapper.insert(visitLog);
        Assert.state(count == 1, "插入失败");
    }
}
