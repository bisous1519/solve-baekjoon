import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	static int N;
	static int S;
	static int[] num;
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		S = Integer.parseInt(tokens.nextToken());
		num = new int[N+1];
		tokens = new StringTokenizer(input.readLine());
		for(int n=1; n<=N; n++) {
			num[n] = Integer.parseInt(tokens.nextToken());
		}//입력
		
		int left = 1;
		int right = 1;
		int sum = num[1];
		int minLength = Integer.MAX_VALUE;
		while(true) {
			if(sum >= S) {
				int length = right - left + 1;
				minLength = Math.min(minLength, length);

				if(left < right) {
					sum -= num[left];
					left++;
				}else {
					right++;
					if(right > N) break;
					sum += num[right];
				}
				
			}else {
				right++;
				if(right > N) break;
				sum += num[right];
				
			}
		}//while
		
		if(minLength == Integer.MAX_VALUE) {
			System.out.println(0);
		}else {
			System.out.println(minLength);
		}
	}
}