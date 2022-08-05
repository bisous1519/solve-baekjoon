import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {

	private static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	private static StringTokenizer tokens;
	private static StringBuilder output = new StringBuilder();
	
	private static Stack<int[]> stack = new Stack<>();
	private static int N;
	private static int[] Top;
	private static int[] result;
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		result = new int[N + 1];
		Top = new int[N + 1];
		tokens = new StringTokenizer(input.readLine());
		for(int i=1; i<=N; i++) {
			Top[i] = Integer.parseInt(tokens.nextToken());
		}
		for(int i=N; i>0; i--) {
			if(stack.empty()) {
				stack.push(new int[] {Top[i], i});
				continue;
			}
			if(Top[i] >= stack.peek()[0]) {
				while(!(stack.empty()) && Top[i] >= stack.peek()[0]) {
					result[stack.pop()[1]] = i;
				}
			}
			stack.push(new int[] {Top[i], i});
		}
		while(!(stack.empty())){
			result[stack.pop()[1]] = 0;
		}
		for(int i=1; i<=N; i++) {
//			System.out.printf("%d ", result[i]);
			output.append(result[i] + " ");
		}
		System.out.println(output);
	}

}