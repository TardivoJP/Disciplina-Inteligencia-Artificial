public class Tree{
    Node<Integer> head;
    int increment;
    int maxChildren;
    int minChildren;
    int steps;

    public Tree(int maxDepth, int minDepth, int maxChildren, int minChildren){
        this.increment = 1;
        this.maxChildren = maxChildren;
        this.minChildren = minChildren;
        this.head = new Node<Integer>(this.increment++);
        generateTree(1, this.head, randomNumber(maxChildren, minDepth));
    }

    public void generateTree(int depth, Node<Integer> node, int height){
        if(depth == height){
            return;
        }

        for(int i=0; i<randomNumber(this.maxChildren, this.minChildren); i++){
            node.addChild(new Node<Integer>(this.increment++));
        }

        for(Node<Integer> child : node.children){
            generateTree(depth+1, child, height);
        }
    }

    private int randomNumber(int max, int min){
        return (int)(Math.random() * (max - min + 1)) + min;
    }

    public void depthFirstSearch(){
        StringBuilder sb = new StringBuilder();
        depthFirstSearchAux(this.head, sb);
        System.out.println(sb.toString());
    }

    public void depthFirstSearchAux(Node<Integer> node, StringBuilder cur) {
        if(cur.length() != 0) {
            cur.append(" -> ");
        }
        cur.append(node.data);
    
        for(Node<Integer> child : node.children) {
            depthFirstSearchAux(child, cur);
        }
    }

    public void breadthFirstSearch(){
        MyQueue<Node<Integer>> queue = new MyQueue<>();
        queue.offer(this.head);
        StringBuilder sb = new StringBuilder();
        breadthFirstSearchAux(queue, sb);
        System.out.println(sb.toString());
    }

    private void breadthFirstSearchAux(MyQueue<Node<Integer>> queue, StringBuilder cur){
        if(queue.size() == 0){
            return;
        }else{
            if(cur.length() != 0){
                cur.append(" -> ");
            }
        }

        Node<Integer> firstInLine = queue.poll();
        cur.append(firstInLine.data);
        for(Node<Integer> child : firstInLine.children){
            queue.offer(child);
        }

        breadthFirstSearchAux(queue, cur);
    }

    public void printTree(){
        printNode(this.head, 1);
    }

    private void printNode(Node<Integer> node, int depth){
        StringBuilder sb = new StringBuilder();
        for(int i=1; i<depth; i++){
            sb.append("    ");
        }
        sb.append(node.data);
        System.out.println(sb.toString());

        for(Node<Integer> child : node.children){
            printNode(child, depth+1);
        }
    }

    public void depthFirstSearch(int value){
        StringBuilder sb = new StringBuilder();
        this.steps = 1;
        depthFirstSearchAux(this.head, sb, value);
        System.out.println(sb.toString());
    }

    public boolean depthFirstSearchAux(Node<Integer> node, StringBuilder cur, int value) {
        if(node.data == value){
            cur.setLength(0);
            cur.append(String.format("Value %d found after %d steps", value, this.steps));
            return true;
        }

        this.steps++;
    
        for(Node<Integer> child : node.children) {
            if(depthFirstSearchAux(child, cur, value)){
                return true;
            }
        }

        cur.setLength(0);
        cur.append("Value not found");
        return false;
    }

    public void breadthFirstSearch(int value){
        MyQueue<Node<Integer>> queue = new MyQueue<>();
        queue.offer(this.head);
        StringBuilder sb = new StringBuilder();
        this.steps = 1;
        breadthFirstSearchAux(queue, sb, value);
        System.out.println(sb.toString());
    }

    private boolean breadthFirstSearchAux(MyQueue<Node<Integer>> queue, StringBuilder cur, int value){
        if(queue.size() == 0){
            cur.append("Value not found");
            return false;
        }

        Node<Integer> firstInLine = queue.poll();
        if(firstInLine.data == value){
            cur.append(String.format("Value %d found after %d steps", value, this.steps));
            return true;
        }

        this.steps++;

        for(Node<Integer> child : firstInLine.children){
            queue.offer(child);
        }

        if(breadthFirstSearchAux(queue, cur, value)){
            return true;
        }else{
            return false;
        }
    }

}