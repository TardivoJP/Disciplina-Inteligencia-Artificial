import java.util.ArrayList;
import java.util.HashMap;

public class Class {
    String header;
    boolean numeric;
    ArrayList<String> rowsStr;
    ArrayList<Double> rowsNum;
    HashMap<String, HashMap<String, Integer>> correlations;
    HashMap<String, Integer> totals;

    public Class(String header){
        this.header = header;
        this.numeric = false;
        this.rowsStr = new ArrayList<>();
        this.rowsNum = new ArrayList<>();
        this.correlations = new HashMap<>();
        this.totals = new HashMap<>();
    }

    public void addStr(String val){
        this.rowsStr.add(val);
    }

    public void addNum(Double val){
        this.rowsNum.add(val);
    }
}
