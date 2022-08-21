import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	
	static int N = 5;
	static int Seven = 7;
	static int ans;
	static int cnt;
	static char[][] Map = new char[N][N];
	static int[] dy = {-1, 0, 1, 0}; // 상 우 하 좌
	static int[] dx = {0, 1, 0, -1};
	
	public static boolean isIn(int r, int c) {
		return 0<=r && r<N && 0<=c && c<N;
	}
	
	public static void dfs(int r, int c, boolean[][] choosed, boolean[][] isVisited) {
		for(int d=0; d<dy.length; d++) {
			int goR = r + dy[d];
			int goC = c + dx[d];
			if(isIn(goR, goC) && choosed[goR][goC] && !isVisited[goR][goC]) {
				cnt++;
				isVisited[goR][goC] = true;
				dfs(goR, goC, choosed, isVisited);
			}
		}
	}
	
	public static void combi(int nth, int startR, int startC, int cntY, boolean[][] choosed) {
		if(nth == Seven) {
			// 뽑은 애들이 다 인접해있는지 확인
			outer : 
			for(int r=0; r<N; r++) {
				for(int c=0; c<N; c++) {
					if(choosed[r][c]) { // 첫번째 자리에 있는 놈 기준으로 dfs돌리기
						boolean[][] isVisited = new boolean[N][N];
						isVisited[r][c] = true;
						cnt = 1;
						dfs(r, c, choosed, isVisited);
						if(cnt == Seven) { // 7명모두 인접이면 답++!!
							ans++;
						}
						break outer;
					}
				}
			}
			// 어쨌든 저쨌든 7명 뽑았으니까 return
			return;
		}
		
		for(int r=startR; r<N; r++) {
			for(int c=startC; c<N; c++) {
				// Y조건 확인
				if(cntY == 3 && Map[r][c] == 'Y') {
					continue;
				}
				
				// 선택!
				choosed[r][c] = true;
				combi(nth + 1,
					  c == N-1 ? r + 1 : r,
					  c == N-1 ? 0 : c + 1,
					  Map[r][c] == 'Y' ? cntY + 1 : cntY,
					  choosed);
				choosed[r][c] = false;
			}
			startC = 0; // 열을 다 돌고 다음 행 돌때는 0부터 시작
		}
	}
	
	public static void main(String[] args) throws IOException {
		for(int r=0; r<N; r++) {
			Map[r] = input.readLine().toCharArray();
		}
		
		combi(0, 0, 0, 0, new boolean[N][N]);
		System.out.println(ans);
	}
}