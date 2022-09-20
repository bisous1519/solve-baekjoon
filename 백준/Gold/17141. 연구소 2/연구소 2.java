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
	static int[][] Map;
	static boolean[][] check;
	static int BlankCnt;
	static int Min = Integer.MAX_VALUE;
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
		return 0<=r && r<N && 0<=c && c<N;
	}
	
	public static void bfs(Loc[] virus) {
		Queue<Loc> queue = new LinkedList<>();
		check = new boolean[N][N];
		
		// 초기 바이러스 위치 방문 체크하고 큐에 넣기
		for(int i=0; i<virus.length; i++) {
			int r = virus[i].r;
			int c = virus[i].c;
			check[r][c] = true;
			queue.offer(new Loc(r, c));
		}
		
		// 퍼뜨리자!
		int minute = 0;
		int cnt = M;
		if(BlankCnt == cnt) {
			Min = 0;
			return;
		}
		while(!queue.isEmpty()) {
			int size = queue.size();
			minute++;
			if(minute >= Min) { // 이미 더 오래걸리는 조합
				return;
			}
			while(size-- > 0) {
				Loc cur = queue.poll();
				
				// 사방탐색
				for(int d=0; d<dy.length; d++) {
					int goR = cur.r + dy[d];
					int goC = cur.c + dx[d];
					
					if(!isIn(goR, goC)) { // 장외!
						continue;
					}
					if(check[goR][goC]) { // 이미 방문한 곳
						continue;
					}
					if(Map[goR][goC] == 1) { // 벽
						continue;
					}
					
					// 퍼뜨려!
					check[goR][goC] = true;
					queue.offer(new Loc(goR, goC));
					cnt++;
					
					// 다 퍼뜨렸어!
					if(BlankCnt == cnt && minute < Min) {
						Min = minute;
						return;
					}
				}
			}
		}
	}
	
	public static void combi(int nth, Loc[] choosed, int sR, int sC) {
		if(nth == M) {
			// 바이러스 퍼뜨리러 가자
			bfs(choosed);
			
			return;
		}
		
		if(sC == N) {
			sR++;
			sC = 0;
		}
		for(int r=sR; r<N; r++) {
			for(int c=sC; c<N; c++) {
				if(Map[r][c] == 2) {
					choosed[nth] = new Loc(r, c);
					combi(nth + 1, choosed, r, c+1);
				}
			}
			sC = 0;
		}
	}
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		Map = new int[N][N];
		for(int r=0; r<N; r++) {
			tokens = new StringTokenizer(input.readLine());
			for(int c=0; c<N; c++) {
				Map[r][c] = Integer.parseInt(tokens.nextToken());
				if(Map[r][c] == 1) {
					BlankCnt++;
				}
			}
		}
		BlankCnt = N * N - BlankCnt; // 바이러스가 퍼져야 하는 칸 수 기억
		// 입력
		
		// 초기에 바이러스 놓을 조합 구하기
		combi(0, new Loc[M], 0, 0);
		
		if(Min == Integer.MAX_VALUE) {
			System.out.println(-1);
		}else {
			System.out.println(Min);
		}
	}
}