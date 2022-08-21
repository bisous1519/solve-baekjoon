import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	static int N; // 수빈 첫 위치
	static int K; // 동생 첫 위치
	static final int Gap = 200_000; // 진짜 위치랑 배열인덱스의 차이
	static boolean[] isVisited = new boolean[Gap*2+1];
	
	static class Subin {
		int n;
		int sec;
		public Subin(int n, int sec) {
			this.n = n;
			this.sec = sec;
		}
	}
	
	public static void bfs(int n, int sec) {
		if(n == K) {
			System.out.println(sec);
			return;
		}
		
		Queue<Subin> queue = new LinkedList<>();
		isVisited[n + Gap] = true;
		queue.offer(new Subin(n, sec));
		
		while(!queue.isEmpty()) {
			Subin s = queue.poll();
			
			// 동생찾음!
			if(s.n + 1 == K || s.n - 1 == K || s.n * 2 == K) {
				System.out.println(s.sec + 1);
				break;
			}
			
			// 1 걸어감
			if(s.n + 1 <= Gap && s.n + 1 >= Gap * -1) {
				if(!isVisited[s.n + 1 + Gap]) {
					isVisited[s.n + 1 + Gap] = true;
					queue.offer(new Subin(s.n + 1, s.sec + 1));
				}
			}
			
			// -1 걸어감
			if(s.n - 1 <= Gap && s.n - 1 >= Gap * -1) {
				if(!isVisited[s.n - 1 + Gap]) {
					isVisited[s.n - 1 + Gap] = true;
					queue.offer(new Subin(s.n - 1, s.sec + 1));
				}
			}
			
			// *2 순간이동
			if(s.n * 2 <= Gap && s.n * 2 >= Gap * -1) {
				if(!isVisited[s.n * 2 + Gap]) {
					isVisited[s.n * 2 + Gap] = true;
					queue.offer(new Subin(s.n * 2, s.sec + 1));
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		K = Integer.parseInt(tokens.nextToken());
		bfs(N, 0);
	}
}