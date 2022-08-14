import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	static Queue<int[]> queue = new LinkedList<>();
	static int A;
	static int B;
	static int N; // 동규의 현재위치 (출발점)
	static int M; // 주미의 현재위치 (도착점)
	static int DolRange = 100000;
	static boolean[] visited = new boolean[DolRange+1];
	static int[] dx;
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		A = Integer.parseInt(tokens.nextToken());
		B = Integer.parseInt(tokens.nextToken());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		dx = new int[] {A, B, A, B, -1 * A, -1 * B, 1, -1};
		visited[N] = true;
		queue.offer(new int[] {N, 0}); // {출발점, 이동횟수 0}
		while(!queue.isEmpty()) {
			int[] current = queue.poll();
			int cur = current[0];
			int move = current[1];
			// 8가지 이동하는 경우의 수
			for(int i=0, moveTo=0; i<8; i++) {
				if(i < 2) {
					moveTo = cur * dx[i]; // *A, *B
				}else {
					moveTo = cur + dx[i]; // +A, +B, -A, -B, +1, -1
				}
				if(!(0 <= moveTo && moveTo <= DolRange)) {
					continue; // 돌의 범위를 넘어가면 안되지
				}
				if(moveTo == M) {
					System.out.println(move + 1); // 도착!!
					return;
				}
				if(!visited[moveTo]) {
					visited[moveTo] = true;
					queue.offer(new int[] {moveTo, move + 1});
				}
			}
		}
	}
}