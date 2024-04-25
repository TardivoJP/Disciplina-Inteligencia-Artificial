import java.util.Scanner;

//Classe main para instanciar e executar os métodos
public class Main {
    public static void main(String[] args){
        //Árvore padrão gerada aleatóriamente
        Tree tree = new Tree(4, 3, 3, 1);
        
        //Operando para o menu
        int op = 0;
        
        //Scanner para receber os inputs
        Scanner sc = new Scanner(System.in);
        
        //Loop do menu
        do{
          System.out.println("=========================");
          System.out.println("Welcome!");
          System.out.println("=========================");
          //Gerar nova árvore com parametros customizados
          System.out.println("1. Generate new tree");
          //Ver árvore atual
          System.out.println("2. View current tree");
          //mostrar ou não mostrar os pesos dos nós
          System.out.println("3. Toggle weights on/off");
          System.out.println("=========================");
          //busca em profundidade
          System.out.println("DEPTH FIRST SEARCH");
          //percorrer a árvore completa
          System.out.println("4. Full tree");
          //procurar um valor específico
          System.out.println("5. Find specific value");
          System.out.println("=========================");
          //busca em largura
          System.out.println("BREADTH FIRST SEARCH");
          //percorrer a árvore completa
          System.out.println("6. Full tree");
          //procurar um valor específico
          System.out.println("7. Find specific value");
          System.out.println("=========================");
          System.out.println("WEIGHTED SEARCH");
          //procurar um valor específico
          System.out.println("8. Find specific value");
          System.out.println("=========================");
          //terminar o programa
          System.out.println("9. Exit");
          System.out.println("=========================");
          
          //switch com a execução dos operandos
          op = sc.nextInt();
          int value = 0;
          switch(op){
            case 1:
              //recebe os valores de parâmetro e sobrescreve uma nova árvore na variável
              System.out.println("Insert the maximum height for the tree: ");
              int maxDepth = sc.nextInt();
              System.out.println("Insert the minimum height for the tree: ");
              int minDepth = sc.nextInt();
              System.out.println("Insert the maximum number of children per node: ");
              int maxChildren = sc.nextInt();
              System.out.println("Insert the minimum number of children per node: ");
              int minChildren = sc.nextInt();
              tree = new Tree(maxDepth, minDepth, maxChildren, minChildren);
              System.out.println("New tree generated!");
              break;
            case 2:
              System.out.println("=========================");
              System.out.println("Tree Structure");
              System.out.println("=========================");
              tree.printTree();
              break;
            case 3:
              System.out.println("=========================");
              System.out.println("Depth First Search");
              System.out.println("=========================");
              tree.displayWeights = true;
              break;
            case 4:
              System.out.println("=========================");
              System.out.println("Depth First Search");
              System.out.println("=========================");
              tree.depthFirstSearch();
              break;
            case 5:
              System.out.println("=========================");
              System.out.println("Depth First Search");
              System.out.println("=========================");
              System.out.println("Insert the value to search in the tree: ");
              value = sc.nextInt();
              tree.depthFirstSearch(value);
              break;
            case 6:
              System.out.println("=========================");
              System.out.println("Breadth First Search");
              System.out.println("=========================");
              tree.breadthFirstSearch();
              break;
            case 7:
              System.out.println("=========================");
              System.out.println("Breadth First Search");
              System.out.println("=========================");
              System.out.println("Insert the value to search in the tree: ");
              value = sc.nextInt();
              tree.breadthFirstSearch(value);
              break;
            case 8:
              System.out.println("=========================");
              System.out.println("Weighted Search");
              System.out.println("=========================");
              System.out.println("Insert the value to search in the tree: ");
              value = sc.nextInt();
              tree.weightedSearch(value);
              break;
            case 9:
              System.out.println("See you next time!");
              break;
            default:
              System.out.println("Invalid operand!");
              break;
          }
        //condição de parada do menu, selecionar o valor da opção de saída
        }while(op != 9);
        
        //fechando o scanner
        sc.close();
    }
}
