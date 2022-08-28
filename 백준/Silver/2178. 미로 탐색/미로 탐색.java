import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	static int N;
	static int M;
	static char[][] Map;
	static boolean[][] isVisited;
	static int ans;
	static int[] dy = {-1, 0, 1, 0}; // 상 우 하 좌
	static int[] dx = {0, 1, 0, -1};
	
	public static class Loc {
		int r;
		int c;
		int depth;
		public Loc(int r, int c, int depth) {
			this.r = r;
			this.c = c;
			this.depth = depth;
		}
	}
	
	public static boolean isIn(int r, int c) {
		return 0<=r && r<N && 0<=c && c<M;
	}
	
	public static void bfs(int r, int c, int depth) {
		Queue<Loc> queue = new LinkedList<>();
		isVisited[r][c] = true;
		queue.offer(new Loc(r, c, depth));
		
		while(!queue.isEmpty()) {
			int size = queue.size();
			while(size-- > 0) {
				Loc cur = queue.poll();
				
				for(int d=0; d<dy.length; d++) {
					int goR = cur.r + dy[d];
					int goC = cur.c + dx[d];
					// 장외야 돌아가
					if(!isIn(goR, goC)) {
						continue;
					}
					// 이미 방문한 곳
					if(isVisited[goR][goC]) {
						continue;
					}
					// 도착!
					if(goR == N-1 && goC == M-1) {
						ans = cur.depth + 1;
						return;
					}
					// 가도 되는 곳!
					if(Map[goR][goC] == '1') {
						isVisited[goR][goC] = true;
						queue.offer(new Loc(goR, goC, cur.depth + 1));
					}
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		Map = new char[N][M];
		isVisited = new boolean[N][M];
		for(int n=0; n<N; n++) {
			Map[n] = input.readLine().toCharArray();
		}
		
		bfs(0, 0, 1);
		System.out.println(ans);
	}
}