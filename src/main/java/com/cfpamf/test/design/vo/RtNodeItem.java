package com.cfpamf.test.design.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author flavone
 * @date 2018/09/26
 */
@ApiModel(value = "RtNodeItem", description = "节点参数")
@Data
public class RtNodeItem {
    @ApiModelProperty(value = "标签", name = "label", required = true, notes = "自定义名称")
    private String label;

    @ApiModelProperty(value = "权重", name = "weight", allowEmptyValue = true, notes = "自定义权重")
    private Integer weight;
}
