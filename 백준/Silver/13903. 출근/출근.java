import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	static int R;
	static int C;
	static int[][] Map;
	static int N;
	static int[] dy;
	static int[] dx;
	static int Min = Integer.MAX_VALUE;
	
	static class Roc {
		int r;
		int c;
		public Roc(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}
	
	public static boolean isIn(int r, int c) {
		return 0<=r && r<R && 0<=c && c<C;
	}
	
	public static void bfs() {
		Queue<Roc> queue = new LinkedList<>();
		boolean[][] isVisited = new boolean[R][C];
		for(int c=0; c<C; c++) {
			if(Map[0][c] == 1) {
				queue.add(new Roc(0, c));
				isVisited[0][c] = true;
			}
		}
		
		int cnt = 0;
		while(!queue.isEmpty()) {
			int size = queue.size();
			while(size-- > 0) {
				Roc cur = queue.poll();
				
				for(int d=0; d<dy.length; d++) {
					int goR = cur.r + dy[d];
					int goC = cur.c + dx[d];
					
					if(!isIn(goR, goC)) continue; 
					if(isVisited[goR][goC]) continue;
					if(goR == R-1) {
						if(Map[goR][goC] == 1) {
							Min = cnt + 1;
							return;
						}
					}
					
					if(Map[goR][goC] == 1) {
						queue.add(new Roc(goR, goC));
						isVisited[goR][goC] = true;
					}
				}
			}
			cnt++;
		}
	}
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		R = Integer.parseInt(tokens.nextToken());
		C = Integer.parseInt(tokens.nextToken());
		Map = new int[R][C];
		for(int r=0; r<R; r++) {
			tokens = new StringTokenizer(input.readLine());
			for(int c=0; c<C; c++) {
				Map[r][c] = Integer.parseInt(tokens.nextToken());
			}
		}//입력
		
		N = Integer.parseInt(input.readLine());
		dy = new int[N];
		dx = new int[N];
		for(int n=0; n<N; n++) {
			tokens = new StringTokenizer(input.readLine());
			dy[n] = Integer.parseInt(tokens.nextToken());
			dx[n] = Integer.parseInt(tokens.nextToken());
		}//입력
		
		bfs();
		
		if(Min == Integer.MAX_VALUE) {
			System.out.println(-1);
		}else {
			System.out.println(Min);
		}
	}
}