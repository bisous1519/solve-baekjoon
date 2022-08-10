import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();
	
	static int T;
	static int R;
	static int N;
	static double[] factorial = new double[31];
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		T = Integer.parseInt(tokens.nextToken());
		for(int t=0; t<T; t++) {
			tokens = new StringTokenizer(input.readLine());
			R = Integer.parseInt(tokens.nextToken());
			N = Integer.parseInt(tokens.nextToken());
			if(R == N) {
				output.append(1).append("\n");
			}else {
				R = Math.min(R, N-R); // nCr = nC(n-r)
				double answer = 1;
				int d = 1;
				for(int i=N; i>=R+1; i--) { // nCr = n! / (n-r)!r! = (n * n-1 * n-2 * .. * r+1) / (n-r)!
					answer *= i;
					if(answer % d == 0) {
						answer /= d;
					}
					if(d < N-R) { // 아직 나눌 분모가 남아있음
						d++;
					}
				}
				output.append((int)answer).append("\n");
			}
		}
		System.out.println(output);
	}
}