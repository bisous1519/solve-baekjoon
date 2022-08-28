import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();
	
	static int N;
	static int M;
	
	public static void permD(int nth, int[] choosed) {
		if(nth == M) {
			for(int a : choosed) {
				output.append(a + " ");
			}
			output.append("\n");
			return;
		}
		
		for(int i=1; i<=N; i++) {
			choosed[nth] = i;
			permD(nth + 1, choosed);
		}
	}
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		permD(0, new int[M]);
		System.out.println(output);
	}
}