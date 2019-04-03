package com.cfpamf.test.design.service.impl;

import com.cfpamf.test.design.dto.req.PictReqDto;
import com.cfpamf.test.design.dto.resp.PictRespDto;
import com.cfpamf.test.design.service.IGetCaseService;
import com.google.common.collect.Lists;
import com.rmn.pairwise.IInventory;
import com.rmn.pairwise.PairwiseInventoryFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author flavone
 * @date 2019/02/22
 */
@Service
public class PictServiceImpl implements IGetCaseService<PictReqDto, PictRespDto> {

    /**
     * 使用PICT工具生成成对测试用例
     *
     * @param input
     * @return
     */
    private List<String> getTestSets(String input) {
        IInventory inventory = PairwiseInventoryFactory.generateParameterInventory(input);
        List<Map<String, String>> rawDataSet = inventory.getTestDataSet().getTestSets();
        List<String> resultList = Lists.newArrayList();
        rawDataSet.forEach(stringStringMap -> resultList.add(stringStringMap.toString()));
        return resultList;
    }

    /**
     * 将入参列表转换为逗号分隔的字符串
     *
     * @param input
     * @return
     */
    private String listToString(List<String> input) {
        if (null == input || input.size() <= 1) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(input.get(0));
        for (int i = 1; i < input.size(); i++) {
            sb.append(System.getProperty("line.separator")).append(input.get(i));
        }
        return sb.toString();
    }

    @Override
    public PictRespDto getTestCase(PictReqDto inputData) {
        PictRespDto dto = new PictRespDto();
        String inputStr = listToString(inputData.getInput());
        if (null == inputStr) {
            dto.setResult(null);
            dto.setStatus(false);
            dto.setErrMsg("输入行数小于1行");
        } else {
            List<String> result = getTestSets(inputStr);
            dto.setResult(result);
            dto.setStatus(true);
            dto.setCount(result.size());
        }
        return dto;
    }
}
