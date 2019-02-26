package com.cfpamf.test.design.service.impl;

import com.cfpamf.test.design.service.IPerfTestService;
import com.cfpamf.test.design.util.BigDecimalUtil;
import com.cfpamf.test.design.vo.perf.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author flavone
 * @date 2019/02/25
 */
@Service
public class PerfTestServiceImpl implements IPerfTestService {
    /**
     * 最小平均并发用户数
     */
    private static final int MINIMUM_USER = 5;

    @Override
    public ConcurrentUserData getConcurrentUserByExperience(OnlineStatisticsData statisticsData) {
        if (null == statisticsData.getActivityRatio()
                || statisticsData.getActivityRatio().compareTo(BigDecimal.ZERO) <= 0
                || statisticsData.getActivityRatio().compareTo(BigDecimal.ONE) >= 0) {
            statisticsData.setActivityRatio(new BigDecimal(0.2));
        }
        if (!checkDataValid(statisticsData)) {
            throw new IllegalArgumentException("参数不能为空或为负");
        }
        BigDecimal avg = statisticsData.getDailyVisitUser()
                .divide(statisticsData.getActivityRatio(), BigDecimal.ROUND_HALF_UP)
                .divide(statisticsData.getStatisticsPeriod(), BigDecimal.ROUND_HALF_UP)
                .divide(new BigDecimal(3600), 2);
        return getDataByAverage(avg);
    }

    @Override
    public ConcurrentUserData getConcurrentUserByMonitor(OnlineMonitorData monitorData) {
        if (!checkDataValid(monitorData)) {
            throw new IllegalArgumentException("参数不能为空或为负");
        }
        BigDecimal avg = monitorData.getQueryCount()
                .multiply(monitorData.getAverageResponseTime())
                .divide(monitorData.getMonitorPeriod(), BigDecimal.ROUND_HALF_UP)
                .divide(new BigDecimal(3600), 2);
        return getDataByAverage(avg);
    }

    @Override
    public ConcurrentUserData getConcurrentUserWithSeckillStage(SeckillStageData stageData) {
        if (!checkDataValid(stageData)) {
            throw new IllegalArgumentException("参数不能为空或为负");
        }
        BigDecimal avg = stageData.getArrivalUser().multiply(stageData.getSubmitFrequency()).setScale(2, BigDecimal.ROUND_HALF_UP);
        return new ConcurrentUserData(avg, avg);
    }

    @Override
    public BigDecimal getQPSByMonitor(OnlineMonitorData monitorData) {
        if (!checkDataValid(monitorData)) {
            throw new IllegalArgumentException("参数不能为空或为负");
        }
        return monitorData.getQueryCount()
                .divide(monitorData.getMonitorPeriod(), 2, BigDecimal.ROUND_HALF_UP)
                .divide(new BigDecimal(3600), 2);
    }

    @Override
    public BigDecimal getSimpleQPSByCompare(OnlineMonitorDataCollection dataCollection) {
        OnlineMonitorData maxQpsData = dataCollection.getMaxData();
        OnlineMonitorData monitorData = dataCollection.getNormalData();
        if (!checkDataValid(monitorData) || !checkDataValid(maxQpsData)) {
            throw new IllegalArgumentException("参数不能为空或为负");
        }
        if (maxQpsData.getQueryCount()
                .multiply(maxQpsData.getAverageResponseTime())
                .divide(maxQpsData.getMonitorPeriod(), 2)
                .compareTo(
                        monitorData.getQueryCount()
                                .multiply(monitorData.getAverageResponseTime())
                                .divide(monitorData.getMonitorPeriod(), 2)) <= 0) {
            throw new IllegalArgumentException("一般值的[总请求数*平均响应时间/监控时间]应小于最大值");
        }
        return getQPSByMonitor(maxQpsData)
                .multiply(monitorData.getQueryCount())
                .multiply(maxQpsData.getAverageResponseTime())
                .divide(maxQpsData.getQueryCount(), BigDecimal.ROUND_HALF_UP)
                .divide(monitorData.getAverageResponseTime(), BigDecimal.ROUND_HALF_UP)
                .setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    private boolean checkDataValid(Object obj) {
        if (null == obj) {
            return false;
        }
        if (obj instanceof BigDecimal) {
            return ((BigDecimal) obj).compareTo(BigDecimal.ZERO) > 0;
        }
        if (obj instanceof OnlineStatisticsData) {
            return checkDataValid(((OnlineStatisticsData) obj).getStatisticsPeriod())
                    && checkDataValid(((OnlineStatisticsData) obj).getDailyVisitUser());
        }
        if (obj instanceof OnlineMonitorData) {
            return checkDataValid(((OnlineMonitorData) obj).getAverageResponseTime())
                    && checkDataValid(((OnlineMonitorData) obj).getMonitorPeriod())
                    && checkDataValid(((OnlineMonitorData) obj).getQueryCount());
        }
        return obj instanceof SeckillStageData
                && checkDataValid(((SeckillStageData) obj).getArrivalUser())
                && checkDataValid(((SeckillStageData) obj).getSubmitFrequency());
    }

    private ConcurrentUserData getDataByAverage(BigDecimal avg) {
        ConcurrentUserData data = new ConcurrentUserData();
        data.setAverageConcurrentUser(avg);
        if (avg.compareTo(new BigDecimal(MINIMUM_USER)) >= 0) {
            data.setMaximumConcurrentUser(avg.add(BigDecimalUtil.sqrt(avg).multiply(new BigDecimal(3))).setScale(2, BigDecimal.ROUND_HALF_UP));
        } else {
            data.setMaximumConcurrentUser(avg.multiply(new BigDecimal(3)).setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        return data;
    }

}
