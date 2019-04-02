package com.cfpamf.test.design.util;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author flavone
 * @date 2019/04/02
 */
public class PhaseUtilTest {

    @Test
    public void testDoPhase() throws Exception {

    }

    @Test
    public void testGetKeys() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("aaa", "123");
        map.put("ccc","ss");
        map.put("eeee","jojo");
        String orignalStr = "123${aaa}\\${bb}${ccc}+DDD+${eeee}";
        List<String> list = PhaseUtil.getKeys(orignalStr);
        String newStr = PhaseUtil.doPhase(orignalStr, map);

        list.forEach(System.out::println);
        System.out.println(newStr);
    }
}