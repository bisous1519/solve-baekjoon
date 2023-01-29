import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();
	
	static int T;
	static int R;
	static String str;
	
	public static void main(String[] args) throws IOException {
		T = Integer.parseInt(input.readLine());
		for(int t=1; t<=T; t++) {
			tokens = new StringTokenizer(input.readLine());
			R = Integer.parseInt(tokens.nextToken());
			str = tokens.nextToken();
			for(int length=0; length<str.length(); length++) {
				for(int r=1; r<=R; r++) {
					output.append(str.charAt(length));
				}
			}
			output.append("\n");
		}
		System.out.println(output);
	}
}