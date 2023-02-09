import java.util.Arrays;
import java.util.Scanner;

public class Main {

	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		String str = sc.next();
		int[] alpha = new int[26];
		Arrays.fill(alpha, -1);
		for(int i=0; i<str.length(); i++) {
			int a = str.charAt(i) - 'a';
			if(alpha[a] == -1) {
				alpha[a] = i;
			}
		}
		for(int i=0; i<alpha.length; i++) {
			System.out.print(alpha[i] + " ");
		}
	}
}