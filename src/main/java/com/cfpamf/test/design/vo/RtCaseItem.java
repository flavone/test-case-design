package com.cfpamf.test.design.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 *
 * @author flavone
 * @date 2018/10/08
 */
@Data
public class RtCaseItem {
    @ApiModelProperty(value = "流程遍历用例", name = "testCase", required = true, notes = "代表流程遍历的单个用例")
    List<RtNodeItem> testCase;
}
