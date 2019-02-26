package com.cfpamf.test.design.vo.perf;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author flavone
 * @date 2019/02/25
 */
@Data
public class OnlineStatisticsData {
    @ApiModelProperty(value = "日活跃用户数", required = true)
    BigDecimal dailyVisitUser;
    @ApiModelProperty(value = "统计周期(小时)", required = true)
    BigDecimal statisticsPeriod;
    @ApiModelProperty(value = "活跃度，0-1之间")
    BigDecimal activityRatio;
}
