import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int H = sc.nextInt();
        int M = sc.nextInt();
        int min = 45;
        
        if(M < min) {
            min -= M;
            M = 60;
            M -= min;
            H -= 1;
            if(H < 0) {
                H = 23;
            }
        }else {
            M -= min;
        }
        
        System.out.println(H + " " + M);
    }
}