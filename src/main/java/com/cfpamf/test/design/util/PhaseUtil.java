package com.cfpamf.test.design.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串参数解析工具
 *
 * @author flavone
 * @date 2019/3/31 11:49
 */
public class PhaseUtil {
    /**
     * 将字符串中${xxx}替换成对应的字符串
     *
     * @param originalString
     * @param kvMap
     * @return
     */
    public static String doPhase(String originalString, Map<String, String> kvMap) {
        final String[] phasedString = {originalString};
        List<String> keyList = getInstance().getKeyList(originalString);
        kvMap.forEach((k, v) -> {
            if (keyList.contains(k)) {
                phasedString[0] = phasedString[0].replaceAll("\\$\\{" + k + "}", v);
            }
        });
        return phasedString[0];
    }

    /**
     * 获取字符串中${xxx}内的参数值
     *
     * @param originalString
     * @return
     */
    public static List<String> getKeys(String originalString) {
        return getInstance().getKeyList(originalString);
    }

    private static PhaseUtil getInstance() {
        return new PhaseUtil();
    }

    private List<String> getKeyList(String originalString) {
        String reg = "(?<=(?<!\\\\)\\$\\{)(.*?)(?=(?<!\\\\)\\})";
        return find(reg, originalString);
    }

    private List<String> find(String reg, String str) {
        Matcher matcher = Pattern.compile(reg).matcher(str);
        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(matcher.group());
        }
        return list;
    }
}
