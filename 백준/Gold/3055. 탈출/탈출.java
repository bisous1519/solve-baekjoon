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
	static Co start;
	static Co end;
	static String ans;
	static char[][] Map;
	static Queue<Co> water = new LinkedList<>();
	static Queue<Co> dochi = new LinkedList<>();
	static int[] dy = {-1, 0, 1, 0}; // 상, 우, 하, 좌
	static int[] dx = {0, 1, 0, -1};
	
	public static class Co {
		int r;
		int c;
		int min; // 몇분대의 좌표인가
		public Co(int r, int c, int min) {
			this.r = r;
			this.c = c;
			this.min = min;
		}
	}
	
	public static boolean isIn(int r, int c) {
		return 0<=r && r<R && 0<=c && c<C;
	}
	
	public static void bfs() {
		int min = 0;
		
		// 물 퍼지고, 고슴도치 이동경로 넣고 반복
		while(true) {
			if(dochi.isEmpty()) { // 고슴도치가 더 이상 갈 곳이 없음
				ans = "KAKTUS";
				return;
			}
			
			min++;
			
			// 물 퍼지기
			while(!water.isEmpty()) {
				if(water.peek().min == min - 1) { // 이번에 퍼져야하는 물
					Co curWater = water.poll();
					for(int d=0; d<dy.length; d++) {
						int goR = curWater.r + dy[d];
						int goC = curWater.c + dx[d];
						if(isIn(goR, goC)) {
							if(Map[goR][goC] == '.' || Map[goR][goC] == 'S') { // 빈곳, S의 잔상에 갈 수 있음
								Map[goR][goC] = '*';
								water.offer(new Co(goR, goC, min));
							}
						}
					}
				}else {
					break;
				}
			}
			
			// 고슴도치 이동
			while(!dochi.isEmpty()) {
				if(dochi.peek().min == min - 1) { // 1분 전 위치
					Co curDochi = dochi.poll();
					for(int d=0; d<dy.length; d++) {
						int goR = curDochi.r + dy[d];
						int goC = curDochi.c + dx[d];
						if(isIn(goR, goC)) {
							if(Map[goR][goC] == 'D') { // 도착!!
								ans = Integer.toString(min);
								return;
							}
							if(Map[goR][goC] == '.') {
								Map[goR][goC] = 'S';
								dochi.add(new Co(goR, goC, min));
							}
						}
					}
				}else {
					break;
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		R = Integer.parseInt(tokens.nextToken());
		C = Integer.parseInt(tokens.nextToken());
		Map = new char[R][C];
		for(int r=0; r<R; r++) {
			Map[r] = input.readLine().toCharArray();
		}
		
		// S와 D위치, 물위치 저장
		for(int r=0; r<R; r++) {
			for(int c=0; c<C; c++) {
				if(Map[r][c] == '*') { // 물
					water.offer(new Co(r, c, 0));
				}else if(Map[r][c] == 'S') { // S
					dochi.offer(new Co(r, c, 0));
				}else if(Map[r][c] == 'D') { // D
					end = new Co(r, c, 0);
				}
			}
		}
		
		// 매 분 물차고 -> 고슴도치 이동
		bfs();
		System.out.println(ans);
	}
}