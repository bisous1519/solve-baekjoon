import java.util.*;

class Main {
    
    static Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args){
        int N = sc.nextInt();
        
        int Min = Integer.MAX_VALUE;
        int Max = Integer.MIN_VALUE;
        for(int n=0; n<N; n++){
            int num = sc.nextInt();
            Min = Math.min(Min, num);
            Max = Math.max(Max, num);
        }
        
        System.out.println(Min + " " + Max);
    }
}