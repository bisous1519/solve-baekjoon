import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();
	
	static int R;
	static int C;
	static char[][] Map;
	static int MR, MC, ZR, ZC;
	static int blankR, blankC;
	static int fromM = -1; // M에서부터 찾아온 방향
	static int fromZ = -1; // Z에서부터 찾아온 방향
	static int[] dy = {-1, 0, 1, 0}; // 상 우 하 좌
	static int[] dx = {0, 1, 0, -1};
	static int[][] dir = {{},
						  {1, -1, -1, 2},
						  {-1, -1, 1, 0},
						  {-1, 0, 3, -1},
						  {3, 2, -1, -1},
						  {0, -1, 2, -1},
						  {-1, 1, -1, 3},
						  {0, 1, 2, 3}};
	
	public static boolean isIn(int r, int c) {
		return 0<=r && r<R && 0<=c && c<C;
	}
	
	public static void flow(int pre, int cr, int cc) {
		int next = -1;
		
		switch(Map[cr][cc]) {
		case '1': next = dir[1][pre]; break;
		case '2': next = dir[2][pre]; break;
		case '3': next = dir[3][pre]; break;
		case '4': next = dir[4][pre]; break;
		case '|': next = dir[5][pre]; break;
		case '-': next = dir[6][pre]; break;
		case '+': next = dir[7][pre]; break;
		default: break; // 빈칸!
		}
		
		if(next == -1) { // 빈칸!
			blankR = cr;
			blankC = cc;
			fromM = fromM == -1 ? pre : fromM;
			fromZ = fromM == -1 ? fromZ : pre;
			return;
		}
		
		flow(next, cr + dy[next], cc + dx[next]);
	}
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		R = Integer.parseInt(tokens.nextToken());
		C = Integer.parseInt(tokens.nextToken());
		Map = new char[R][C];
		for(int r=0; r<R; r++) {
			Map[r] = input.readLine().toCharArray();
		}
		
		// M, Z 위치 찾기
		for(int r=0; r<R; r++) {
			for(int c=0; c<C; c++) {
				if(Map[r][c] == 'M') {
					MR = r;
					MC = c;
				}else if(Map[r][c] == 'Z') {
					ZR = r;
					ZC = c;
				}
			}
		}
		
		// M에서 출발해서 빈칸 찾기
		for(int d=0; d<dy.length; d++) {
			int goR = MR + dy[d];
			int goC = MC + dx[d];
			if(isIn(goR, goC) && Map[goR][goC] != '.' && Map[goR][goC] != 'Z') {
				flow(d, goR, goC);
				break;
			}
		}
		
		// Z에서 출발해서 빈칸 찾기
		for(int d=0; d<dy.length; d++) {
			int goR = ZR + dy[d];
			int goC = ZC + dx[d];
			if(isIn(goR, goC) && Map[goR][goC] != '.' && Map[goR][goC] != 'M') {
				flow(d, goR, goC);
				break;
			}
		}
		
		// 빈칸에 들어갈 파이프 찾기
		// 특별 파이프인지 확인
		int cnt = 0; // 빈칸 주변 사방에 파이프 몇개있는지 확인
		for(int d=0; d<dy.length; d++) {
			int goR = blankR + dy[d];
			int goC = blankC + dx[d];
			if(isIn(goR, goC) && Map[goR][goC] != '.' && Map[goR][goC] != 'M' && Map[goR][goC] != 'Z') {
				cnt++;
			}
		}
		if(cnt == 4) {
			// 특별 파이프 o
			System.out.println((blankR+1) + " " + (blankC+1) + " +");
			return;
		}
		
		// 특별 파이프 x
		for(int r=1; r<=6; r++) {
			if(dir[r][fromM] != -1 && dir[r][fromZ] != -1) {
				char shape = '.';
				switch(r) {
				case 1: shape = '1'; break;
				case 2: shape = '2'; break;
				case 3: shape = '3'; break;
				case 4: shape = '4'; break;
				case 5: shape = '|'; break;
				case 6: shape = '-'; break;
				}
				
				System.out.println((blankR+1) + " " + (blankC+1) + " " + shape);
				return;
			}
		}
	}
}