import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 순열 (nPm) (오름차순)
public class Main {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();

	static int N, M;
	
	public static void permu(int nth, int[] choosed, int start) {
		if(nth == M) {
			for(int i=0; i<M; i++) {
				output.append(choosed[i] + " ");
			}
			output.append("\n");
			
			return;
		}
		
		for(int i=start; i<=N; i++) {
			choosed[nth] = i;
			permu(nth + 1, choosed, i + 1);
		}
	}
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		
		permu(0, new int[M], 1);
		
		System.out.println(output);
	}
}