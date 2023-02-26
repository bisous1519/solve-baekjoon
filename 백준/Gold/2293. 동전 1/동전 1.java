import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	static int N;
	static int K;
	static int[] coin;
	static int[] dp;
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		K = Integer.parseInt(tokens.nextToken());
		coin = new int[N];
		dp = new int[K+1];
		for(int n=0; n<N; n++) {
			coin[n] = Integer.parseInt(input.readLine());
		}//입력
		
		dp[0] = 1;
		for(int n=0; n<N; n++) {
			for(int k=coin[n]; k<=K; k++) {
				dp[k] += dp[k - coin[n]];
			}
		}
		
		System.out.println(dp[K]);
	}
}