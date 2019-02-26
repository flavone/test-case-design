package com.cfpamf.test.design.vo.perf;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author flavone
 * @date 2019/02/25
 */
@Data
public class OnlineMonitorData {
    @ApiModelProperty(value = "总请求次数")
    BigDecimal queryCount;
    @ApiModelProperty(value = "平均响应时间")
    BigDecimal averageResponseTime;
    @ApiModelProperty(value = "监控统计时间(小时)")
    BigDecimal monitorPeriod;
}
