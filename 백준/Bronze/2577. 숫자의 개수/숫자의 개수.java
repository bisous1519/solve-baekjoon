import java.util.Arrays;
import java.util.Scanner;

public class Main {
	
	static Scanner sc = new Scanner(System.in); 
	
	public static void main(String[] args) {
		int a = 1;
		for(int i=1; i<=3; i++) {
			a *= sc.nextInt();
		}
		
		String num = a + " ";
		int[] cnt = new int[10];
		Arrays.fill(cnt, 0);
		for(int i=0; i<num.length() - 1; i++) {
			cnt[num.charAt(i) - '0']++;
		}
		
		for(int i=0; i<10; i++) {
			System.out.println(cnt[i]);
		}
	}
}