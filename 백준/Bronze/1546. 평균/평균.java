import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		double[] s = new double[N];
		double max = -1;
		for(int n=0; n<N; n++) {
			s[n] = sc.nextInt();
			max = Math.max(max, s[n]);
		}
		double sum = 0;
		for(int n=0; n<N; n++) {
			sum += s[n]/max*100;
		}
		System.out.println(sum / N);
	}
}