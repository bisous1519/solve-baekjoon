import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();
	
	static int N, M;
	static int[] Nums;
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		Nums = new int[N+1];
		tokens = new StringTokenizer(input.readLine());
		for(int n=1; n<=N; n++) {
			int num = Integer.parseInt(tokens.nextToken());
			Nums[n] = Nums[n-1] + num;
		}
		
		for(int m=0; m<M; m++) {
			tokens = new StringTokenizer(input.readLine());
			int s = Integer.parseInt(tokens.nextToken());
			int e = Integer.parseInt(tokens.nextToken());
			System.out.println(Nums[e] - Nums[s-1]);
		}
	}
}