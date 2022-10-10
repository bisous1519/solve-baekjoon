import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 순열 (nPm)
public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();
	
	static int N, M;
	
	public static void permu(int nth, int[] choosed, boolean[] visited) {
		if(nth == M) {
			for(int i=0; i<M; i++) {
				output.append(choosed[i] + " ");
			}
			output.append("\n");
			
			return;
		}
		
		for(int i=1; i<=N; i++) {
			if(!visited[i]) {
				visited[i] = true;
				choosed[nth] = i;
				permu(nth + 1, choosed, visited);
				visited[i] = false;
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		
		permu(0, new int[M], new boolean[N+1]);
		
		System.out.println(output);
	}
}