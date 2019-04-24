package com.cfpamf.test.design.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author flavone
 * @date 2019/04/24
 */
@Data
@ApiModel(value = "CtReqDto", description = "组合数据集")
public class CtReqDto {
    @ApiModelProperty(value = "组合数据集,如 \r\n[\"User:A,B,C\",\"Name:A1,B1,C1\",\"Age:10,20,30\"]", name = "combinations", required = true)
    private List<String> combinations;

    @ApiModelProperty(value = "排除数据集,如 \r\n[\"User=A, Name=A1\",\"Name=B1,Age=10\"],可以不填", name = "excludes", required = false)
    private List<String> excludes;
}
