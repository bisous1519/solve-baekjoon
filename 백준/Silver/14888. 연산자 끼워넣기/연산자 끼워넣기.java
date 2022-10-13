import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	static int N;
	static int[] nums;
	static List<Character> op = new ArrayList<>();
	static int Max = Integer.MIN_VALUE;
	static int Min = Integer.MAX_VALUE;
	
	public static int calc(char[] operator) {
		int result = nums[0];
		
		for(int n=1; n<N; n++) {
			switch(operator[n-1]) {
			case '+':
				result += nums[n];
				break;
			case '-':
				result -= nums[n];
				break;
			case '*':
				result *= nums[n];
				break;
			case '/':
				if(result < 0) {
					result = (result * -1) / nums[n];
					result *= -1;
				}else {
					result /= nums[n];
				}
				break;
			}
		}
		
		return result;
	}
	
	public static void permu(int nth, char[] choosed, boolean[] visited) {
		if(nth == N-1) {
			int num = calc(choosed);
			Max = Math.max(Max, num);
			Min = Math.min(Min, num);
			return;
		}
		
		for(int i=0; i<op.size(); i++) {
			if(!visited[i]) {
				visited[i] = true;
				choosed[nth] = op.get(i);
				permu(nth + 1, choosed, visited);
				visited[i] = false;
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(input.readLine());
		nums = new int[N];
		tokens = new StringTokenizer(input.readLine());
		for(int n=0; n<N; n++) {
			nums[n] = Integer.parseInt(tokens.nextToken());
		}
		tokens = new StringTokenizer(input.readLine());
		for(int i=0; i<4; i++) {
			char c = '+';
			switch(i) {
			case 1: c = '-'; break;
			case 2: c = '*'; break;
			case 3: c = '/'; break;
			}
			int k = Integer.parseInt(tokens.nextToken());
			for(int j=0; j<k; j++) {
				op.add(c);
			}
		} // 입력
		
		permu(0, new char[N-1], new boolean[op.size()]);
		
		System.out.printf("%d\n%d", Max, Min);
	}
}