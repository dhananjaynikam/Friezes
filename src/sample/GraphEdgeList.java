package sample;

import java.util.ArrayList;

public class GraphEdgeList {

    protected GraphEdgeNode start;
    protected GraphEdgeNode end;
    protected int size;

    GraphEdgeList(){
        start = null;
        end = null;
        size = 0;
    }

    public static void add(ArrayList<GraphEdgeList> edgeList, int node1Data, int node2Data){
        GraphEdgeNode node1 = new GraphEdgeNode(node1Data);
        GraphEdgeNode node2 = new GraphEdgeNode(node2Data);
        node1.setDirectConnection(node2);
        node2.setDirectConnection(node1);

        if (edgeList.get(node1Data).getSize() == 0 ) {
            GraphEdgeList vertex1 = new GraphEdgeList();
            vertex1.insertAtStart(node2);
            edgeList.set(node1Data, vertex1);
        } else {
            GraphEdgeList list = edgeList.get(node1Data);
            list.insertAtStart(node2);
        }
        if (edgeList.get(node2Data).getSize() == 0) {
            GraphEdgeList vertex2 = new GraphEdgeList();
            vertex2.insertAtStart(node1);
            edgeList.set(node2Data, vertex2);
        } else {
            GraphEdgeList list = edgeList.get(node2Data);
            list.insertAtStart(node1);
        }
    }

    private void insertAtStart(GraphEdgeNode node){
        if(start == null){
            start = node;
            end = node;
        } else {
            node.setNextConnection(start);
            start.setPrevConnection(node);
            start = node;
        }
        size ++;
    }

    private int getSize() {
        return this.size;
    }
}
