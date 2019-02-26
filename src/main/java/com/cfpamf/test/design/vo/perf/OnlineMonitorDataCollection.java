package com.cfpamf.test.design.vo.perf;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author flavone
 * @date 2019/02/26
 */
@Data
public class OnlineMonitorDataCollection {
    @ApiModelProperty(value = "最大值")
    OnlineMonitorData maxData;
    @ApiModelProperty(value = "一般值")
    OnlineMonitorData normalData;
}
