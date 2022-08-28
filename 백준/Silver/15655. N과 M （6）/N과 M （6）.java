import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();
	
	static int N;
	static int M;
	static int[] Nums;
	
	public static void combi(int nth, int[] choosed, int start) {
		if(nth == M) {
			for(int a : choosed) {
				output.append(a + " ");
			}
			output.append("\n");
			return;
		}
		
		for(int i=start; i<N; i++) {
			choosed[nth] = Nums[i];
			combi(nth + 1, choosed, i + 1);
		}
	}
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		Nums = new int[N];
		tokens = new StringTokenizer(input.readLine());
		for(int n=0; n<N; n++) {
			Nums[n] = Integer.parseInt(tokens.nextToken());
		}
		Arrays.sort(Nums);
		combi(0, new int[M], 0);
		System.out.println(output);
	}
}