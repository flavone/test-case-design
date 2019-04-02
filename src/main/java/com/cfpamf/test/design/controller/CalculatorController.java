package com.cfpamf.test.design.controller;

import com.cfpamf.test.design.util.CalculatorUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author flavone
 * @date 2019/04/02
 */
@Api(value = "数学表达式计算", tags = "数学表达式计算API")
@RestController
@RequestMapping(value = "/calculator")
public class CalculatorController {
    @ApiOperation(value = "获取计算结果", notes = "获取加减乘除计算结果")
    @PostMapping(value = "/getTVGCases")
    public String doCalculate(
            @RequestParam(value = "数学表达式") String data,
            @RequestParam(value = "精确位数", required = false) Integer scale) {
        if (null == data) {
            return "数学表达式不能为空";
        }
        try {
            if (null == scale) {
                return CalculatorUtil.conversion(data).toString();
            } else {
                return CalculatorUtil.conversion(data).setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
            }
        } catch (Exception e){
            return "无法计算出结果：" + e.getMessage();
        }
    }
}
