import java.util.Random;

public class Power {
    public static void main(String[] args){
        Random rand = new Random();

        int x = 2;
        int pow = rand.nextInt(20);


        System.out.println("2^"+pow+" = "+power(x, pow));
    }

    public static double power(int x, int pow){
        if(pow == 0){
            return 1;
        }

        if(pow < 0){
            return 1.0 / power(x, pow * -1);
        }

        double half = power(x, pow / 2);
        
        if(pow % 2 == 0){
            return half * half;
        }else{
            return x * half * half;
        }
    }
}
