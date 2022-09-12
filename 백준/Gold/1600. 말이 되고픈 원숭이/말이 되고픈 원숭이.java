import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	static int K;
	static int R, C;
	static int[][] Map;
	static boolean[][][] check;
	static Loc start;
	static Loc end;
	static int[][] dy = {{-2, -1, 1, 2, 2, 1, -1, -2},
						 {-1, 0, 1, 0}};
	static int[][] dx = {{1, 2, 2, 1, -1, -2, -2, -1},
						 {0, 1, 0, -1}}; // (말)오위부터 시계방향 | (원)상우하좌
	
	static class Loc {
		int k;
		int r;
		int c;
		public Loc(int r, int c) {
			this.r = r;
			this.c = c;
		}
		public Loc(int k, int r, int c) {
			this(r, c);
			this.k = k;
		}
	}
	
	public static boolean isIn(int r, int c) {
		return 0<=r && r<R && 0<=c && c<C;
	}
	
	public static int bfs() {
		Queue<Loc> queue = new LinkedList<>();
		check[0][0][0] = true;
		queue.offer(new Loc(0, 0, 0));
		
		int cnt = 0;
		while(!queue.isEmpty()) {
			cnt++;
			int size = queue.size();
			while(size-- > 0) {
				Loc cur = queue.poll();
				
				for(int dr=0; dr<2; dr++) { // 0: 말, 1: 원숭이
					if(cur.k == K) {
						dr = 1;
					}
					int goK = cur.k + (dr == 0 ? 1 : 0);
					for(int dc=0; dc<dy[dr].length; dc++) {
						
						int goR = cur.r + dy[dr][dc];
						int goC = cur.c + dx[dr][dc];
						
						if(!isIn(goR, goC)) { // 장외
							continue;
						}
						if(goR == end.r && goC == end.c) {
							return cnt;
						}
						
						if(check[goK][goR][goC]) { // 이미 방문
							continue;
						}
						if(Map[goR][goC] == 1) { // 장애물
							continue;
						}
						
						check[goK][goR][goC] = true;
						queue.offer(new Loc(goK, goR, goC));
					}
				}
			}
		} // bfs
		
		return -1;
	}
	
	public static void main(String[] args) throws IOException {
		K = Integer.parseInt(input.readLine());
		tokens = new StringTokenizer(input.readLine());
		C = Integer.parseInt(tokens.nextToken());
		R = Integer.parseInt(tokens.nextToken());
		Map = new int[R][C];
		check = new boolean[K+1][R][C];
		start = new Loc(0, 0);
		end = new Loc(R-1, C-1);
		for(int r=0; r<R; r++) {
			tokens = new StringTokenizer(input.readLine());
			for(int c=0; c<C; c++) {
				Map[r][c] = Integer.parseInt(tokens.nextToken());
			}
		} // 입력
		
		if(start.r == end.r && start.c == end.c) {
			System.out.println(0);
		
		// 여행을 떠나자!
		}else {
			int cnt = bfs();
			System.out.println(cnt);
		}
	}
}