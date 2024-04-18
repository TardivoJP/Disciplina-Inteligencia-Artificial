public class MyQueue<T>{
    Node<T> head;
    Node<T> tail;
    int size;

    public MyQueue(){
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void offer(T value){
        Node<T> newNode = new Node<>(value);
        this.size++;

        if(this.tail == null){    
            this.head = newNode;
            this.tail = newNode;
        }else{
            this.tail.next = newNode;
            this.tail = newNode;
        }
    }

    public T poll(){
        if(this.head == null){
            return null;
        }

        Node<T> firstInLine = this.head;
        this.head = this.head.next;
        this.size--;
        if(this.size == 0){
            this.tail = null;
        }
        return firstInLine.data;
    }

    public int size(){
        return size;
    }
}
