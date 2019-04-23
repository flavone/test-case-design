package com.cfpamf.test.design.dto.req;

import com.cfpamf.test.design.vo.dt.DtCaseItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author flavone
 * @date 2019/04/23
 */
@Data
@ApiModel(value = "域测试请求")
public class DtReqDto {
    /**
     * 有效数据集
     */
    @ApiModelProperty(value = "有效数据集")
    private List<DtCaseItem> validCaseList;

    /**
     * 无效数据集，当且仅当数据类型为列表时，需要指定无效数据集
     */
    @ApiModelProperty(value = "无效数据集，当且仅当数据类型为列表时，需要指定无效数据集")
    private List<DtCaseItem> invalidCaseList;
}
