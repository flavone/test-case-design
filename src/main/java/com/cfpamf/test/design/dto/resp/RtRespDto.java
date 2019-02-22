package com.cfpamf.test.design.dto.resp;

import com.cfpamf.test.design.vo.RtCaseItem;
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
@ApiModel
public class RtRespDto extends BaseRespDto {
    @ApiModelProperty(value = "流程遍历结果", name = "result", required = true, notes = "代表流程遍历的用例的集合")
    private List<RtCaseItem>  result;
}
