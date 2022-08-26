import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	static int R;
	static int C;
	static int T; // 초
	static int[][] Map;
	static int[][] MapTemp;
	static Airc airc = new Airc();
	static List<Loc> misae = new ArrayList<>();
	static Queue<Integer> queue;
	static int tmp; // 완전 임시 변수
	static int[] dy = {-1, 0, 1, 0}; // 상 우 하 좌
	static int[] dx = {0, 1, 0, -1};
	
	static class Airc {
		int r1;
		int r2;
	}
	
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
	
	// 확산 : 원본 참고해서 MapTemp 배열에 미세먼지 퍼진상황 저장
	public static void spread() {
		for(int i=0; i<misae.size(); i++) {
			Loc cur = misae.get(i);
			int per1 = Map[cur.r][cur.c] / 5; // 한 칸당 퍼지는 양
			int cnt = 0; // 몇 칸에 퍼졌는지
			if(per1 != 0) {
				for(int d=0; d<dy.length; d++) {
					int goR = cur.r + dy[d];
					int goC = cur.c + dx[d];
					
					// 장외면 안됨
					if(!isIn(goR, goC)) {
						continue;
					}
					
					// 공기청정기 자리면 안됨
					if(goC == 0 && (goR == airc.r1 || goR == airc.r2)) {
						continue;
					}
					
					 // 4방으로 미세 확산 
					MapTemp[goR][goC] += per1;
					cnt++;
				}
			}
			MapTemp[cur.r][cur.c] += (Map[cur.r][cur.c] - (per1 * cnt)); // 확산된 원본자리에 남은 미세 누적
		}
	}
	
	// 순환 : MapTemp 배열에 공기청정기 돌림
	public static void aircOn(int r, int c, String dir, boolean isFin) {
		// delta 배열 인덱스 초기화, 증감설정
		int cr = r;
		int cc = c;
		int dS = dir.equals("up") ? 0 : dy.length - 1;
		int dE = dir.equals("up") ? dy.length : -1;
		int dPM = dir.equals("up") ? 1 : -1;
		
		// 큐에 쭉 넣고
		while(dS != dE) {
			// delta 바뀌는 조건
			if(!isIn(cr + dy[dS], cc + dx[dS])
					|| (dir.equals("up") && dS == 2 && cr == airc.r1)
					|| (dir.equals("down") && dS == 0 && cr == airc.r2)) {
				dS += dPM;
				continue;
			}
			
			cr += dy[dS];
			cc += dx[dS];
			if(!isFin) queue.offer(MapTemp[cr][cc]); // 뺄 때
			else if(!queue.isEmpty()) MapTemp[cr][cc] = queue.poll(); // 뿌릴 때
		}
		
		if(isFin) return; // 뿌리고 끝
		
		// 한칸 밀려서 쭉 뿌림
		tmp = queue.poll();
		aircOn(r, c, dir, true); // 뿌리러 감!
	}
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		R = Integer.parseInt(tokens.nextToken());
		C = Integer.parseInt(tokens.nextToken());
		T = Integer.parseInt(tokens.nextToken());
		Map = new int[R][C];
		for(int r=0; r<R; r++) {
			tokens = new StringTokenizer(input.readLine());
			for(int c=0; c<C; c++) {
				Map[r][c] = Integer.parseInt(tokens.nextToken());
				if(Map[r][c] == -1) { // 공기청정기 위치 저장
					if(airc.r1 == 0) {
						airc.r1 = r;
					}else {
						airc.r2 = r;
					}
				}else if(Map[r][c] != 0) { // 미세먼지 위치 저장
					misae.add(new Loc(r, c));
				}
			}
		}
		
		// 초시계 째깍째깍
		for(int t=1; t<=T; t++) {
			MapTemp = new int[R][C]; // MapTemp 배열 초기화
			
			// 미세미세 확산
			spread();
			
			// 공기청정기 윗 공기 순환
			queue = new LinkedList<>();
			aircOn(airc.r1, 0, "up", false); // 공청돌림
			MapTemp[airc.r1][1] = 0;
			MapTemp[airc.r1][0] = -1;
			
			// 공기청정기 아랫 공기 순환
			queue = new LinkedList<>();
			aircOn(airc.r2, C - 1, "down", false); // 공청돌림
			MapTemp[airc.r2][1] = 0;
			MapTemp[airc.r2][0] = -1;
			MapTemp[airc.r2][C-1] = tmp;
			
			// 배열 복사 : 원본배열을 MapTemp로 바꾸고, 미세위치 새로 저장
			misae = new ArrayList<>();
			for(int r=0; r<R; r++) {
				for(int c=0; c<C; c++) {
					Map[r][c] = MapTemp[r][c];
					if(Map[r][c] > 0) {
						misae.add(new Loc(r, c));
					}
				}
			}
		}
		
		// 남아있는 미세먼지 양!
		int sum = 0;
		for(int r=0; r<R; r++) {
			for(int c=0; c<C; c++) {
				if(Map[r][c] != -1) {
					sum += Map[r][c];
				}
			}
		}
		
		// 출력
		System.out.println(sum);
	}
}