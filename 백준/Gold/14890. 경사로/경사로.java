import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	static int N;
	static int L;
	static int[][] Map;
	static boolean[] isSlide;
	static int ans;
	static int[] dy = {1, 0}; // 하, 우
	static int[] dx = {0, 1};
	
	public static boolean isIn(int r, int c) {
		return 0<=r && r<N && 0<=c && c<N;
	}
	
	public static boolean isPossible(int r, int c, int dir, int updown) {
		int h = Map[r][c];
		int cr = r;
		int cc = c;
		int sIdx = dir == 0 ? cr : cc;
		for(int i=1; i<=L; i++) {
			if(!isIn(cr, cc)) { // 장 외
				return false;
			}
			if(isSlide[sIdx]) { // 이미 경사로 둔 곳
				return false;
			}
			if(Map[cr][cc] != h) { // 평평하지 x
				return false;
			}
			
			isSlide[sIdx] = true;
			sIdx += updown;
			cr += (dy[dir] * updown);
			cc += (dx[dir] * updown);
		}
		return true;
	}
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		L = Integer.parseInt(tokens.nextToken());
		Map = new int[N][N];
		for(int r=0; r<N; r++) {
			tokens = new StringTokenizer(input.readLine());
			for(int c=0; c<N; c++) {
				Map[r][c] = Integer.parseInt(tokens.nextToken());
			}
		} // 입력
		
		final int down = 1; // 내리막길
		final int up = -1; // 오르막길
		final int col = 1;
		final int row = 0;
		
		// 오른쪽으로 길
		for(int r=0; r<N; r++) {
			boolean flag = true;
			int cnt = L;
			isSlide = new boolean[N];
			for(int c=1; c<N; c++) {
				
				// 이전과 높이가 같으면 문제x
				if(Map[r][c-1] == Map[r][c]) {
					continue;
				}
				
				// 경사가 생기면! (단, 1층차이)
				if(Map[r][c-1] == Map[r][c] + 1) { // 내리막
					if(!isPossible(r, c, col, down)) {
						flag = false;
						break;
					}
					c += (L - 1);
				}
				else if(Map[r][c-1] == Map[r][c] - 1) { // 오르막
					if(!isPossible(r, c - 1, col, up)) {
						flag = false;
						break;
					}
				}
				
				// 2층 이상의 경사
				else {
					flag = false;
					break;
				}
			}
			if(flag) {
				ans++;
			}
		}
		
		// 아래쪽으로 길
		for(int c=0; c<N; c++) {
			boolean flag = true;
			int cnt = L;
			isSlide = new boolean[N];
			for(int r=1; r<N; r++) {
				
				// 이전과 높이가 같으면 문제x
				if(Map[r-1][c] == Map[r][c]) {
					continue;
				}
				
				// 경사가 생기면! (단, 1층차이)
				if(Map[r-1][c] == Map[r][c] + 1) { // 내리막
					if(!isPossible(r, c, row, down)) {
						flag = false;
						break;
					}
					r += (L - 1);
				}
				else if(Map[r-1][c] == Map[r][c] - 1) { // 오르막
					if(!isPossible(r - 1, c, row, up)) {
						flag = false;
						break;
					}
				}
				
				// 2층 이상의 경사
				else {
					flag = false;
					break;
				}
			}
			if(flag) {
				ans++;
			}
		}
		
		System.out.println(ans);
	}
}