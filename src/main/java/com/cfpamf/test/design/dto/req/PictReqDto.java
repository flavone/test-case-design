package com.cfpamf.test.design.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author flavone
 * @date 2019/02/22
 */
@Data
@ApiModel(value = "PictReqDto", description = "正交数据集")
public class PictReqDto extends BaseReqDto {
    public PictReqDto() {
    }

    @ApiModelProperty(value = "数据集,如 \r\n[\"User:A,B,C\",\"Name:A1,B1,C1\",\"Age:10,20,30\"]", name = "input", required = true)
    public List<String> input;
}
