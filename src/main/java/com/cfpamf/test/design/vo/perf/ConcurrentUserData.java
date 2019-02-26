package com.cfpamf.test.design.vo.perf;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author flavone
 * @date 2019/02/25
 */
@Data
public class ConcurrentUserData {
    @ApiModelProperty(value = "平均并发用户")
    BigDecimal averageConcurrentUser;
    @ApiModelProperty(value = "最大并发用户")
    BigDecimal maximumConcurrentUser;

    public ConcurrentUserData() {
    }

    public ConcurrentUserData(BigDecimal avg, BigDecimal max) {
        this.averageConcurrentUser = avg;
        this.maximumConcurrentUser = max;
    }
}
