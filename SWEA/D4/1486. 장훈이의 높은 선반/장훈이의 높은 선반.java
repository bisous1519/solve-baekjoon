import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();
	
	static int T;
	static int N, B;
	static int[] height;
	static int Min;
	
	public static void subset(int nth, int sum) {
		if(nth == N) {
			if(sum >= B) {
				Min = Math.min(Min, sum - B);
			}
			return;
		}
		
		subset(nth + 1, sum + height[nth]);
		subset(nth + 1, sum);
	}
	
	public static void main(String[] args) throws IOException {
		T = Integer.parseInt(input.readLine());
		for(int t=1; t<=T; t++) {
			tokens = new StringTokenizer(input.readLine());
			N = Integer.parseInt(tokens.nextToken());
			B = Integer.parseInt(tokens.nextToken());
			height = new int[N];
			tokens = new StringTokenizer(input.readLine());
			for(int n=0; n<N; n++) {
				height[n] = Integer.parseInt(tokens.nextToken());
			} // 입력
			
			Min = Integer.MAX_VALUE;
			subset(0, 0);
			output.append(String.format("#%d %d\n", t, Min));
		}
		
		System.out.println(output);
	}
}
