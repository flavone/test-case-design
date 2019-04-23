package com.cfpamf.test.design.enums;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author flavone
 */
@Getter
@AllArgsConstructor
@ApiModel
public enum DtValueEnum {
    /**
     * 是整数
     */
    @ApiModelProperty(value = "是整数")
    IS_INTEGER,
    /**
     * 是浮点数
     */
    @ApiModelProperty(value = "是浮点数")
    IS_FLOAT,
    /**
     * 是字符串
     */
    @ApiModelProperty(value = "是字符串")
    IS_STRING
}
