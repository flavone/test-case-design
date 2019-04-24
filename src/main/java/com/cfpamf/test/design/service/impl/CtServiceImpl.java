package com.cfpamf.test.design.service.impl;

import com.cfpamf.test.design.bo.CombinationTestFactory;
import com.cfpamf.test.design.dto.req.CtReqDto;
import com.cfpamf.test.design.dto.resp.CtRespDto;
import com.cfpamf.test.design.service.IGetCaseService;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 组合测试
 *
 * @author flavone
 * @date 2019/04/24
 */
@Service
public class CtServiceImpl implements IGetCaseService<CtReqDto, CtRespDto> {
    @Override
    public CtRespDto getTestCase(CtReqDto inputData) {
        CtRespDto dto = new CtRespDto();
        try {
            Map<String, List<String>> combinations = phaseCombinations(inputData.getCombinations());
            List<Map<String, String>> excludes = phaseExcludes(inputData.getExcludes());
            CombinationTestFactory<String, String> factory = CombinationTestFactory.getInstance();
            List<Map<String, String>> result = factory.getTestData(combinations, excludes);
            dto.setResult(phaseResult(result));
            dto.setCount(result.size());
            dto.setStatus(true);
        } catch (Exception e) {
            dto.setStatus(false);
            dto.setErrMsg(e.getMessage());
        }
        return dto;
    }

    private Map<String, List<String>> phaseCombinations(List<String> combinations) {
        if (null == combinations || combinations.isEmpty()) {
            return null;
        }
        Map<String, List<String>> combinationMap = new HashMap<>(16);
        combinations.forEach(s -> {
            String[] t = s.split(":");
            List<String> t1 = Arrays.asList(t[1].split(","));
            combinationMap.put(t[0], t1);
        });
        return combinationMap;
    }

    private List<Map<String, String>> phaseExcludes(List<String> excludes) {
        if (null == excludes || excludes.isEmpty()) {
            return null;
        }
        List<Map<String, String>> excludeList = Lists.newArrayList();
        excludes.forEach(s -> {
            Map<String, String> map = new HashMap<>(16);
            String[] t = s.split(",");
            for (String t1 : Arrays.asList(t)) {
                String[] t2 = t1.split("=");
                map.put(t2[0], t2[1]);
            }
            excludeList.add(map);
        });
        return excludeList;
    }

    private List<String> phaseResult(List<Map<String, String>> result) {
        List<String> phasedResult = Lists.newArrayList();
        if (null == result || result.isEmpty()) {
            return phasedResult;
        }
        result.forEach(map -> {
            phasedResult.add(map.toString());
        });
        return phasedResult;
    }
}
