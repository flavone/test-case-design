package com.cfpamf.test.design.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author flavone
 * @date 2019/04/02
 */
@Data
@ApiModel
public class NewRtRespDto {
    @ApiModelProperty(value = "用例个数")
    private Integer count;
    @ApiModelProperty(value = "流程遍历结果", name = "results", notes = "代表流程遍历的用例的集合")
    private List<String> results;
    @ApiModelProperty(value = "错误消息")
    private String errMsg;
}
