package com.cfpamf.test.design.dto.req;

import com.cfpamf.test.design.vo.RtEdgeItem;
import com.cfpamf.test.design.vo.RtNodeItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 *
 * @author flavone
 * @date 2018/09/26
 */
@Data
@ApiModel(value = "RtReqDto", description = "流程图数据集")
public class RtReqDto {
    public RtReqDto() {
    }

    @ApiModelProperty(value = "节点集合", name = "nodes", required = true, notes = "代表各状态的节点的集合")
    private List<RtNodeItem> nodes;

    @ApiModelProperty(value = "边集合", name = "edges", required = true, notes = "代表边的集合，如果边的前后节点不在节点集合中，则该边无效")
    private List<RtEdgeItem> edges;

    @ApiModelProperty(value = "权重比例", name = "weightPercent", notes = "选择权重在前百分比的流程")
    private Integer weightPercent;

    @ApiModelProperty(value = "起始节点", name = "startNodeLabel", required = true)
    private String startNodeLabel;

    @ApiModelProperty(value = "结束节点", name = "stopNodeLabel", required = true)
    private String stopNodeLabel;

    @ApiModelProperty(value = "模式, 0默认DFS", name = "mode", notes = "DFS=0 或 BFS=1")
    private Integer mode;
}
