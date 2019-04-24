package com.cfpamf.test.design.bo;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author flavone
 * @date 2019/04/24
 */
public class CombinationTestFactoryTest {

    @Test
    public void testGetTestData() throws Exception {
        CombinationTestFactory<String, String> factory = CombinationTestFactory.getInstance();
        Map<String, List<String>> combinations = new HashMap<>();
        combinations.put("A", Lists.newArrayList("A1","A2","A4"));
        combinations.put("B", Lists.newArrayList("B1","B3"));
        combinations.put("C", Lists.newArrayList("C1","C2","C3"));
        combinations.put("D", Lists.newArrayList("D1"));
        combinations.put("E", Lists.newArrayList("E2","E3","E4"));
        List<Map<String, String>> excludes = Lists.newArrayList();
        Map<String, String> e1  = new HashMap<>();
        e1.put("A","A1");
        e1.put("B", "B3");
        Map<String, String> e2  = new HashMap<>();
        e2.put("A","A2");
        e2.put("C","C3");
        e2.put("E","E1");
        excludes.add(e1);
        excludes.add(e2);

        List<Map<String, String>> result =  factory.getTestData(combinations, excludes);
        System.out.println(result.size());
        result.forEach(resultMap->{
            System.out.println(resultMap.toString());
        });
    }
}