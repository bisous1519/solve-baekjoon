import java.util.Scanner;

public class Main {
    
    static Scanner sc = new Scanner(System.in);
    static StringBuilder output = new StringBuilder();
    
    public static void main(String[] args) {
        int N = sc.nextInt();
        
        for(int i=1; i<=9; i++) {
            output.append(N + " * " + i + " = " + (N*i) + "\n");
        }
        
        System.out.println(output);
    }
}