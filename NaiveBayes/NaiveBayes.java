import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class NaiveBayes {
    public static void main(String[] args){
        String filePath = "dados.csv";
        String line;
        ArrayList<Class> classes = new ArrayList<>();
        
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            boolean isFirstLine = true;

            while((line = br.readLine()) != null){
                String[] values = line.split(",");

                if(isFirstLine){
                    for (String header : values) {
                        classes.add(new Class(header));
                    }
                    isFirstLine = false;
                }else{
                    for(int i=0; i<values.length; i++){
                        String value = values[i];
                        Class cls = classes.get(i);

                        try{
                            double num = Double.parseDouble(value);
                            cls.addNum(num);
                            cls.numeric = true;
                        }catch(NumberFormatException e){
                            cls.addStr(value);
                        }

                        cls.correlations.put(value, new HashMap<>());
                    }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }

        Class pivot = classes.get(classes.size() - 1);

        for(Class cls : classes){
            if(!cls.numeric){
                for(int i=0; i<cls.rowsStr.size(); i++){
                    if(cls.correlations.get(cls.rowsStr.get(i)).containsKey(pivot.rowsStr.get(i))){
                        cls.correlations.get(cls.rowsStr.get(i)).put(pivot.rowsStr.get(i), cls.correlations.get(cls.rowsStr.get(i)).get(pivot.rowsStr.get(i)) + 1);
                    }else{
                        cls.correlations.get(cls.rowsStr.get(i)).put(pivot.rowsStr.get(i), 1);
                    }

                    if(cls.totals.containsKey(pivot.rowsStr.get(i))){
                        cls.totals.put(pivot.rowsStr.get(i), cls.totals.get(pivot.rowsStr.get(i)) + 1);
                    }else{
                        cls.totals.put(pivot.rowsStr.get(i), 1);
                    }
                }
            }
        }

        Scanner s = new Scanner(System.in);

        ArrayList<String> pivotParams = new ArrayList<>();
        int totalParams = 0;
        for(String key : pivot.correlations.keySet()){
            pivotParams.add(key);
            totalParams += pivot.correlations.get(key).get(key);
        }

        ArrayList<ArrayList<LaplaceanValue>> resultValues = new ArrayList<>();
        for(int i=0; i<pivot.correlations.size(); i++){
            resultValues.add(new ArrayList<>());
        }

        boolean laplaceanModifier = false;

        double[] result = new double[pivot.correlations.size()];

        for(int i=0; i<classes.size()-1; i++){
            Class cur = classes.get(i);

            System.out.println(cur.header+": ");

            for(String key : cur.correlations.keySet()){
                System.out.println(key);
            }
            
            String attributeChoice = "";
            while(!cur.correlations.containsKey(attributeChoice)){
                attributeChoice = s.nextLine();
                if(!cur.correlations.containsKey(attributeChoice)){
                    System.out.println("Escolha um parametro existente!");
                }
            }

            System.out.println();

            for(int j=0; j<result.length; j++){
                if(!cur.correlations.get(attributeChoice).containsKey(pivotParams.get(j))){
                    resultValues.get(j).add(new LaplaceanValue(0, cur.totals.get(pivotParams.get(j))));
                    laplaceanModifier = true;
                    continue;
                }

                resultValues.get(j).add(new LaplaceanValue(cur.correlations.get(attributeChoice).get(pivotParams.get(j)), cur.totals.get(pivotParams.get(j))));
            }
        }

        s.close();


        double totalProbabilities = 0;
        for(int i=0; i<resultValues.size(); i++){
            int alpha = 0;

            if(laplaceanModifier){
                alpha = resultValues.get(i).size() + 1;
            }
            
            System.out.println(alpha);

            for(int j=0; j<resultValues.get(i).size(); j++){
                if(laplaceanModifier){
                    resultValues.get(i).get(j).numerator++;
                }

                resultValues.get(i).get(j).denominator += alpha;

                if(j == 0){
                    result[i] = resultValues.get(i).get(j).calcValue();
                }else{
                    result[i] *= resultValues.get(i).get(j).calcValue();
                }
            }

            result[i] *= (double) pivot.correlations.get(pivotParams.get(i)).get(pivotParams.get(i)) / totalParams;
            totalProbabilities += result[i];

            System.out.println("P("+pivotParams.get(i)+") = "+result[i]);
        }

        System.out.println("\nNormalizando...\n");

        for(int i=0; i<result.length; i++){
            result[i] /= (double) totalProbabilities;
            result[i] *= 100;

            System.out.printf("P(%s) = %.2f%%\n", pivotParams.get(i), result[i]);
        }
    }
}
