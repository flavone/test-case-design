package com.cfpamf.test.design.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author flavone
 * @date 2019/02/22
 */
@Data
@ApiModel()
public class PictRespDto extends BaseRespDto {
    @ApiModelProperty(value = "PICT正交用例结果", name = "result")
    private List<String> result;
}
