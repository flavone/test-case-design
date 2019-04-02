package com.cfpamf.test.design.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author flavone
 * @date 2019/04/02
 */
@Data
@ApiModel(value = "NewRtReqDto", description = "流程图数据集")
public class NewRtReqDto {
    @ApiModelProperty(value = "数据集合", name = "inputs", notes = "节点及其潜在的后向节点，如[\"A:B,C,D\", \"B:C,D\",\"D:E\"]")
    private List<String> inputs;

    @ApiModelProperty(value = "权重比例", name = "weightPercent", notes = "选择权重在前百分比的流程")
    private Integer weightPercent;

    @ApiModelProperty(value = "起始节点", name = "startNodeLabel", required = true)
    private String startNodeLabel;

    @ApiModelProperty(value = "结束节点", name = "stopNodeLabel", required = true)
    private String stopNodeLabel;

    @ApiModelProperty(value = "模式, 0默认DFS", name = "mode", notes = "DFS=0 或 BFS=1")
    private Integer mode;
}
