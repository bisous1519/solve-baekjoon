import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	static int N; // 마을 크기
	static int M; // 초기 체력
	static int H; // 민초 먹으면 증가 체력
	static int R, C; // 진우 집 위치(1)
	static List<Mincho> MinchoL = new ArrayList<>(); // 모든 민초의 위치
	static int mCnt; // 민초 총 개수
	static int[][] Map; // 마을 지도
	static boolean[][] isVisited;
	static int Max = 0;
	
	static class Mincho {
		int r;
		int c;
		public Mincho(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}
	
	public static void permutation(int nth, int[] choosed, boolean[] isVisited) {
		if(nth == mCnt) {
			// 순열 완성!
			// 순열의 순서대로 민초 먹으러 방문!
			int h = M; // 초기체력
			int cnt = 0; // 먹은 민초 개수
			int move = 0; // 움직인 거리 누적(어떤 시점에서 집 돌아가는데 드는 체력)
			int jR = R; // 현재 진우의 위치
			int jC = C;
			for(int i=0; i<mCnt; i++) {
				int mR = MinchoL.get(choosed[i]).r;
				int mC = MinchoL.get(choosed[i]).c;
				
				// 현재 남은 체력으로 갈 수 있는 곳인지 확인
				int dis = Math.abs(jR-mR) + Math.abs(jC-mC);
				if(dis <= h) {
					h -= dis; // 가면서 체력 감소
					h += H; // 민초먹고 체력 증가
					cnt++; // 민초 먹음++
					
					// 민초자리로 가
					jR = mR;
					jC = mC;
					
					// 현재 남은 체력으로 집 돌아간다면?
					if(Math.abs(R-mR) + Math.abs(C-mC) <= h) {
						Max = Math.max(Max, cnt);
					}
				}
			}
			return;
		}
		
		for(int i=0; i<mCnt; i++) {
			if(!isVisited[i]) {
				isVisited[i] = true;
				choosed[nth] = i;
				permutation(nth + 1, choosed, isVisited);
				isVisited[i] = false;
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		H = Integer.parseInt(tokens.nextToken());
		Map = new int[N][N];
		isVisited = new boolean[N][N];
		for(int r=0; r<N; r++) {
			tokens = new StringTokenizer(input.readLine());
			for(int c=0; c<N; c++) {
				Map[r][c] = Integer.parseInt(tokens.nextToken());
				if(Map[r][c] == 2) { // 민초 위치!
					MinchoL.add(new Mincho(r, c));
				} else if(Map[r][c] == 1) { // 진우 집 위치!
					R = r;
					C = c;
				}
			}
		}
		
		// 민초 리스트를 순열돌리기
		mCnt = MinchoL.size();
		permutation(0, new int[mCnt], new boolean[mCnt]);
		System.out.println(Max);
	}
}