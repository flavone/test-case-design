package com.cfpamf.test.design.vo.dt;

import com.cfpamf.test.design.enums.DtTypeEnum;
import com.cfpamf.test.design.enums.DtValueEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author flavone
 * @date 2019/04/23
 */
@Data
@ApiModel(value = "域设置对象")
public class DtCaseItem {
    /**
     * 数据范围类型，是列表还是区间
     */
    @ApiModelProperty(value = "数据范围类型，是列表还是区间")
    private DtTypeEnum dtType;

    /**
     * 数据类型，是整型、浮点型、还是字符串
     */
    @ApiModelProperty(value = "数据类型，是整型、浮点型、还是字符串")
    private DtValueEnum dtValue;

    /**
     * 数据值表达式
     * <p>当为整数时，是一个数值区间，如 [-1, 1000]</p>
     * <p>当为浮点数时，也是一个数值区间，但包含了精确度，如[-123.12, 100.1234567], 即精确度区间为2-7位小数</p>
     * <p>当为字符串时，是一个正则表达式,如 123(.+?)[a-z]456，或者是一个正整数区间, 如 [1,100]</p>
     * <p>当为列表时，是一个列表，如[A, B, C, D]</p>
     */
    @ApiModelProperty(value = "数据值表达式 \n 当为整数时，是一个数值区间，如 [-1, 1000]\n" +
            "当为浮点数时，也是一个数值区间，但包含了精确度，如[-123.12, 100.1234567], 即精确度区间为2-7位小数\n" +
            "当为字符串时，是一个正则表达式,如 123(.+?)[a-z]456，或者是一个正整数区间, 如 [1,100]\n" +
            "当为列表时，是一个列表，如[A, B, C, D]")
    private String dtContent;
}
