import java.util.ArrayList;

//Estrutura de nó
//  utiliza tipo genérico <T> para funcionar com qualquer tipo de dado
//  serve para dois propósitos, fila e árvore

public class Node<T>{
    //Parâmetros da classe
    //data armazena um dado de qualquer tipo na estrutura de nó
    //depth armazena a profundidade do nó na estrutura da árvore
    //  esse parâmetro não é utilizado na estrutura de fila
    //children armazena os filhos deste nó para modelar a estrutura de árvore
    //  caso fosse um grafo mais generalizado poderiamos utilizar um HashSet para armazenar as adjacências/conexões
    //  esse parâmetro não é utilizado na estrutura de fila
    //next para saber qual é o próximo elemento na lista ligada da fila
    //  esse parâmetro não é utilizado na estrutura de árvore
    T data;
    int depth;
    ArrayList<Node<T>> children;
    Node<T> next;

    //Construtor da estrutura inicializando o nó com algum tipo de dado e conexões vazias
    public Node(T data){
        this.data = data;
        this.depth = 0;
        this.children = new ArrayList<>();
        this.next = null;
    }
    
    //Construtor alternativo com profundidade passada como argumento
    public Node(T data, int depth){
        this.data = data;
        this.depth = depth;
        this.children = new ArrayList<>();
        this.next = null;
    }

    //Função para adicionar um filho para sua lista, criando a hierarquia necessária para modelar árvores
    public void addChild(Node<T> child){
        this.children.add(child);
    }
}
