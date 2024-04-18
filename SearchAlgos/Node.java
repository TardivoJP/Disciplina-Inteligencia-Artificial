import java.util.ArrayList;

public class Node<T>{
    T data;
    ArrayList<Node<T>> children;
    Node<T> next;

    public Node(T data){
        this.data = data;
        this.children = new ArrayList<>();
        this.next = null;
    }

    public void addChild(Node<T> child){
        this.children.add(child);
    }
}
