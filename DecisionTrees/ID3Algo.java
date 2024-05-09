import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class ID3Algo {
    public static void main(String[] args){
        String[] headers = {"outlook", "temperature", "humidity", "windy"};
        
        String[][] parameters = {{"sunny", "sunny", "overcast", "rainy", "rainy", "rainy", "overcast", "sunny", "sunny", "rainy", "sunny", "overcast", "overcast", "rainy"},
                              {"hot", "hot", "hot", "mild", "cool", "cool", "cool", "mild", "cool", "mild", "mild", "mild", "hot", "mild"},
                              {"high", "high", "high", "high", "normal", "normal", "normal", "high", "normal", "normal", "normal", "high", "normal", "high"},
                              {"false", "true", "false", "false", "false", "true", "true", "false", "false", "false", "true", "true", "false", "true"}};

        String[] decisions = {"no", "no", "yes", "yes", "yes", "no", "yes", "no", "yes", "yes", "yes", "yes", "yes", "no"};

        DecisionTree tree = buildTree(headers, parameters, decisions);
        Node cur = tree.root;
        Scanner s = new Scanner(System.in);

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("Decision Tree Simulation");
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@");

        outerloop:
        while(true){
            System.out.println("Choosing: " + cur.parameter);
            String choice = "";

            while(!cur.children.containsKey(choice)){
                int count = 1;

                for(String key : cur.children.keySet()){
                    System.out.println(count + " - " + key);
                    count++;
                }

                choice = s.nextLine();

                if(!cur.children.containsKey(choice)){
                    System.out.println("Invalid choice.");
                }
            }

            cur = cur.children.get(choice);

            if(cur.children.size() == 1){
                for(String key : cur.children.keySet()){
                    Node child = cur.children.get(key);

                    if(child.decision){
                        if(child.parameter.equals("yes")){
                            System.out.println("You will play!");
                        }else{
                            System.out.println("You will NOT play!");
                        }
                        break outerloop;
                    }else{
                        cur = cur.children.get(key);
                        break;
                    }
                }
            }
        }
        
        s.close();
    }

    public static DecisionTree buildTree(String[] headers, String[][] parameters, String[] decisions){
        HashMap<String, Integer> decisionMap = new HashMap<>();

        for(String decision : decisions){
            decisionMap.compute(decision, (key, value) -> value == null ? 1 : value + 1);
        }

        double totalEntropy = calcEntropy(decisionMap, decisions.length);
        Column[] columns = setupAllParameters(headers, parameters, decisions, totalEntropy);

        int mostChaoticIdx = 0;
        double maxParamEntropy = 0.0;

        for(int i=0; i<columns.length; i++){
            if(columns[i].columnEntropy > maxParamEntropy){
                maxParamEntropy = columns[i].columnEntropy;
                mostChaoticIdx = i;
            }
        }

        DecisionTree tree = new DecisionTree(new Node(columns[mostChaoticIdx].header));

        for(String key : columns[mostChaoticIdx].rowsMap.keySet()){
            Node child = new Node(key);
            tree.root.children.put(key, child);

            HashSet<Integer> indexesToAvoid = new HashSet<>();

            indexesToAvoid.add(mostChaoticIdx);
            calcSubTree(columns[mostChaoticIdx].rowsMap.get(key), indexesToAvoid, child, columns, parameters, decisions);
            indexesToAvoid.remove(mostChaoticIdx);
        }

        printTree(tree.root, 0);

        return tree;
    }

    public static Column[] setupAllParameters(String[] headers, String[][] parameters, String[] decisions, double totalEntropy){
        Column[] columns = new Column[headers.length];

        for(int i=0; i<columns.length; i++){
            columns[i] = new Column(headers[i], parameters[i]);

            for(int j=0; j<columns[i].rows.length; j++){
                if(!columns[i].rowsMap.containsKey(columns[i].rows[j])){
                    columns[i].rowsMap.put(columns[i].rows[j], new Row(columns[i].rows[j]));
                }

                if(!columns[i].rowsMap.get(columns[i].rows[j]).choices.containsKey(decisions[j])){
                    columns[i].rowsMap.get(columns[i].rows[j]).choices.put(decisions[j], 1);
                }else{
                    columns[i].rowsMap.get(columns[i].rows[j]).choices.put(decisions[j], columns[i].rowsMap.get(columns[i].rows[j]).choices.get(decisions[j]) + 1);
                }

                columns[i].rowsMap.get(columns[i].rows[j]).indexes.add(j);
                columns[i].rowsMap.get(columns[i].rows[j]).totalChoices++;
            }

            for(String key : columns[i].rowsMap.keySet()){
                columns[i].rowsMap.get(key).rowEntropy = calcEntropy(columns[i].rowsMap.get(key).choices, columns[i].rowsMap.get(key).totalChoices);
            }


            for(String key : columns[i].rowsMap.keySet()){
                columns[i].columnEntropy += (((double) columns[i].rowsMap.get(key).totalChoices / decisions.length) * columns[i].rowsMap.get(key).rowEntropy);
            }

            columns[i].columnEntropy = totalEntropy - columns[i].columnEntropy;
        }

        return columns;
    }

    public static double calcEntropy(HashMap<String, Integer> choices, int choiceSpace){
        double entropy = 0.0;
        
        for(String key : choices.keySet()){
            if(choices.get(key) == choiceSpace){
                continue;
            }

            double curP = (double) choices.get(key) / choiceSpace;
            double curEntropy = Math.log(curP) / Math.log(2);
            entropy += ((curP * curEntropy) * (-1));
        }

        return entropy;
    }

    public static void calcSubTree(Row row, HashSet<Integer> indexesToAvoid, Node cur, Column[] columns, String[][] parameters, String[] decisions){
        if(row.choices.size() == 1){
            String onlyChoice = "";
            for(String choice : row.choices.keySet()){
                onlyChoice = choice;
            }

            cur.children.put(onlyChoice, new Node(onlyChoice, true));
            return;
        }

        HashMap<String, Row> localRows = new HashMap<>();
        double localMaxEntropy = 0.0;
        int localMostChaoticIdx = 0;

        for(int i=0; i<columns.length; i++){
            if(indexesToAvoid.contains(i)){
                continue;
            }

            HashMap<String, HashMap<String, Integer>> colMap = new HashMap<>();
            ArrayList<Integer> colChoiceSpaces = new ArrayList<>();
            ArrayList<Double> colEntropies = new ArrayList<>();

            for(String key : columns[i].rowsMap.keySet()){
                Row localRow = new Row(key);
                int rowChoiceSpace = 0;

                if(!colMap.containsKey(key)){
                    colMap.put(key, new HashMap<>());
                }

                for(int index : row.indexes){
                    if(parameters[i][index].equals(key)){
                        if(!colMap.get(key).containsKey(decisions[index])){
                            colMap.get(key).put(decisions[index], 1);
                            localRow.choices.put(decisions[index], 1);
                        }else{
                            colMap.get(key).put(decisions[index], colMap.get(key).get(decisions[index]) + 1);
                            localRow.choices.put(decisions[index], localRow.choices.get(decisions[index]) + 1);
                        }
                        
                        rowChoiceSpace++;
                    }
                }

                double localEntropy = calcEntropy(colMap.get(key), rowChoiceSpace);

                colChoiceSpaces.add(rowChoiceSpace);
                colEntropies.add(localEntropy);
                localRow.name = key;
                localRow.totalChoices = rowChoiceSpace;
                localRow.rowEntropy = localEntropy;
                localRows.put(key, localRow);
            }

            double curEntropyTotal = 0.0;

            for(int j=0; j<colChoiceSpaces.size(); j++){
                curEntropyTotal += (((double) colChoiceSpaces.get(j) / row.totalChoices) * colEntropies.get(j));
            }

            curEntropyTotal = row.rowEntropy - curEntropyTotal;

            if(curEntropyTotal > localMaxEntropy){
                localMaxEntropy = curEntropyTotal;
                localMostChaoticIdx = i;
            }
        }

        Node intermediate = new Node(columns[localMostChaoticIdx].header);
        
        cur.children.put(columns[localMostChaoticIdx].header, intermediate);

        for(String key : columns[localMostChaoticIdx].rowsMap.keySet()){
            Node child = new Node(key);
            intermediate.children.put(key, child);

            indexesToAvoid.add(localMostChaoticIdx);
            calcSubTree(localRows.get(key), indexesToAvoid, child, columns, parameters, decisions);
            indexesToAvoid.remove(localMostChaoticIdx);
        }
    }

    public static class Column{
        String header;
        String[] rows;
        HashMap<String, Row> rowsMap;
        double columnEntropy;

        public Column(String header, String[] rows){
            this.header = header;
            this.rows = rows;
            this.rowsMap = new HashMap<>();
            this.columnEntropy = 0.0;
        }
    }

    public static class Row{
        String name;
        int totalChoices;
        ArrayList<Integer> indexes;
        HashMap<String, Integer> choices;
        double rowEntropy;

        public Row(String name){
            this.name = name;
            this.totalChoices = 0;
            this.indexes = new ArrayList<>();
            this.choices = new HashMap<>();
            this.rowEntropy = 0.0;
        }
    }

    public static class DecisionTree{
        Node root;

        public DecisionTree(Node root){
            this.root = root;
        }
    }

    public static class Node{
        String parameter;
        boolean decision;
        HashMap<String, Node> children;

        public Node(String parameter){
            this.parameter = parameter;
            this.decision = false;
            this.children = new HashMap<>();
        }

        public Node(String parameter, boolean decision){
            this.parameter = parameter;
            this.decision = decision;
            this.children = new HashMap<>();
        }
    }

    public static void printTree(Node root, int depth){
        for(int i=0; i<depth; i++){
            System.out.print(" ");
        }
        
        System.out.print(depth + " : ");
        System.out.print(root.parameter);    
        System.out.println();

        for(String child : root.children.keySet()){
            printTree(root.children.get(child), depth + 1);
        }
    }
}
