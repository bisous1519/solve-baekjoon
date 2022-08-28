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
	static int[] Num;
	
	public static void perm(int nth, int[] choosed, boolean[] isVisited) {
		if(nth == M) {
			for(int a : choosed) {
				output.append(a + " " );
			}
			output.append("\n");
			return;
		}
		
		for(int i=0; i<N; i++) {
			if(!isVisited[i]) {
				isVisited[i] = true;
				choosed[nth] = Num[i];
				perm(nth + 1, choosed, isVisited);
				isVisited[i] = false;
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		Num = new int[N];
		tokens = new StringTokenizer(input.readLine());
		for(int n=0; n<N; n++) {
			Num[n] = Integer.parseInt(tokens.nextToken());
		}
		Arrays.sort(Num);
		perm(0, new int[M], new boolean[N]);
		System.out.println(output);
	}
}