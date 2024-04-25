import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collections;
import java.util.Comparator;

public class Graph {
    Node<String> head;
    int steps;
    
    public Graph(){
        Node<String> nodeA = new Node("A");
        Node<String> nodeB = new Node("B");
        Node<String> nodeC = new Node("C");
        Node<String> nodeD = new Node("D");
        Node<String> nodeE = new Node("E");
        Node<String> nodeF = new Node("F");
        Node<String> nodeG = new Node("G");
        Node<String> nodeH = new Node("H");
        Node<String> nodeI = new Node("I");
        
        nodeA.addTransition(new Edge(nodeB, 5));
        nodeA.addTransition(new Edge(nodeC, 9));
        nodeA.addTransition(new Edge(nodeD, 1));
        
        nodeB.addTransition(new Edge(nodeI, 3));
        
        nodeC.addTransition(new Edge(nodeE, 13));
        nodeC.addTransition(new Edge(nodeF, 2));
        nodeC.addTransition(new Edge(nodeG, 10));
        
        nodeD.addTransition(new Edge(nodeC, 2));
        nodeD.addTransition(new Edge(nodeH, 20));
        

        this.head = nodeA;
    }
    
    public void weightedSearch(String value){
        ArrayList<Edge> arr = new ArrayList<>();
        HashSet<String> set = new HashSet<>();
        
        
        for(int i=0; i<this.head.edges.size(); i++){
          arr.add(this.head.edges.get(i));
          set.add(this.head.edges.get(i).node.data);
        }
        
        printCurList(arr);
        
        StringBuilder sb = new StringBuilder();
        sb.append("\n"+this.head.data);
        this.steps = 1;
        weightedSearchAux(arr, set, sb, value);
        System.out.println(sb.toString());
    }
    
    private boolean weightedSearchAux(ArrayList<Edge> arr, HashSet<String> set, StringBuilder cur, String value){
        if(arr.size() == 0){
            cur.setLength(0);
            cur.append("Value not found");
            return false;
        }
		
        Collections.sort(arr, Comparator.comparingInt(edge -> edge.weight));
		
        Node<String> firstInList = arr.get(0).node;
        arr.remove(0);
        set.remove(firstInList.data);
        
        cur.append(" -> " + firstInList.data);
        this.steps++;
        
        if(firstInList.data == value){
            cur.append(String.format("\nValue %s found after %d steps\n", value, this.steps));
            return true;
        }
        

        for(Edge edge : firstInList.edges){
          int index = 0;
          boolean found = false;
          
          if(set.contains(edge.node.data)){
            for(int i=0; i<arr.size(); i++){
              if(arr.get(i).node.data == edge.node.data){
                if(arr.get(i).weight > edge.weight){
                  index = i;
                  found = true;
                  break;
                }
              }
            }
          }
          
          if(found){
            arr.remove(index);
          }
          
          arr.add(edge);
          set.add(edge.node.data);
        }
        
        printCurList(arr);

        if(weightedSearchAux(arr, set, cur, value)){
            return true;
        }else{
            return false;
        }
    }
    
    private void printCurList(ArrayList<Edge> arr){
        System.out.println();
      
        for(int i=0; i<arr.size(); i++){
          System.out.print(arr.get(i).node.data + " - [" + arr.get(i).weight + "]");
          if(i<arr.size()-1){
            System.out.print(", ");
          }
        }
        
        System.out.println();
    }
}