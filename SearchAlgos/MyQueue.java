import java.util.Random;

//Estrutura de fila
//  utiliza tipo genérico <T> para funcionar com qualquer tipo de dado
//  no caso da árvore teremos MyQueue<Node<Integer>>

public class MyQueue<T>{
    //Parâmetros da classe
    //head para saber onde está o primeiro elemento da fila
    //tail para saber onde está o último elemento da fila
    //  e também realizar inserções rapidamente no fim da fila
    //size para saber o tamanho atual da fila e se ela está vazia ou não
    Node<T> head;
    Node<T> tail;
    int size;

    //Construtor da estrutura inicializando os valores padrão vazios
    public MyQueue(){
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
    
    //Função para inserir um valor do tipo T no fim da fila
    public void offer(T value){
        //Instanciamos um novo Node que irá armazenar o valor inserido
        Node<T> newNode = new Node<>(value);
        //Incrementamos o tamanho atual da fila
        this.size++;
        
        //Se o tail estiver nulo, significa que é o primeiro elemento da fila
        //  então setamos o novo Node como head e tail
        if(this.tail == null){    
            this.head = newNode;
            this.tail = newNode;
        //Caso contrário temos que fazer uma simples permuta
        //  o tail atual aponta para o novo elemento
        //  o novo elemento transforma-se no tail da fila
        }else{
            this.tail.next = newNode;
            this.tail = newNode;
        }
    }

    //Função para remover e retornar o primeiro elemento da fila
    public T poll(){
        //Se o head é null, a fila está vazia então retornamos null
        if(this.head == null){
            return null;
        }
        
        //Aramzenamos o primeiro elemento em uma variável temporária
        Node<T> firstInLine = this.head;
        //Realizamos a permuta para que o próximo/segundo elemento seja o novo head
        this.head = this.head.next;
        //Decrementamos o tamanho da fila
        this.size--;
        //Se o tamanho for 0, então a fila ficou vazia, então setamos o tail para null
        if(this.size == 0){
            this.tail = null;
        }
        //Finalmente retornamos o elemento desejado
        return firstInLine.data;
    }

    //Função que retorna o tamanho atual da fila
    public int size(){
        return this.size;
    }

    public void clearQueue(){
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
}
