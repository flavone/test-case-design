package com.cfpamf.test.design.bo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>节点对象，包括 标签，权重, 边</p>
 * 如果要支持自动化，可扩展节点的边{@link GraphEdge}
 *
 * @author flavone
 * @date 2018/09/25
 */
@Data
public class GraphNode {
    private List<GraphEdge> edgeList;

    private String label;

    private int weight = 0;


    /**
     * 根据节点名称生成一个唯一节点，默认权重为0，该节点初始的边{@link GraphEdge}为空
     * @param label 节点名称
     */
    public GraphNode(String label) {
        this.label = label;
        this.edgeList = new ArrayList<>();
    }

    /**
     * 根据节点名称和权重生成唯一节点，该节点初始的边{@link GraphEdge}为空
     * @param label 节点名称
     * @param weight 权重
     */
    public GraphNode(String label, int weight) {
        this.label = label;
        this.weight = weight;
        this.edgeList = new ArrayList<>();
    }

    /** 添加从该节点出发的边
     * @param edge from为该节点的边
     */
    public void addEdge(GraphEdge edge) {
        this.edgeList.add(edge);
    }
}
