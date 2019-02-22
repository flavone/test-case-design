package com.cfpamf.test.design.bo;

import lombok.Data;

/**
 *
 * @author flavone
 * @date 2018/09/25
 */
@Data
public class GraphEdge {
    private String nodeLeft;

    private String nodeRight;

    private int weight = 0;

    public GraphEdge(String nodeLeft, String nodeRight) {
        this.nodeLeft = nodeLeft;
        this.nodeRight = nodeRight;
    }

    public GraphEdge(String nodeLeft, String nodeRight, int weight) {
        this.nodeLeft = nodeLeft;
        this.nodeRight = nodeRight;
        if (weight > 0) {
            this.weight = weight;
        }
    }
}
