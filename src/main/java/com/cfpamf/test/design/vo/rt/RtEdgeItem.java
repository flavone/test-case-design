package com.cfpamf.test.design.vo.rt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author flavone
 * @date 2018/09/26
 */
@ApiModel(value = "RtEdgeItem", description = "边的参数")
@Data
public class RtEdgeItem {
    @ApiModelProperty(value = "前节点", name = "from", required = true, notes = "")
    private String from;

    @ApiModelProperty(value = "后节点", name = "to", required = true, notes = "")
    private String to;

    @ApiModelProperty(value = "权重", name = "weight", notes = "该节点流向的权重")
    private Integer weight;
}
