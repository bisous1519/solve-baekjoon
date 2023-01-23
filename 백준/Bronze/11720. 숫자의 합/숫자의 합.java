import java.util.Scanner;

public class Main {
	
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		int N = sc.nextInt();
		String nums = sc.next();
		
		int sum = 0;
		for(int n=0; n<N; n++) {
			sum += nums.charAt(n) - '0';
		}
		
		System.out.println(sum);
	}
}