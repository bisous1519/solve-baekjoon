import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int a = sc.nextInt();
		int b = sc.nextInt();
		int t = a - b;
		for(int i=3; i<=8; i++) {
			int c = sc.nextInt();
			if(t != b - c) {
				System.out.println("mixed");
				return;
			}
			b = c;
		}
		if(t != 1) {
			System.out.println("ascending");
		}else {
			System.out.println("descending");
		}
	}
}