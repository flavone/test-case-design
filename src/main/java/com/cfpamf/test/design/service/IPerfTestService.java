package com.cfpamf.test.design.service;

import com.cfpamf.test.design.vo.perf.*;

import java.math.BigDecimal;

/**
 * @author flavone
 * @date 2019/02/25
 */
public interface IPerfTestService {
    /**
     * 根据经验获取并发用户数
     * @param statisticsData
     * @return
     */
    ConcurrentUserData getConcurrentUserByExperience(OnlineStatisticsData statisticsData);

    /**
     * 根据监控数据获取并发用户数
     * @param monitorData
     * @return
     */
    ConcurrentUserData getConcurrentUserByMonitor(OnlineMonitorData monitorData);

    /**
     * 根据秒杀场景计算并发用户数
     * @param stageData
     * @return
     */
    ConcurrentUserData getConcurrentUserWithSeckillStage(SeckillStageData stageData);

    /**
     * 根据监控数据获取QPS
     * @param monitorData
     * @return
     */
    BigDecimal getQPSByMonitor(OnlineMonitorData monitorData);

    /**
     *  根据监控数据获取低频QPS
     *
     * @param dataCollection
     * @return
     */
    BigDecimal getSimpleQPSByCompare(OnlineMonitorDataCollection dataCollection);
}
