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
public class OatCaseItem {
    @ApiModelProperty(value = "正交用例", name = "testCase", required = true, notes = "代表正交设计的单个用例")
    private List<OatValueItem> testCase;
}
