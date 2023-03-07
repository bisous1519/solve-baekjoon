import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;

	static final int EAST = 0; 
	static final int SOUTH = 1; 
	static final int WEST = 2; 
	static final int NORTH = 3; 
	static final int BOTTOM = 5;
	
	static int R;
	static int C;
	static int K;
	static int[][] Map;
	static int[] Dice = {4, 3, 2, 1, 5, 6};
	static int dir = EAST;			// 처음엔 동쪽부터
	static Loc loc = new Loc(1, 1);	// 처음엔 (1, 1)
	static int[] dy = {-1, 1, 0, 0};
	static int[] dx = {0, 0, -1, 1};
	
	public static class Loc {
		int r;
		int c;
		public Loc(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}
	
	public static boolean isIn(int r, int c) {
		return 1<=r && r<=R && 1<=c && c<=C;
	}
	
	public static void rollToEast() { // 동
		if(loc.c == C) {
			roll(WEST);
			
		}else {
			dir = EAST;
			loc.c++;
			int temp = Dice[5];
			Dice[5] = Dice[1];
			Dice[1] = Dice[3];
			Dice[3] = Dice[0];
			Dice[0] = temp;
		}
	}
	
	public static void rollToWest() { // 서
		if(loc.c == 1) {
			roll(EAST);
			
		}else {
			dir = WEST;
			loc.c--;
			int temp = Dice[5];
			Dice[5] = Dice[0];
			Dice[0] = Dice[3];
			Dice[3] = Dice[1];
			Dice[1] = temp;
		}
	}
	
	public static void rollToSouth() { // 남
		if(loc.r == R) {
			roll(NORTH);
			
		}else {
			dir = SOUTH;
			loc.r++;
			int temp = Dice[5];
			Dice[5] = Dice[4];
			Dice[4] = Dice[3];
			Dice[3] = Dice[2];
			Dice[2] = temp;
		}
	}
	
	public static void rollToNorth() { // 북
		if(loc.r == 1) {
			roll(SOUTH);
			
		}else {
			dir = NORTH;
			loc.r--;
			int temp = Dice[5];
			Dice[5] = Dice[2];
			Dice[2] = Dice[3];
			Dice[3] = Dice[4];
			Dice[4] = temp;
		}
	}
	
	public static void roll(int d) {
		switch(d) {
		case EAST: rollToEast(); break;
		case SOUTH: rollToSouth(); break;
		case WEST: rollToWest(); break;
		case NORTH: rollToNorth(); break;
		}
	}
	
	public static int cntBfs(int num) {
		Queue<Loc> queue = new LinkedList<>();
		queue.offer(new Loc(loc.r, loc.c));
		boolean[][] isVisited = new boolean[R+1][C+1];
		isVisited[loc.r][loc.c] = true;
		
		int cnt = 1;
		while(!queue.isEmpty()) {
			int size = queue.size();
			while(size-- > 0) {
				Loc cur = queue.poll();
				
				for(int d=0; d<dy.length; d++) {
					int goR = cur.r + dy[d];
					int goC = cur.c + dx[d];
					
					if(!isIn(goR, goC)) continue; // 장외
					if(isVisited[goR][goC]) continue; // 이미 방문
					if(Map[goR][goC] != num) continue; // 갈필요x
					
					cnt++;
					isVisited[goR][goC] = true;
					queue.offer(new Loc(goR, goC));
				}
			}
		}
		
		return cnt;
		
	}
	
	public static void main(String[] args) throws Exception {
		tokens = new StringTokenizer(input.readLine());
		R = Integer.parseInt(tokens.nextToken());
		C = Integer.parseInt(tokens.nextToken());
		K = Integer.parseInt(tokens.nextToken());
		Map = new int[R+1][C+1];
		for(int n=1; n<=R; n++) {
			tokens = new StringTokenizer(input.readLine());
			for(int m=1; m<=C; m++) {
				Map[n][m] = Integer.parseInt(tokens.nextToken());
			}
		}//입력
		
		// 주사위 굴리기 시작
		dir = EAST;
		int score = 0;
		for(int k=0; k<K; k++) {
			// 1) 주사위 굴림
			roll(dir);
			
			// 2) 점수 획득
			score += cntBfs(Map[loc.r][loc.c]) * Map[loc.r][loc.c];
			
			// 3) 이동방향 결정
			if(Dice[BOTTOM] > Map[loc.r][loc.c]) { // A > B
				dir++;
				if(dir == 4) {
					dir = 0;
				}
			}else if(Dice[BOTTOM] < Map[loc.r][loc.c]) { // A < B
				dir--;
				if(dir == -1) {
					dir = 3;
				}
			}
		}
		
		System.out.println(score);
	}
}