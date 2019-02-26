package com.cfpamf.test.design.service.impl;

import com.cfpamf.test.design.dto.req.OatReqDto;
import com.cfpamf.test.design.dto.resp.OatRespDto;
import com.cfpamf.test.design.service.IGetCaseService;
import com.cfpamf.test.design.vo.oat.OatCaseItem;
import com.cfpamf.test.design.vo.oat.OatSelectItem;
import com.cfpamf.test.design.vo.oat.OatValueItem;
import org.springframework.stereotype.Service;
import pairwisetesting.PairwiseTestingToolkit;
import pairwisetesting.coredomain.*;
import pairwisetesting.engine.tvg.TVGEngine;
import pairwisetesting.testcasesgenerator.TXTTestCasesGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author flavone
 * @date 2018/09/07
 */
@Service
public class OatServiceImpl implements IGetCaseService<OatReqDto, OatRespDto> {
    private MetaParameter tmp = null;

    private String[][] getTestData(OatReqDto data) throws MetaParameterException, EngineException {
        PairwiseTestingToolkit toolkit = new PairwiseTestingToolkit();
        toolkit.setMetaParameterProvider(data2Provider(data));
        toolkit.setEngine(new TVGEngine());
        toolkit.setTestCasesGenerator(new TXTTestCasesGenerator());
        return toolkit.generateTestData();
    }

    private IMetaParameterProvider data2Provider(OatReqDto data) {
        return () -> {
            MetaParameter mp = new MetaParameter(data.getStrength());

            for (OatSelectItem item : data.getData()) {
                Factor f = new Factor(item.getName());
                item.getValues().forEach(f::addLevel);
                mp.addFactor(f);
            }
            tmp = mp;
            return mp;
        };
    }

    /**
     * 获取正交用例设计返回结果
     *
     * @param inputData 入参
     * @return 返回正交用例
     */
    @Override
    public OatRespDto getTestCase(OatReqDto inputData) {
        OatRespDto dto = new OatRespDto();
        try {
            String[][] data = getTestData(inputData);
            int mpLength = tmp.getNumOfFactors();
            String[] factorNames = tmp.getFactorNames();
            List<OatCaseItem> ci = new ArrayList<>();
            for (String[] row : data) {
                OatCaseItem item = new OatCaseItem();
                List<OatValueItem> list = new ArrayList<>();
                for (int j = 0; j < mpLength; j++) {
                    OatValueItem vi = new OatValueItem();
                    vi.setName(factorNames[j]);
                    vi.setValue(row[j]);
                    list.add(vi);
                }
                if (notInExcludedCases(inputData.getExcludedCases(), list)) {
                    item.setTestCase(list);
                    ci.add(item);
                }
            }
            dto.setStatus(true);
            dto.setCount(ci.size());
            dto.setStatus(true);
            dto.setResult(ci);
        } catch (MetaParameterException | EngineException e) {
            dto.setStatus(false);
            dto.setCount(0);
            dto.setErrMsg(e.getMessage());
        }
        return dto;
    }

    /**
     * 判断当前组合是否在排除列表中
     *
     * @param excludedCases 排除列表
     * @param items         参数组合
     * @return 如果不在排除列表则为 true
     */
    private boolean notInExcludedCases(List<OatCaseItem> excludedCases, List<OatValueItem> items) {
        if (excludedCases == null || excludedCases.size() < 1) {
            return true;
        }
        List<OatCaseItem> excludedItems = new ArrayList<>(excludedCases);
        for (OatCaseItem excludedItem : excludedItems) {
            List<OatValueItem> excludedPVList = excludedItem.getTestCase();
            if (items.containsAll(excludedPVList)) {
                return false;
            }
        }
        return true;
    }
}
