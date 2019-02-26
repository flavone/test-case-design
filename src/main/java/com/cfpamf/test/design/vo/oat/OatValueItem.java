package com.cfpamf.test.design.vo.oat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * @author flavone
 * @date 2018/09/28
 */
@Data
public class OatValueItem {
    @ApiModelProperty(value = "参数名", name = "name", required = true)
    private String name;

    @ApiModelProperty(value = "参数值", name = "values", required = true)
    private String value;
}
