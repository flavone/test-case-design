package com.cfpamf.test.design.controller;

import com.cfpamf.test.design.dto.req.*;
import com.cfpamf.test.design.dto.resp.*;
import com.cfpamf.test.design.service.IGetCaseService;
import com.cfpamf.test.design.util.PhaseUtil;
import com.cfpamf.test.design.vo.rt.RtCaseItem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private IGetCaseService<CtReqDto, CtRespDto> ctService;

    @ApiOperation(value = "获取TVG正交试验用例", notes = "通过正交试验工具TVG获取成对测试的用例", hidden = true)
    @PostMapping(value = "/getTVGCases")
    public OatRespDto getTVGCase(@RequestBody OatReqDto data) {
        return oatService.getTestCase(data);
    }

    @ApiOperation(value = "获取流程图用例", notes = "通过有向图遍历起始和结束节点之间的全部流程，支持自循环和闭环")
    @PostMapping(value = "/getRTCaseString")
    public NewRtRespDto getRTCaseString(@RequestBody NewRtReqDto input) {
        NewRtRespDto respDto = new NewRtRespDto();
        try {
            RtReqDto data = PhaseUtil.dto2Dto(input);
            RtRespDto dto = rtService.getTestCase(data);
            if (!dto.isStatus()) {
                respDto.setErrMsg(dto.getErrMsg());
                return respDto;
            }
            List<String> stringList = new ArrayList<>();
            for (RtCaseItem item : dto.getResult()) {
                List<String> str = new ArrayList<>();
                item.getTestCase().stream().forEach(k -> str.add(k.getLabel()));
                stringList.add(String.join("->", str));
            }
            respDto.setCount(stringList.size());
            respDto.setResults(stringList);
            respDto.setErrMsg("success");
        } catch (Exception e) {
            respDto.setErrMsg(e.getMessage());
        }
        return respDto;
    }

    @ApiOperation(value = "获取流程图用例", notes = "通过有向图遍历起始和结束节点之间的全部流程", hidden = true)
    @PostMapping(value = "/getRTCases")
    public RtRespDto getRTCase(@RequestBody RtReqDto data) {
        return rtService.getTestCase(data);
    }

    @ApiOperation(value = "获取PICT正交试验用例", notes = "通过PICT工具获取测试用例")
    @PostMapping(value = "/getPICTCases")
    public PictRespDto getPICTCase(@RequestBody PictReqDto input) {
        return pictService.getTestCase(input);
    }

    @ApiOperation(value = "获取组合测试用例", notes = "通过组合测试工具获取测试用例")
    @PostMapping(value = "/getCTCases")
    public CtRespDto getCTCase(@RequestBody CtReqDto input) {
        return ctService.getTestCase(input);
    }
}
