import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();
	
	static int TC;
	static int N;
	static int sR;
	static int sC;
	static int eR;
	static int eC;
	static int result;
	static Queue<int[]> queue = new LinkedList<>();
	static boolean[][] Map;
	static int[] dy = {-1, -2, -2, -1, 1, 2, 2, 1}; // 왼쪽위에 아래 부터 시계방향
	static int[] dx = {-2, -1, 1, 2, 2, 1, -1, -2};
	
	public static boolean isIn(int r, int c) {
		return 0<=r && r<N && 0<=c && c<N;
	}
	
	public static void bfs() {
		while(!queue.isEmpty()) {
			int[] cur = queue.poll();
			int r = cur[0];
			int c = cur[1];
			int cnt = cur[2];
			
			if(r == eR && c == eC) {
				result = cnt;
				return;
			}
			
			for(int i=0; i<dy.length; i++) {
				int tr = r + dy[i];
				int tc = c + dx[i];
				if(isIn(tr, tc) && !Map[tr][tc]) {
					Map[tr][tc] = true;
					queue.offer(new int[] {tr, tc, cnt + 1});
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		TC = Integer.parseInt(input.readLine());
		for(int tc=0; tc<TC; tc++) {
			N = Integer.parseInt(input.readLine());
			tokens = new StringTokenizer(input.readLine());
			sR = Integer.parseInt(tokens.nextToken());
			sC = Integer.parseInt(tokens.nextToken());
			tokens = new StringTokenizer(input.readLine());
			eR = Integer.parseInt(tokens.nextToken());
			eC = Integer.parseInt(tokens.nextToken());

			Map = new boolean[N][N];
			Map[sR][sC] = true;
			queue.clear();
			queue.offer(new int[] {sR, sC, 0});
			bfs();
			output.append(String.format("%d\n", result));
		}
		System.out.println(output);
	}
}