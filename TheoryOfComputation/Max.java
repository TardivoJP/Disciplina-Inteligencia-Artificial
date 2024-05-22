import java.util.Random;

public class Max {
    public static void main(String[] args){
        Random rand = new Random();

        long[] bigArr = new long[100000];

        for(int i=0; i<bigArr.length; i++){
            bigArr[i] = rand.nextLong();
        }

        System.out.println(findMax(bigArr, 0, bigArr.length-1));
    }

    public static long findMax(long[] arr, int left, int right){
        if(left == right){
            return arr[left];
        }
        
        if(left + 1 == right){
            return Math.max(arr[left], arr[right]);
        }

        int mid = (left + right) / 2;
        long maxLeft = findMax(arr, left, mid);
        long maxRight = findMax(arr, mid + 1, right);

        return Math.max(maxLeft, maxRight);
    }
}
