package com.cfpamf.test.design.util;

import com.cfpamf.test.design.dto.req.NewRtReqDto;
import com.cfpamf.test.design.dto.req.RtReqDto;
import com.cfpamf.test.design.vo.rt.RtEdgeItem;
import com.cfpamf.test.design.vo.rt.RtNodeItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
     * 符号替换
     */
    private static final String[] INVALID_SYMBOLS = {"，", "。", "（", "）"};

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

    public static String replaceInvalidSymbol(String originalString) {
        if (null == originalString || originalString.length() == 0) {
            return "";
        }
        for (String s : INVALID_SYMBOLS) {
            switch (s) {
                case "，":
                    originalString = originalString.replaceAll(s, ",");
                    break;
                case "。":
                    originalString = originalString.replaceAll(s, ".");
                    break;
                case "（":
                    originalString = originalString.replaceAll(s, "(");
                    break;
                case "）":
                    originalString = originalString.replaceAll(s, ")");
                    break;
                default:
                    break;
            }
        }
        return originalString;
    }

    public static RtReqDto dto2Dto(NewRtReqDto reqDto) {
        RtReqDto dto = new RtReqDto();
        List<RtNodeItem> nodes = new ArrayList<>();
        List<RtEdgeItem> edges = new ArrayList<>();
        for (String s : reqDto.getInputs()) {
            s = s.trim();
            s = replaceInvalidSymbol(s);
            String[] strList = s.split(":");
            RtNodeItem node = new RtNodeItem();
            node.setLabel(strList[0]);
            node.setWeight(0);
            if (nodes.size() == 0) {
                nodes.add(node);
            } else if (nodes.stream().noneMatch(k -> Objects.equals(k.getLabel(), node.getLabel()))) {
                nodes.add(node);
            }
            String[] nextNodeLabels = strList[1].split(",");
            for (String nextNode : nextNodeLabels) {
                RtEdgeItem edge = new RtEdgeItem();
                edge.setFrom(strList[0]);
                edge.setTo(nextNode);
                edge.setWeight(0);
                if (edges.size() == 0) {
                    edges.add(edge);
                } else if (edges.stream().noneMatch(k -> Objects.equals(k.getFrom(), edge.getFrom()) && Objects.equals(k.getTo(), edge.getTo()))) {
                    edges.add(edge);
                }
            }
        }
        dto.setNodes(nodes);
        dto.setEdges(edges);
        dto.setMode(reqDto.getMode());
        dto.setStartNodeLabel(reqDto.getStartNodeLabel());
        dto.setStopNodeLabel(reqDto.getStopNodeLabel());
        dto.setWeightPercent(reqDto.getWeightPercent());
        return dto;
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
