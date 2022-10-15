import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	static int R, C;
	static int[][] Map;
	static final int GO = 0;
	static final int WALL = 1;
	static int[] dy = {-1, 0, 1, 0}; // 상 우 하 좌
	static int[] dx = {0, 1, 0, -1};
	
	static class Loc {
		int isB;
		int r;
		int c;
		public Loc(int isB, int r, int c) {
			this.isB = isB;
			this.r = r;
			this.c = c;
		}
	}

	public static boolean isIn(int r, int c) {
		return 0<=r && r<R && 0<=c && c<C;
	}
	
	public static int bfs() {
		Queue<Loc> queue = new LinkedList<>();
		boolean[][][] visited = new boolean[2][R][C]; // 0:깬적없음 1:깬적있음
		visited[0][0][0] = true;
		queue.offer(new Loc(0, 0, 0));
		
		int depth = 1;
		while(!queue.isEmpty()) {
			depth++;
			int size = queue.size();
			while(size-- > 0) {
				Loc cur = queue.poll();
				
				for(int d=0; d<dy.length; d++) {
					int goR = cur.r + dy[d];
					int goC = cur.c + dx[d];
					
					if(!isIn(goR, goC)) { // 장외
						continue;
					}
					
					if(goR == R-1 && goC == C-1) {
						return depth;
					}
					
					if(Map[goR][goC] == WALL) { // 벽이고
						if(cur.isB == 1) { // 깰 수 없음
							continue;
						}
						if(visited[cur.isB][goR][goC]) { // cur.isB == false
							continue;
						}
						visited[cur.isB][goR][goC] = true;
						queue.offer(new Loc(1, goR, goC));
					}
					else { // 벽 아니야
						if(visited[cur.isB][goR][goC]) {
							continue;
						}
						visited[cur.isB][goR][goC] = true;
						queue.offer(new Loc(cur.isB, goR, goC));
					}
				}
			}
		} // while
		
		return -1;
	}
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		R = Integer.parseInt(tokens.nextToken());
		C = Integer.parseInt(tokens.nextToken());
		Map = new int[R][C];
		for(int r=0; r<R; r++) {
			String str = input.readLine();
			for(int c=0; c<C; c++) {
				Map[r][c] = str.charAt(c) - '0';
			}
		} // 입력
		
		if(R == 1 && C == 1) {
			System.out.println(1);
		}else {
			System.out.println(bfs());
		}
	}
}