package com.cfpamf.test.design.service.impl;

import com.cfpamf.test.design.bo.DirectGraph;
import com.cfpamf.test.design.bo.GraphEdge;
import com.cfpamf.test.design.bo.GraphNode;
import com.cfpamf.test.design.dto.req.RtReqDto;
import com.cfpamf.test.design.dto.resp.RtRespDto;
import com.cfpamf.test.design.service.IGetCaseService;
import com.cfpamf.test.design.vo.rt.RtNodeItem;
import com.cfpamf.test.design.vo.rt.RtCaseItem;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author flavone
 * @date 2018/09/25
 */
@Service
public class RtServiceImpl implements IGetCaseService<RtReqDto, RtRespDto> {

    private static final int SIMPLE_NODE_PATH_SIZE = 1;

    private List<List<GraphNode>> result;

    private boolean nodeCycle;

    private boolean edgeCycle;

    private int rollback;

    /**
     * 深度优先遍历，因为只要遇到目标节点就会记录，所以最前面的路径并非最长的路径
     * <p>这么说其实不算DFS (:笑</p>
     *
     * @param currentNode 当前节点
     * @param endNode     目标节点
     * @param path        当前遍历路径
     */
    private void depthFirstSearch(@NotNull List<GraphNode> nodes, GraphNode currentNode, GraphNode endNode, List<GraphNode> path) {
        path.add(currentNode);
        //当前节点是目标节点则保存并返回
        if (Objects.equals(currentNode.getLabel(), endNode.getLabel())) {
            //路径未保存,则保存路径
            if (!containsBranch(path)) {
                this.result.add(new ArrayList<>(path));
            }
            //找到结果后回滚到最近的分叉路口
            //rollbackToIndex(path);
            //应该是直接回滚上一节点，因为存在情况导致rollback值取未找到结果时的最大path
            rollback(path);
            return;
        }
        //当前节点没有边则移除
        if (currentNode.getEdgeList() == null || currentNode.getEdgeList().size() == 0) {
            rollback(path);
            return;
        }
        /*
        解决内循环问题和自循环问题
        内循环即 既有NodeA->NodeB，也有NodeB->NodeA，这种会导致死循环
        自循环即 NodeA->NodeA，这种也会导致死循环
         */
        for (GraphEdge edge : currentNode.getEdgeList()) {
            //当出现分叉路径时，记录回滚路径
            //rollback现在没用了，因为无论如何，当前递归完成时都会往前回滚一次，这里先留着
            if (currentNode.getEdgeList().size() > 1) {
                rollback = path.lastIndexOf(currentNode) + 1;
            }
            GraphNode nextNode = nodes.stream().filter(graphNode -> Objects.equals(graphNode.getLabel(), edge.getNodeRight())).findFirst().get();
            // 解决自循环问题,第一次自循环则允许
            // why? 因为有部分业务逻辑会导致状态不变，但这种情况时因为前面的逻辑处理不一致导致的
            if (nodeCycle && Objects.equals(currentNode.getLabel(), nextNode.getLabel())) {
                nodeCycle = false;
                continue;
            }
            if (Objects.equals(currentNode.getLabel(), nextNode.getLabel())) {
                nodeCycle = true;
            }
            // 解决内循环问题,出现重复路径则不允许
            // why? 因为重复路径意味着这个业务逻辑前面已经验证过了，没必要再进行验证
            checkEdgeCycle(path, currentNode.getLabel(), nextNode.getLabel());
            if (edgeCycle) {
                edgeCycle = false;
                continue;
            }
            depthFirstSearch(nodes, nextNode, endNode, path);
        }
        //当前节点的后向节点全部取完时回滚到上一节点
        rollback(path);
    }

