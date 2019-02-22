package com.cfpamf.test.design.bo;

import lombok.Data;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 一个有向图，包括若干有向图节点{@link GraphNode}，以及若干联接节点的边{@link GraphEdge}
 *
 * @author flavone
 * @date 2018/09/25
 */
@Data
public class DirectGraph {
    private List<GraphNode> nodes = null;

    private void initNodes(List<GraphNode> nodes) {
        if (this.nodes == null) {
            this.nodes = new ArrayList<>();
        }
        this.nodes.addAll(new ArrayList<>(nodes));
    }

    private DirectGraph(List<GraphNode> nodes, List<GraphEdge> edges) {
        initNodes(nodes);
        for (GraphNode node : nodes) {
            edges.stream().filter(edge -> Objects.equals(edge.getNodeLeft(), node.getLabel())).forEach(node::addEdge);
        }
    }

    /**
     * <p>根据JSON格式的对象生成有向图</p>
     * <p>这种格式的json比较不好写，因为除了node需要定义，每个edge也需要定义</p>
     * <p>TODO 增加一种格式，只需要定义node，以及node潜在的后向node名称</p>
     *
     * @param jsonObject JSON对象
     * @return 有向图对象
     * @throws JSONException
     */
    public static DirectGraph getInstanceFromJson(JSONObject jsonObject) throws JSONException {
        List<GraphNode> nodeList = new ArrayList<>();
        List<GraphEdge> edgeList = new ArrayList<>();
        JSONArray jsonNodes = jsonObject.getJSONArray("nodes");
        JSONArray jsonEdges = jsonObject.getJSONArray("edges");
        // 节点解析
        for (int i = 0; i < jsonNodes.length(); i++) {
            JSONObject jsonNode = jsonNodes.getJSONObject(i);
            if (jsonNode.has("weight")) {
                nodeList.add(new GraphNode(jsonNode.getString("label"), jsonNode.getInt("weight")));
            } else {
                nodeList.add(new GraphNode(jsonNode.getString("label")));
            }
        }
        // 边解析，根据节点名称判断边的节点
        for (int i = 0; i < jsonEdges.length(); i++) {
            JSONObject jsonEdge = jsonEdges.getJSONObject(i);
            String fromNodeLabel = jsonEdge.getString("from");
            String toNodeLabel = jsonEdge.getString("to");
            GraphNode fromNode = null;
            GraphNode toNode = null;
            for (GraphNode tmpNode : nodeList) {
                if (Objects.equals(tmpNode.getLabel(), fromNodeLabel)) {
                    fromNode = tmpNode;
                    break;
                }
            }
            for (GraphNode tmpNode : nodeList) {
                if (Objects.equals(tmpNode.getLabel(), toNodeLabel)) {
                    toNode = tmpNode;
                    break;
                }
            }
            //边必须有来向节点和去向节点
            if (fromNode == null || toNode == null) {
                continue;
            }
            // 是否需要路径权重？
            if (jsonEdge.has("weight")) {
                edgeList.add(new GraphEdge(fromNode.getLabel(), toNode.getLabel(), jsonEdge.getInt("weight")));
            } else {
                edgeList.add(new GraphEdge(fromNode.getLabel(), toNode.getLabel()));
            }
        }
        return new DirectGraph(nodeList, edgeList);
    }
}
