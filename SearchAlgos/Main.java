public class Main {
    public static void main(String[] args){
        Tree tree = new Tree(4, 3, 3, 1);

        System.out.println("=========================");
        System.out.println("Depth First Search");
        System.out.println("=========================");
        tree.depthFirstSearch();
        tree.depthFirstSearch(5);
        System.out.println("=========================");
        System.out.println("Breadth First Search");
        System.out.println("=========================");
        tree.breadthFirstSearch();
        tree.breadthFirstSearch(5);
        System.out.println("=========================");
        System.out.println("Tree Structure");
        System.out.println("=========================");
        tree.printTree();
    }
}