    /**
     * 检查当前路径是否已在结果中
     *
     * @param path 路径
     * @return 在则返回true
     */
    private boolean containsBranch(List<GraphNode> path) {
        for (List<GraphNode> tmp : result) {
            if (tmp.contains(path)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 回滚路径到添加最后一个节点前的状态
     *
     * @param path 路径
     */
    private void rollback(List<GraphNode> path) {
        path.remove(path.size() - 1);
    }

    /**
     * 回滚路径到分叉路径出现时的状态
     *
     * @param path 路径
     */
    private void rollbackToIndex(List<GraphNode> path) {
        while (path.size() > rollback) {
            rollback(path);
        }
    }

    /**
     * 检查是否存在边重复
     *
     * @param path         当前遍历的路径
     * @param currentLabel 当前节点
     * @param nextLabel    后向节点
     */
    private void checkEdgeCycle(@NotNull List<GraphNode> path, String currentLabel, String nextLabel) {
        if (path.size() < SIMPLE_NODE_PATH_SIZE) {
            edgeCycle = false;
        }
        for (int i = 0; i < path.size() - 1; i++) {
            if (Objects.equals(path.get(i).getLabel(), currentLabel) && Objects.equals(path.get(i + 1).getLabel(), nextLabel)) {
                edgeCycle = true;
                return;
            }
        }
        edgeCycle = false;
    }

    /**
     * 初始化内部参数
     */
    private void initCache() {
        result = new ArrayList<>();
        nodeCycle = false;
        edgeCycle = false;
        rollback = 1;
    }

    /**
     * 根据特定模式进行有向图路径遍历
     *
     * @param graph     有向图
     * @param startNode 起始节点
     * @param stopNode  结束节点
     * @param mode      遍历模式，0=DFS，1=BFS
     * @return 全部路径的集合, 如果缺失起始或结束节点，则返回空
     * <p>TODO: 2018/11/06   BFS暂未实现</p>
     */
    private List<List<GraphNode>> search(DirectGraph graph, GraphNode startNode, GraphNode stopNode, int mode) {
        initCache();
        List<GraphNode> path = new ArrayList<>();
        if (null == startNode || null == stopNode) {
            return result;
        }
        if (!graph.getNodes().contains(startNode) || !graph.getNodes().contains(stopNode)) {
            return result;
        }
        if (mode == 0) {
            depthFirstSearch(graph.getNodes(), startNode, stopNode, path);
        } else {
            //TODO BFS是否需要？目前DFS模式也能实现全覆盖
        }
        return result;
    }

    @Override
    public RtRespDto getTestCase(RtReqDto inputData) {
        RtRespDto dto = new RtRespDto();
        try {
            //将对象解析成有向图
            DirectGraph g = DirectGraph.getInstanceFromJson(new JSONObject(inputData));
            //获取起始节点和结束节点
            GraphNode start = g.getNodes().stream().filter(node -> Objects.equals(node.getLabel(), inputData.getStartNodeLabel())).findFirst().get();
            GraphNode stop = g.getNodes().stream().filter(node -> Objects.equals(node.getLabel(), inputData.getStopNodeLabel())).findFirst().get();
            List<List<GraphNode>> resultList = this.search(g, start, stop, inputData.getMode() != 0 ? 1 : 0);
            //结果存储
            List<RtCaseItem> result = new ArrayList<>();
            for (List<GraphNode> nodeCase : resultList) {
                RtCaseItem simpleCase = new RtCaseItem();
                List<RtNodeItem> nodeParameterList = new ArrayList<>();
                for (GraphNode node : nodeCase) {
                    RtNodeItem np = new RtNodeItem();
                    np.setLabel(node.getLabel());
                    np.setWeight(node.getWeight());
                    nodeParameterList.add(np);
                }
                simpleCase.setTestCase(nodeParameterList);
                result.add(simpleCase);
            }
            //TODO 解决权重计算，可以考虑将权重计算放到另一个工具类统一处理
            dto.setResult(result);
            dto.setCount(result.size());
            dto.setStatus(true);
        } catch (JSONException e) {
            dto.setStatus(false);
            dto.setErrMsg(e.getMessage());
        }
        return dto;
    }
}
