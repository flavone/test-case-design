package com.cfpamf.test.design.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author flavone
 * @date 2019/04/24
 */
@Data
@ApiModel()
public class CtRespDto extends BaseRespDto {
    @ApiModelProperty(value = "组合测试用例结果", name = "result")
    private List<String> result;
}
