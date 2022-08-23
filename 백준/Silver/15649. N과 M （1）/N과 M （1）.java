import java.util.Scanner;

public class Main {
	
	static int N;
	static int M;
	
	public static void permu(int nth, int[] choosed, boolean[] isVisited) {
		if(nth == M) {
			for(int i=0; i<M; i++) {
				System.out.print(choosed[i] + " ");
			}
			System.out.println();
			return;
		}
		
		for(int i=1; i<=N; i++) {
			if(!isVisited[i]) {
				isVisited[i] = true;
				choosed[nth] = i;
				permu(nth + 1, choosed, isVisited);
				isVisited[i] = false;
			}
		}
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		permu(0, new int[M], new boolean[N+1]);
	}

}