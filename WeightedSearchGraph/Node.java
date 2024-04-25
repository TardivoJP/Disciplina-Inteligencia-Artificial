import java.util.ArrayList;

public class Node{
    String data;
    ArrayList<Edge> edges;

    public Node(String data){
        this.data = data;
        this.edges = new ArrayList<>();
    }
    
    public void addTransition(Edge edge){
        this.edges.add(edge);
    }
}