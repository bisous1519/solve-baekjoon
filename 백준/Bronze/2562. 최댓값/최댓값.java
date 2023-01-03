import java.util.Scanner;

public class Main {
	
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		int Max = Integer.MIN_VALUE;
		int nth = 0;
		for(int i=1; i<=9; i++) {
			int a = sc.nextInt();
			if(Max < a) {
				nth = i;
				Max = a;
			}
		}
		System.out.println(Max);
		System.out.println(nth);
	}
}