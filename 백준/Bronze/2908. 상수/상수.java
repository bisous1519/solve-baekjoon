import java.util.Scanner;
public class Main {
    static Scanner sc = new Scanner(System.in);
    public static int newNumber(int a) {
        int newN = 0;
        for(int i=1; i<=3; i++) {
            newN *= 10;
            newN += a % 10;
            a /= 10;
        }
        return newN;
    }
    public static void main(String[] args) {
        int a = sc.nextInt();
        int b = sc.nextInt();
        
        a = newNumber(a);
        b = newNumber(b);
        System.out.println(Math.max(a, b));
    }
}