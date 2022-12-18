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
	static int T;
	static int[][] Map;
	static int[] dy = {-1, 1, 0, 0}; // 상하좌우
	static int[] dx = {0, 0, -1, 1};
	
	static class Hero {
		int r;
		int c;
		boolean hasGram;
		public Hero(int r, int c, boolean hasGram) {
			this.r = r;
			this.c = c;
			this.hasGram = hasGram;
		}
	}
	
	public static boolean isIn(int r, int c) {
		return 1<=r && r<=R && 1<=c && c<=C;
	}
	
	public static String bfs() {
		Queue<Hero> queue = new LinkedList<>();
		queue.offer(new Hero(1, 1, false)); // 용사 초기위치
		
		boolean[][][] isVisited = new boolean[R+1][C+1][2];
		isVisited[1][1][0] = true; // 용사 초기위치 체크
		
		int time = 0;
		while(!queue.isEmpty()) {
			
			time++;
			if(time > T) return "Fail";
			
			int size = queue.size();
			while(size-- > 0) {
				
				Hero hero = queue.poll();
				
				for(int d=0; d<dy.length; d++) {
					int goR = hero.r + dy[d];
					int goC = hero.c + dx[d];
					
					if(!isIn(goR, goC)) continue; // 맵 밖
					if(isVisited[goR][goC][hero.hasGram ? 1 : 0]) continue; // 방문한 곳
					if(!hero.hasGram && Map[goR][goC] == 1) continue; // 그람 없는데 벽
					
					if(goR == R && goC == C) return time + "";
					
					isVisited[goR][goC][hero.hasGram ? 1 : 0] = true;
					
					if(Map[goR][goC] == 2) {
						queue.offer(new Hero(goR, goC, true));
					}else {
						queue.offer(new Hero(goR, goC, hero.hasGram));
					}
				}
			}
		}
		
		return "Fail";
	}

	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		R = Integer.parseInt(tokens.nextToken());
		C = Integer.parseInt(tokens.nextToken());
		T = Integer.parseInt(tokens.nextToken());
		Map = new int[R+1][C+1];
		for(int r=1; r<=R; r++) {
			tokens = new StringTokenizer(input.readLine());
			for(int c=1; c<=C; c++) {
				Map[r][c] = Integer.parseInt(tokens.nextToken());
			}
		}// 입력
		
		System.out.println(bfs());
	}
}