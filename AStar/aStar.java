import java.util.*;
 
class Main {
 
  public static class Node {
	String name;
    boolean visited;
	double x;
	double y;
	double weight;
	ArrayList<Node> edges;
	Node parent;
 
	public Node(String name, double x, double y){
  	this.name = name;
    this.visited = false;
  	this.x = x;
  	this.y = y;
  	this.edges = new ArrayList<>();
  	this.parent = null;
	}
  }
 
  public static void aStar(Node start, Node goal){
	double heuristic =  Math.abs(start.x - goal.x) + Math.abs(start.y - goal.y);
    
	goal.weight = 0;
	start.weight = heuristic;
    
	ArrayList<Node> openList = new ArrayList<>();
    
	openList.add(start);
    
	aStarAux(goal, openList);
    
	StringBuilder sb = new StringBuilder();
    
	Node cur = goal;
	while(cur.parent != null){
  	sb.insert(0, cur.name + " ");
  	cur = cur.parent;
	}
	sb.insert(0, cur.name + " ");
    
	System.out.println(sb.toString());
  }
 
  public static boolean aStarAux(Node goal, ArrayList<Node> openList){
	if(openList.size() == 0){
        return false;
	}
    
	for(int i=0; i<openList.size(); i++){
        System.out.print(openList.get(i).name + " [" + openList.get(i).weight + "] ");
	}
    
	System.out.println();
    
	Node current = openList.get(0);
	if(current == goal){
        return true;
	}
    
	openList.remove(0);
    current.visited = true;
    
    
	for(Node edge : current.edges){
        if(edge.visited){
            continue;
        }
        
        double heuristic =  Math.abs(edge.x - goal.x) + Math.abs(edge.y - goal.y);
        double distance =  Math.abs(current.x - edge.x) + Math.abs(current.y - edge.y);
        
        edge.weight = heuristic + distance;
        edge.parent = current;
        openList.add(edge);
	}
    
	Collections.sort(openList, Comparator.comparingDouble(node -> node.weight));
    
	if(aStarAux(goal, openList)){
  	return true;
	}
    
	return false;
  }
 
  public static void main(String[] args) {
	HashMap<String, Node> nodeMap = new HashMap<>();
 
	nodeMap.put("Milão", new Node("Milão", 45.463738, 9.188545));
	nodeMap.put("Bérgamo", new Node("Bérgamo", 45.694197,9.670746));
	nodeMap.put("Turim", new Node("Turim", 45.070312,7.686856));
	nodeMap.put("Verona", new Node("Verona", 45.438366,10.991714));
	nodeMap.put("Parma", new Node("Parma", 44.801479,10.327991));
	nodeMap.put("Génova", new Node("Génova", 44.407145,8.934738));
	nodeMap.put("Placência", new Node("Placência", 45.052642,9.693688));
	nodeMap.put("Módena", new Node("Módena", 44.647128,10.925227));
	nodeMap.put("Bolonha", new Node("Bolonha", 44.369331,11.252379));
	nodeMap.put("Alexandria", new Node("Alexandria", 44.907273,8.61168));
	nodeMap.put("Novara", new Node("Novara", 45.446775,8.61687));
 
 
	nodeMap.get("Milão").edges.add(nodeMap.get("Bérgamo"));
	nodeMap.get("Milão").edges.add(nodeMap.get("Placência"));
	nodeMap.get("Milão").edges.add(nodeMap.get("Alexandria"));
	nodeMap.get("Milão").edges.add(nodeMap.get("Novara"));
 
 
	nodeMap.get("Bérgamo").edges.add(nodeMap.get("Milão"));
	nodeMap.get("Bérgamo").edges.add(nodeMap.get("Verona"));
 
 
	nodeMap.get("Turim").edges.add(nodeMap.get("Novara"));
	nodeMap.get("Turim").edges.add(nodeMap.get("Alexandria"));
 
 
	nodeMap.get("Verona").edges.add(nodeMap.get("Bérgamo"));
	nodeMap.get("Verona").edges.add(nodeMap.get("Módena"));
 
 
	nodeMap.get("Parma").edges.add(nodeMap.get("Módena"));
	nodeMap.get("Parma").edges.add(nodeMap.get("Placência"));
 
 
	nodeMap.get("Génova").edges.add(nodeMap.get("Alexandria"));
 
 
	nodeMap.get("Placência").edges.add(nodeMap.get("Milão"));
	nodeMap.get("Placência").edges.add(nodeMap.get("Alexandria"));
	nodeMap.get("Placência").edges.add(nodeMap.get("Parma"));
 
 
	nodeMap.get("Módena").edges.add(nodeMap.get("Verona"));
	nodeMap.get("Módena").edges.add(nodeMap.get("Parma"));
	nodeMap.get("Módena").edges.add(nodeMap.get("Bolonha"));
 
 
	nodeMap.get("Bolonha").edges.add(nodeMap.get("Módena"));
 
 
	nodeMap.get("Alexandria").edges.add(nodeMap.get("Turim"));
	nodeMap.get("Alexandria").edges.add(nodeMap.get("Placência"));
	nodeMap.get("Alexandria").edges.add(nodeMap.get("Novara"));
	nodeMap.get("Alexandria").edges.add(nodeMap.get("Génova"));
 
 
	nodeMap.get("Novara").edges.add(nodeMap.get("Turim"));
	nodeMap.get("Novara").edges.add(nodeMap.get("Alexandria"));
	nodeMap.get("Novara").edges.add(nodeMap.get("Milão"));
    
    
	aStar(nodeMap.get("Milão"), nodeMap.get("Bolonha"));
  }
}
