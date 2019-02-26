package com.cfpamf.test.design.vo.oat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 *
 * @author flavone
 * @date 2018/09/28
 */
@Data
public class OatSelectItem {
    @ApiModelProperty(value = "参数名", name = "name", required = true)
    private String name;

    @ApiModelProperty(value = "可选参数值", name = "values", required = true)
    private List<String> values;
}
