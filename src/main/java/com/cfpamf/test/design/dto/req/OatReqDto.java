package com.cfpamf.test.design.dto.req;

import com.cfpamf.test.design.vo.OatCaseItem;
import com.cfpamf.test.design.vo.OatSelectItem;
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
@ApiModel(value = "OatReqDto", description = "正交数据集")
public class OatReqDto extends BaseReqDto {
    public OatReqDto() {
    }

    @ApiModelProperty(value = "数据集", name = "data", required = true)
    private List<OatSelectItem> data;

    @ApiModelProperty(value = "排除的状态集", name = "excludedCases")
    private List<OatCaseItem> excludedCases;

    @ApiModelProperty(value = "强度", name = "strength", required = false, notes = "在1-10之间", example = "2")
    private int strength;

}
