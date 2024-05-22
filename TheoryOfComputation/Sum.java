import java.util.Random;

public class Sum {
    public static void main(String[] args){
        Random rand = new Random();

        int[] arr = new int[100];

        for(int i=0; i<arr.length; i++){
            arr[i] = rand.nextInt();
        }

        System.out.println(sumOfElements(arr, 0));
    }

    public static long sumOfElements(int[] arr, int start){
        if(start >= arr.length){
            return 0;
        }

        return arr[start] + sumOfElements(arr, start + 1);
    }
}
