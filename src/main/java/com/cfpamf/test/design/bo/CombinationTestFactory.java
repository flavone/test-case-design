package com.cfpamf.test.design.bo;

import com.google.common.collect.Lists;

import java.util.*;

/**
 * 组合测试工具
 * @author flavone
 * @date 2019/04/23
 */
public class CombinationTestFactory<K, V> {

    public List<Map<K, V>> getTestData(Map<K, List<V>> combinations, List<Map<K, V>> exculdes) {
        if (null == combinations || combinations.isEmpty()) {
            return null;
        }
        List<Map<K, V>> result = Lists.newArrayList();
        combinationSelect(result, combinations, 0);
        excludeResult(result, exculdes);
        return result;
    }

    public static <K, V> CombinationTestFactory<K, V> getInstance() {
        return new CombinationTestFactory<>();
    }

    private void combinationSelect(List<Map<K, V>> result, Map<K, List<V>> combinations, int index) {
        Map.Entry<K, List<V>> entry = getEntryByIndex(combinations, index);
        if (null == entry) {
            return;
        }
        if (result.isEmpty()) {
            entry.getValue().forEach(v -> {
                Map<K, V> map = new HashMap<>(16);
                map.put(entry.getKey(), v);
                result.add(map);
            });
        } else {
            List<Map<K, V>> tmpResult = Lists.newArrayList();
            entry.getValue().forEach(v -> {
                result.forEach(kvMap -> {
                    Map<K, V> map = new HashMap<>(16);
                    map.putAll(kvMap);
                    map.put(entry.getKey(), v);
                    tmpResult.add(map);
                });
            });
            result.clear();
            result.addAll(tmpResult);
        }
        combinationSelect(result, combinations, index + 1);
    }

    private Map.Entry<K, List<V>> getEntryByIndex(Map<K, List<V>> combinations, int index) {
        int i = 0;
        for (Map.Entry<K, List<V>> kListEntry : combinations.entrySet()) {
            if (i == index) {
                return kListEntry;
            } else {
                i++;
            }
        }
        return null;
    }

    private void excludeResult(List<Map<K, V>> result, List<Map<K, V>> excludes) {
        if (null == excludes || excludes.isEmpty()) {
            return;
        }
        Iterator<Map<K, V>> iterator = result.iterator();
        while (iterator.hasNext()) {
            Map<K, V> currentResult = iterator.next();
            for (Map<K, V> currentExResult : excludes) {
                if (currentResult.entrySet().containsAll(currentExResult.entrySet())) {
                    iterator.remove();
                    break;
                }
            }
        }
    }
}
