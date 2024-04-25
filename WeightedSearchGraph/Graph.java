import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collections;
import java.util.Comparator;

public class Graph {
  Node head;
  int steps;
  
  public Graph(){
    Node nodeA = new Node("A");
    Node nodeB = new Node("B");
    Node nodeC = new Node("C");
    Node nodeD = new Node("D");
    Node nodeE = new Node("E");
    Node nodeF = new Node("F");
    Node nodeG = new Node("G");
    Node nodeH = new Node("H");
    Node nodeI = new Node("I");
    
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
    
    StringBuilder finalPath = new StringBuilder();
    StringBuilder allSortedListIterations = new StringBuilder();
    finalPath.append(this.head.data);
    this.steps = 1;
    weightedSearchAux(arr, set, finalPath, allSortedListIterations, value);
    System.out.println(allSortedListIterations.toString());
    System.out.println(finalPath.toString());
  }
  
  private boolean weightedSearchAux(ArrayList<Edge> arr, HashSet<String> set, StringBuilder finalPath, StringBuilder allSortedListIterations, String value){
    if(arr.size() == 0){
      finalPath.setLength(0);
      finalPath.append("Value not found");
      return false;
    }

    Collections.sort(arr, Comparator.comparingInt(edge -> edge.weight));
    appendCurList(arr, allSortedListIterations);

    Node firstInList = arr.get(0).node;
    arr.remove(0);
    set.remove(firstInList.data);
    
    finalPath.append(" -> " + firstInList.data);
    this.steps++;
    
    if(firstInList.data == value){
      finalPath.append(String.format("\nValue %s found after %d steps\n", value, this.steps));
      return true;
    }
    
    for(Edge edge : firstInList.edges){
      if(set.contains(edge.node.data)){
        for(int i=0; i<arr.size(); i++){
          if(arr.get(i).node.data == edge.node.data){
            if(arr.get(i).weight > edge.weight){
              arr.remove(i);
              arr.add(edge);
              set.add(edge.node.data);
              break;
            }else{
              break;
            }
          }
        }
      }else{
        arr.add(edge);
        set.add(edge.node.data);
      }
    }
    
    if(weightedSearchAux(arr, set, finalPath, allSortedListIterations, value)){
      return true;
    }else{
      return false;
    }
  }
  
  private void appendCurList(ArrayList<Edge> arr, StringBuilder cur){
    for(int i=0; i<arr.size(); i++){
      cur.append(arr.get(i).node.data + " - [" + arr.get(i).weight + "]");
      if(i<arr.size()-1){
        cur.append(", ");
      }
    }
    cur.append("\n");
  }
}