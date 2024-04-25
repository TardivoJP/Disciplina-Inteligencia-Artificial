import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

//Estrutura de árvore

public class Tree{
    //Parâmetros da classe
    //head para saber onde está o primeiro elemento da fila
    //  utiliza tipo Integer para melhor nomeacao dos nós
    //increment para nomear os nós de forma única
    //maxChildren e minChildren para definir a faixa de filhos por nó
    //steps para contar quantos passos para encontrar um elemento específico
	//displayweights controla se vai mostrar os pesos ou não quando printar a arvore
    Node<Integer> head;
    int increment;
    int maxChildren;
    int minChildren;
    int steps;
    boolean displayWeights;

    //Construtor da estrutura, recebe as faixas de profundidade e filhos para estabelecer o tamanho da árvore
    public Tree(int maxDepth, int minDepth, int maxChildren, int minChildren){
        this.increment = 1;
        this.displayWeights = false;
        this.maxChildren = maxChildren;
        this.minChildren = minChildren;
        //instanciando o primeiro elemento da árvore e chamando a função recursiva
        this.head = new Node<Integer>(this.increment++, 1);
        //A função é chamada com uma profundidade aleatória na faixa delimitada
        generateTree(1, this.head, randomNumber(maxChildren, minDepth));
    }
    
    //Função recursiva para gerar uma árvore aleatória
    public void generateTree(int depth, Node<Integer> node, int height){
        //Critério de parada é chegar na profundidade desejada
        if(depth == height){
            return;
        }
        
        //Cria-se filhos aleatórios dentro da faixa estabelecida para o nó atual
        for(int i=0; i<randomNumber(this.maxChildren, this.minChildren); i++){
            node.addChild(new Node<Integer>(this.increment++, depth+1, randomNumber(1, 15), node));
        }
        
        //A função recursiva é chamada novamente para cada filho com a profundidade N+1
        for(Node<Integer> child : node.children){
            generateTree(depth+1, child, height);
        }
    }
    
    //Função para gerar um número aleatório dentro de uma faixa
    private int randomNumber(int max, int min){
        return (int)(Math.random() * (max - min + 1)) + min;
    }

    //Função de busca em profundidade
    public void depthFirstSearch(){
        //Inicializamos um StringBuilder para printar o resultado posteriormente
        StringBuilder sb = new StringBuilder();
        //Primeira chamada recursiva com o nó head
        depthFirstSearchAux(this.head, sb);
        System.out.println(sb.toString());
    }

    //Função auxiliar para realizar a recursão dos nós em profundidade
    public void depthFirstSearchAux(Node<Integer> node, StringBuilder cur){
        //Branch estético para adicionar flechas após cada número
        if(cur.length() != 0){
            cur.append(" -> ");
        }
        cur.append(node.data);
        
        //Chamada da função novamente para cada filho
        //  isso é propagado no call stack, então nada mais precisa ser feito
        //  ou seja, sempre será resolvido em profundidade pelas chamadas recursivas
        for(Node<Integer> child : node.children){
            depthFirstSearchAux(child, cur);
        }
    }

    //Função de busca em largura
    public void breadthFirstSearch(){
        //Inicializamos nossa estrutura de fila
        MyQueue<Node<Integer>> queue = new MyQueue<>();
        //Inserimos o nó head na fila
        queue.offer(this.head);
        //Inicializamos um StringBuilder para printar o resultado posteriormente
        StringBuilder sb = new StringBuilder();
        //Primeira chamada recursiva com a fila inicial
        breadthFirstSearchAux(queue, sb);
        System.out.println(sb.toString());
    }
    
    //Função auxiliar para realizar a recursão dos nós em largura
    private void breadthFirstSearchAux(MyQueue<Node<Integer>> queue, StringBuilder cur){
        //Se a fila estiver fazia, a árvore foi percorrida em sua totalidade
        if(queue.size() == 0){
            return;
        //Branch estético para adicionar flechas após cada número
        }else{
          if(cur.length() != 0){
            cur.append(" -> ");
          }
        }
        
        //Armazenamos o primeiro da fila em uma variável temporária
        Node<Integer> firstInLine = queue.poll();
        cur.append(firstInLine.data);
        //Adicionamos cada filho no fim da fila
        //  ou seja, pegamos sempre o primeiro elemento, mas adicionamos os filhos no final
        //  desta forma, simulamos a busca em profundidade
        for(Node<Integer> child : firstInLine.children){
            queue.offer(child);
        }
        
        //Realizamos a chamada recursiva novamente até esvaziar a fila
        breadthFirstSearchAux(queue, cur);
    }

    //Função similar a profundiade, mas para mostrar a árvore completa de uma forma mais estética/didática
    public void printTree(){
      //Inicializamos um StringBuilder para printar o resultado posteriormente
        StringBuilder sb = new StringBuilder();
        //Realizamos a primeira chamada recursiva com o nó head
        //  também utilizamos uma variável de profundidade para adicionar espaços de forma heurística
        printTreeAux(this.head, sb, 1);
        System.out.println(sb.toString());
    }

