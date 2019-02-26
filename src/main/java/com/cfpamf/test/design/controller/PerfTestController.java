package com.cfpamf.test.design.controller;

import com.cfpamf.test.design.service.IPerfTestService;
import com.cfpamf.test.design.vo.perf.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @author flavone
 * @date 2019/02/25
 */
@Api(value = "性能测试", tags = {"性能测试参数API"})
@RestController
@RequestMapping(value = "/perf")
public class PerfTestController {

    @Autowired
    private IPerfTestService perfTestService;

    @ApiOperation(value = "通过统计经验估算并发用户", notes = "")
    @PostMapping(value = "/getExperimentalConcurrentUser")
    public ConcurrentUserData getExperimentalConcurrentUser(@RequestBody OnlineStatisticsData data) {
        return perfTestService.getConcurrentUserByExperience(data);
    }

    @ApiOperation(value = "通过监控统计估算并发用户", notes = "")
    @PostMapping(value = "/getCalculatingConcurrentUser")
    public ConcurrentUserData getCalculatingConcurrentUser(@RequestBody OnlineMonitorData data) {
        return perfTestService.getConcurrentUserByMonitor(data);
    }

    @ApiOperation(value = "通过秒杀场景估算并发用户", notes = "")
    @PostMapping(value = "/getSecKillStageConcurrentUser")
    public ConcurrentUserData getSecKillStageConcurrentUser(@RequestBody SeckillStageData data) {
        return perfTestService.getConcurrentUserWithSeckillStage(data);
    }

    @ApiOperation(value = "通过监控统计估算最大QPS", notes = "")
    @PostMapping(value = "/getMaximumQPS")
    public BigDecimal getMaximumQPS(@RequestBody OnlineMonitorData data) {
        return perfTestService.getQPSByMonitor(data);
    }

    @ApiOperation(value = "通过监控统计对比估算低频业务QPS", notes = "")
    @PostMapping(value = "/getNormalQPS")
    public BigDecimal getNormalQPS(@RequestBody OnlineMonitorDataCollection dataCollection) {
        return perfTestService.getSimpleQPSByCompare(dataCollection);
    }
}
