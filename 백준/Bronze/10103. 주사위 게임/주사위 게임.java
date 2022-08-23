import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	static int N;
	static int cScore;
	static int sScore;
	
	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(input.readLine());
		for(int n=0; n<N; n++) {
			tokens = new StringTokenizer(input.readLine());
			int c = Integer.parseInt(tokens.nextToken());
			int s = Integer.parseInt(tokens.nextToken());
			if(c > s) {
				sScore += c;
			}else if(c < s) {
				cScore += s;
			}
		}
		System.out.println(100-cScore);
		System.out.println(100-sScore);
	}

}