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
	
	public static void combiD(int nth, int[] choosed, int start) {
		if(nth == M) {
			for(int a : choosed) {
				output.append(a + " ");
			}
			output.append("\n");
			return;
		}
		
		for(int i=start; i<=N; i++) {
			choosed[nth] = i;
			combiD(nth + 1, choosed, i);
		}
	}
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		combiD(0, new int[M], 1);
		System.out.println(output);
	}
}