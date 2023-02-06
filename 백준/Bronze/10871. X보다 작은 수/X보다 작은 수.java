import java.util.Scanner;
public class Main {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        int N = sc.nextInt();
        int X = sc.nextInt();
        for(int n=0; n<N; n++) {
            int a = sc.nextInt();
            if(a < X) {
                System.out.print(a + " ");
            }
        }
    }
}