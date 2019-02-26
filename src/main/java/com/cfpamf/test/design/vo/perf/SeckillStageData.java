package com.cfpamf.test.design.vo.perf;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author flavone
 * @date 2019/02/25
 */
@Data
public class SeckillStageData {
    @ApiModelProperty("参与用户总数")
    BigDecimal arrivalUser;
    @ApiModelProperty("用户每秒提交频率")
    BigDecimal submitFrequency;
}