    //Função auxiliar para realizar a recursão dos nós em profundidade
    private void printTreeAux(Node<Integer> node, StringBuilder cur, int depth){
        //Para cada profundidade abaixo da primeira, adicionamos um espaço adicional
        for(int i=1; i<depth; i++){
            cur.append("    ");
        }
		//mostramos os pesos dependendo da booleana que controla
        if(displayWeights){
            cur.append(node.data + " [" + node.weight + "]" + "\n");
        }else{
            cur.append(node.data + "\n");
        }

        //Chamada da função novamente para cada filho com profundidade N+1 para formar os espaços
        for(Node<Integer> child : node.children){
            printTreeAux(child, cur, depth+1);
        }
    }

    //Variação do método de profundidade para encontrar um valor específico
    public void depthFirstSearch(int value){
        StringBuilder sb = new StringBuilder();
        this.steps = 1;
        depthFirstSearchAux(this.head, sb, value);
        System.out.println(sb.toString());
    }

    //Função auxiliar para realizar a recursão dos nós em profundidade
    //  agora é booleana para propagar "true" no callstack caso o valor seja encontrado e parar "mais cedo"
    //  também utilizamos o parâmetro steps da classe para contar quantos passos para encontrar o valor
    public boolean depthFirstSearchAux(Node<Integer> node, StringBuilder cur, int value) {
        if(node.data == value){
            cur.setLength(0);
            appendOptimalPath(cur, value, node);
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

    //Variação do método de largura para encontrar um valor específico
    public void breadthFirstSearch(int value){
        MyQueue<Node<Integer>> queue = new MyQueue<>();
        queue.offer(this.head);
        StringBuilder sb = new StringBuilder();
        this.steps = 1;
        breadthFirstSearchAux(queue, sb, value);
        System.out.println(sb.toString());
    }

    //Função auxiliar para realizar a recursão dos nós em profundidade
    //  agora é booleana para propagar "true" no callstack caso o valor seja encontrado e parar "mais cedo"
    //  também utilizamos o parâmetro steps da classe para contar quantos passos para encontrar o valor
    private boolean breadthFirstSearchAux(MyQueue<Node<Integer>> queue, StringBuilder cur, int value){
        if(queue.size() == 0){
            cur.append("Value not found");
            return false;
        }

        Node<Integer> firstInLine = queue.poll();
        if(firstInLine.data == value){
            appendOptimalPath(cur, value, firstInLine);
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

    //Função definindo a pesquisa com os pesos
    public void weightedSearch(int value){
		//Criamos um arraylist para controlar os nós futuros e realizar o sort com base em seus pesos
        ArrayList<Node<Integer>> arr = new ArrayList<>();
        arr.add(this.head);
        StringBuilder sb = new StringBuilder();
        this.steps = 1;
        weightedSearchAux(arr, sb, value);
        System.out.println(sb.toString());
    }

    //Função auxiliar para realizar a recursão dos nós com base nos pesos
    //  agora é booleana para propagar "true" no callstack caso o valor seja encontrado e parar "mais cedo"
    //  também utilizamos o parâmetro steps da classe para contar quantos passos para encontrar o valor
    private boolean weightedSearchAux(ArrayList<Node<Integer>> arr, StringBuilder cur, int value){
        if(arr.size() == 0){
            cur.setLength(0);
            cur.append("Value not found");
            return false;
        }
		
		//realizamos uma ordenação com base dos pesos de cada nó
        Collections.sort(arr, Comparator.comparingInt(node -> node.weight));
		
		//escolhemos o menor peso que será sempre o primeiro elemento, verificamos e tiramos da lista
        Node<Integer> firstInList = arr.get(0);
        arr.remove(0);

        if(cur.length() != 0){
            cur.append(" -> ");
        }
        cur.append(firstInList.data);
        
        if(firstInList.data == value){
            appendOptimalPath(cur, value, firstInList);
            return true;
        }

        this.steps++;

		//caso contrário adicionamos seus filhos na lista para serem ordenados e testados nas próximas chamadas recursivas
        for(Node<Integer> child : firstInList.children){
            arr.add(child);
        }

        if(weightedSearchAux(arr, cur, value)){
            return true;
        }else{
            return false;
        }
    }

	//função auxiliar para mostrar o resultado na tela para o usuário
	//mostramos quantos passos levou, em qual profundidade e qual seria o caminho ótimo
    private void appendOptimalPath(StringBuilder cur, int value, Node<Integer> node){
        cur.append(String.format("\nValue %d found in depth %d after %d steps\n", value, node.depth, this.steps));
        cur.append(String.format("Optimal Path -> ", value, node.depth, this.steps));

        ArrayList<Integer> way = new ArrayList<>();

        while(node != null){
            way.add(node.data);
            node = node.parent;
        }

        for (int i = way.size()-1; i>=0; i--) {
            cur.append(way.get(i));
            if(i != 0){
                cur.append(" -> ");
            }
        }
    }

}
