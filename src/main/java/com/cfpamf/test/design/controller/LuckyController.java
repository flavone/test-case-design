package com.cfpamf.test.design.controller;

import com.cfpamf.test.design.util.CalculatorUtil;
import com.sun.javafx.binding.StringFormatter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @author flavone
 * @date 2019/04/15
 */
@Api(value = "Lucky", tags = "试试手气")
@RestController
@RequestMapping(value = "/lucky")
public class LuckyController {
    @PostMapping(value = "/goodLuck")
    @ApiOperation(value = "试试手气", notes = "梦想, 还是要有的...")
    public String getFun(@RequestParam(required = false) Integer count) {
        if (null == count || count <= 0) {
            return StringFormatter.format("%s %s\n",
                    Arrays.toString(CalculatorUtil.getRandom(5, 1, 35)),
                    Arrays.toString(CalculatorUtil.getRandom(2, 1, 12))).getValue();
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(StringFormatter.format("%s %s\n",
                    Arrays.toString(CalculatorUtil.getRandom(5, 1, 35)),
                    Arrays.toString(CalculatorUtil.getRandom(2, 1, 12))).getValue());
        }
        return sb.toString();
    }
}
