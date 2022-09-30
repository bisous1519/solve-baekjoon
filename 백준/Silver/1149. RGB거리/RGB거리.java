import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Main {

	private static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	private static StringTokenizer tokens;
	
	static int N;
	static int[] arr;
	static int[] result = new int[3];
	
	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(input.readLine());
		
		// 첫번째 집
		tokens = new StringTokenizer(input.readLine());
		for(int i=0; i<3; i++) {
			result[i] = Integer.parseInt(tokens.nextToken());
		}
		
		// 두번째 집 부터
		for(int n=1; n<N; n++) {
			tokens = new StringTokenizer(input.readLine());
			arr = new int[3];
			for(int i=0; i<3; i++) {
				arr[i] = Integer.parseInt(tokens.nextToken());
			}
			// 입력
			
			arr[0] += Math.min(result[1], result[2]);
			arr[1] += Math.min(result[0], result[2]);
			arr[2] += Math.min(result[0], result[1]);
			
			result = arr.clone();
		}
		
		// 최솟값
		int min = Integer.MAX_VALUE;
		for(int i=0; i<3; i++) {
			min = Math.min(min, result[i]);
		}
		
		System.out.println(min);
	}
}