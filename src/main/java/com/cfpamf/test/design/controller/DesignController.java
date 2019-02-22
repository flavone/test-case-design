package com.cfpamf.test.design.controller;

import com.cfpamf.test.design.dto.req.OatReqDto;
import com.cfpamf.test.design.dto.req.PictReqDto;
import com.cfpamf.test.design.dto.req.RtReqDto;
import com.cfpamf.test.design.dto.resp.OatRespDto;
import com.cfpamf.test.design.dto.resp.PictRespDto;
import com.cfpamf.test.design.dto.resp.RtRespDto;
import com.cfpamf.test.design.service.IGetCaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author flavone
 * @date 2018/09/06
 */
@Api(value = "用例设计", tags = "测试设计API")
@RestController
@RequestMapping(value = "/design")
public class DesignController {

    @Autowired
    private IGetCaseService<OatReqDto, OatRespDto> oatService;

    @Autowired
    private IGetCaseService<RtReqDto, RtRespDto> rtService;

    @Autowired
    private IGetCaseService<PictReqDto, PictRespDto> pictService;

    @ApiOperation(value = "获取TVG正交试验用例", notes = "通过正交试验工具TVG获取成对测试的用例")
    @PostMapping(value = "/getTVGCases")
    public OatRespDto getTVGCase(@RequestBody OatReqDto data) {
        return oatService.getTestCase(data);
    }

    @ApiOperation(value = "获取流程图用例", notes = "通过有向图遍历起始和结束节点之间的全部流程")
    @PostMapping(value = "/getRTCases")
    public RtRespDto getRTCase(@RequestBody RtReqDto data) {
        return rtService.getTestCase(data);
    }

    @ApiOperation(value = "获取PICT正交试验用例", notes = "通过PICT工具获取测试用例")
    @PostMapping(value = "/getPICTCases")
    public PictRespDto getPICTCase(@RequestBody PictReqDto input) {
        return pictService.getTestCase(input);
    }
}
