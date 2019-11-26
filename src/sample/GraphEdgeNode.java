package sample;

public class GraphEdgeNode {

    private GraphEdgeNode directConnection;
    private GraphEdgeNode nextConnection;
    private GraphEdgeNode prevConnection;
    private int id;
    private int data;

    GraphEdgeNode(int id){
        this.id = id;
        directConnection = null;
        nextConnection = null;
        prevConnection = null;
        data = -1;
    }

    public void setData(int data){
        this.data = data;
    }

    public void setDirectConnection(GraphEdgeNode directConnection) {
        this.directConnection = directConnection;
    }

    public void setNextConnection(GraphEdgeNode nextConnection){
        this.nextConnection = nextConnection;
    }

    public void setPrevConnection(GraphEdgeNode prevConnection) {
        this.prevConnection = prevConnection;
    }

    public GraphEdgeNode getPrevConnection() {
        return this.prevConnection;
    }

    public GraphEdgeNode getNextConnection() {
        return this.nextConnection;
    }

    public GraphEdgeNode getDirectConnection(){
        return this.directConnection;
    }

    public int getData(){
        return this.data;
    }

    public int getId(){
        return this.id;
    }
}
