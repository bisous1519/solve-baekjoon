import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		for(int n=0; n<N; n++) {
			String str = sc.next();
			int sum = 0;
			int cnt = 0;
			for(int i=0; i<str.length(); i++) {
				if(str.charAt(i) == 'X') {
					cnt = 0;
				}else {
					cnt++;
					sum += cnt;
				}
			}
			System.out.println(sum);
		}
	}
}