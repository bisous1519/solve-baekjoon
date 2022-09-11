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
	static int cheese = 0;
	static int minute = 0;
	static boolean isFinished = false;
	static int[] dy = {-1, 0, 1, 0}; // 상 우 하 좌
	static int[] dx = {0, 1, 0, -1};
	
	static class Loc {
		int r;
		int c;
		public Loc(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}
	
	public static boolean isIn(int r, int c) {
		return 0<=r && r<R && 0<=c && c<C;
	}
	
	public static boolean isMeetWall(int r, int c) {
		return r==0 || r==R-1 || c==0 || c==C-1;
	}
	
	public static void melt(Queue<Loc> queue) {
		while(!queue.isEmpty()) {
			Loc cur = queue.poll();
			Map[cur.r][cur.c] = 0;
		}
	}
	
	public static void findAirBfs() {
		Queue<Loc> queue = new LinkedList<>();
		Queue<Loc> cheeseQ = new LinkedList<>();
		boolean[][] check = new boolean[R][C];
		
		check[0][0] = true;
		queue.offer(new Loc(0, 0));
		
		boolean isMeetAir = false;
		while(!queue.isEmpty()) {
			int size = queue.size();
			while(size-- > 0) {
				Loc cur = queue.poll();
				
				for(int d=0; d<dy.length; d++) {
					int goR = cur.r + dy[d];
					int goC = cur.c + dx[d];
					
					if(!isIn(goR, goC)) { // 장 외
						continue;
					}
					if(check[goR][goC]) { // 이미 방문한 곳
						continue;
					}
					
					// 가자!
					check[goR][goC] = true;
					
					if(Map[goR][goC] == 0 || isMeetWall(goR, goC)) { // 공기랑 접촉
						isMeetAir = true;
						queue.offer(new Loc(goR, goC));
						continue;
					}
					else if(Map[goR][goC] == 1) { // 치즈 만남!
						cheeseQ.offer(new Loc(goR, goC));
					}
				}
			}
		} // bfs
		
		// 돌면서 공기만남!
		if(isMeetAir) {
			// 맞닿았던 치즈 녹이기
			if(cheese - cheeseQ.size() > 0) {
				cheese -= cheeseQ.size();
				melt(cheeseQ);
				
			// 이번에 녹이면 다 녹음
			}else {
				isFinished = true;
			}
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
				if(Map[r][c] == 1) {
					cheese++; // 치즈 개수 세기
				}
			}
		} // 입력
		
		// 치즈 녹이러 가기
		while(!isFinished) {
			minute++;
			findAirBfs();
		}
		
		System.out.printf("%d\n%d", minute, cheese);
	}
}