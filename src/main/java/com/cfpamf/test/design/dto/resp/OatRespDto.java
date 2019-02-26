package com.cfpamf.test.design.dto.resp;

import com.cfpamf.test.design.vo.oat.OatCaseItem;
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
@ApiModel()
public class OatRespDto extends BaseRespDto {
    @ApiModelProperty(value = "正交用例结果", name = "result", required = true, notes = "代表正交设计的用例的集合")
    private List<OatCaseItem> result;
}
