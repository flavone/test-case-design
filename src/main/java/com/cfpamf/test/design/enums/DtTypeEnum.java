package com.cfpamf.test.design.enums;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author flavone
 * @date 2019/04/23
 */
@Getter
@AllArgsConstructor
@ApiModel
public enum DtTypeEnum {
    /**
     * 是列表
     */
    @ApiModelProperty(value = "是列表")
    IS_LIST,
    /**
     * 是区间
     */
    @ApiModelProperty(value = "是区间")
    IS_DOMAIN
}
