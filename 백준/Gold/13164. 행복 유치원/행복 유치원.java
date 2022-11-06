import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	static int N;
	static int K;
	static int[] Nums;
	static PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		K = Integer.parseInt(tokens.nextToken());
		Nums = new int[N];
		tokens = new StringTokenizer(input.readLine());
		for(int n=0; n<N; n++) {
			Nums[n] = Integer.parseInt(tokens.nextToken());
		} // 입력
		
		for(int n=1; n<N; n++) {
			pq.offer(Nums[n] - Nums[n-1]);
		}
		
		for(int k=1; k<=K-1; k++) {
			pq.poll();
		}
		
		int size = pq.size();
		int sum = 0;
		while(size-- > 0) {
			sum += pq.poll();
		}
		
		System.out.println(sum);
	}
}