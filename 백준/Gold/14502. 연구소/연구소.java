import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	static int R, C;
	static int[][] Map;
	static boolean[][] MapCopy;
	static int initSafeCnt = 0; // 바이러스 번지기 전 --> R*C - 벽개수(1)  
	static List<Virus> virus = new ArrayList<>();
	static int Max = Integer.MIN_VALUE;
	static int[] dy = {-1, 0, 1, 0}; // 상 우 하 좌
	static int[] dx = {0, 1, 0, -1};
	
	static class Virus {
		int r;
		int c;
		public Virus(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}
	
	static class Wall {
		int r;
		int c;
		public Wall(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}
	
	public static boolean isIn(int r, int c) {
		return 0<=r && r<R && 0<=c && c<C;
	}
	
	public static int spreadVirus(int r, int c, int cnt) {
		Queue<Virus> queue = new LinkedList<>();
		MapCopy[r][c] = true;
		cnt++;
		queue.offer(new Virus(r, c));
		
		while(!queue.isEmpty()) {
			int size = queue.size();
			while(size-- > 0) {
				Virus v = queue.poll();
			
				for(int d=0; d<dy.length; d++) {
					int goR = v.r + dy[d];
					int goC = v.c + dx[d];
					if(!isIn(goR, goC)) { // 장외
						continue;
					}
					if(Map[goR][goC] == 1) { // 벽에 막힘!
						continue;
					}
					if(MapCopy[goR][goC]) { // 이미 바이러스!
						continue;
					}
					MapCopy[goR][goC] = true;
					cnt++;
					queue.offer(new Virus(goR, goC));
				}
			}
		}
		
		return cnt;
	}
	
	public static void combi(int nth, Wall[] choosed, int sR, int sC) {
		if(nth == choosed.length) {
			// 벽 세개 세우자!
			for(Wall a : choosed) {
				Map[a.r][a.c] = 1; 
			}
			
			// 바이러스 퍼뜨리자!
			MapCopy = new boolean[R][C];
			int cnt = 0; // 바이러스 개수
			for(int i=0; i<virus.size(); i++) {
				Virus v = virus.get(i);
				if(!MapCopy[v.r][v.c]) { // 아직 퍼지지않은곳
					cnt += spreadVirus(v.r, v.c, 0);
				}
			}
			
			// max update
			Max = Math.max(Max, initSafeCnt - cnt);
			
			// 벽 세개 되돌려놓자!
			for(Wall a : choosed) {
				Map[a.r][a.c] = 0; 
			}
			
			return;
		}
		
		if(sC == C) {
			sC = 0;
			sR++;
		}
		for(int r=sR; r<R; r++){
			for(int c=sC; c<C; c++) {
				if(Map[r][c] == 0) {
					choosed[nth] = new Wall(r, c);
					combi(nth + 1, choosed, r, c+1);
				}
			}
			sC = 0;
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
				if(Map[r][c] == 2) { // 바이러스 위치 저장
					virus.add(new Virus(r, c));
				}else if(Map[r][c] == 1) {
					initSafeCnt++;
				}
			}
		} // 입력
		
		initSafeCnt = R * C - initSafeCnt - 3;
		
		// 벽 세울 위치 3곳 고르기
		combi(0, new Wall[3], 0, 0);
		
		System.out.println(Max);
	}
}