import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();
	
	static int N;
	static final int R = 6;
	static final int C = 4; 
	static boolean[][] MapG = new boolean[R][C]; // 초록보드
	static boolean[][] MapB = new boolean[R][C]; // 블루보드
	static int[][] cnt = new int[R][2]; // 초록[][G] 각 행, 블루[][B] 각 열에 있는 블록개수
	static int score; // 점수 
	static final int G = 0; // cnt배열의 열
	static final int B = 1;
	
	public static void newBlock(int t, int x, int y, int gb, boolean[][] Map) {
		// 지금블럭 두는 열에서 몇행에 다른 블럭 있는지 찾고
		int toR = -1; // 이 블럭이 자리잡게 될 행
		for(int r=0; r<R; r++) {
			if(t == 1) {
				if(Map[r][y]) {
					toR = r - 1;
					break;
				}
			}else if(t == 2) {
				if(Map[r][y] || Map[r][y+(gb*(-2)+1)]) {
					toR = r - 1;
					break;
				}
			}else if(t == 3) {
				if(Map[r][y]) {
					toR = r - 2;
					break;
				}
			}
		}
		if(toR == -1) { // 맵 끝행에 둠!
			toR = R - 1;
			if(t == 3) toR--;
		}
		// 맵에 블럭두기
		Map[toR][y] = true;
		cnt[toR][gb]++;
		if(t == 2) {
			Map[toR][y+(gb*(-2)+1)] = true;
			cnt[toR][gb]++;
		}
		if(t == 3) {
			Map[toR+1][y] = true;
			cnt[toR+1][gb]++;
		}
		
		// 꽉 찬 행 있으면 처리
		for(int r=R-1; r>=0; r--) {
			if(cnt[r][gb] == 0) { // 이 위는 더이상 블록 없음
				break;
			}
			if(cnt[r][gb] == C) { // 꽉 찬 행!
				for(int i=r; i>0; i--) {
					for(int j=0; j<C; j++) {
						Map[i][j] = Map[i-1][j];
						Map[i-1][j] = false;
					}
					cnt[i][gb] = cnt[i-1][gb];
					cnt[i-1][gb] = 0;
				}
				score++;
				r++;
			}
		}
		
		// 특별칸에 블록 있으면 처리
		toR = R - 1; // 아래부터 몇번 행 까지 없애야 하는지
		if(cnt[1][gb] > 0) toR--;
		if(cnt[0][gb] > 0) toR--;
		if(toR != R - 1) { // 특별칸에 블록 있음
			for(int r=R-1; toR>=0; r--, toR--) { // 밑행부터 없애서 내리자
				for(int c=0; c<C; c++) {
					Map[r][c] = Map[toR][c];
					Map[toR][c] = false;
				}
				cnt[r][gb] = cnt[toR][gb];
				cnt[toR][gb] = 0;
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(input.readLine());
		
		// 블록 놓으러 감
		for(int n=0; n<N; n++) {
			tokens = new StringTokenizer(input.readLine());
			int t = Integer.parseInt(tokens.nextToken());
			int x = Integer.parseInt(tokens.nextToken());
			int y = Integer.parseInt(tokens.nextToken());
			
			// 초록보드에 블록 보내고
			newBlock(t, x, y, G, MapG);
			
			// 블루보드에 블록 보내고 (t: 2 -> 3, 3 -> 2 / x,y 바꿔서 보냄)
			newBlock(t == 1 ? t : t == 2 ? 3 : 2, y, C-1-x, B, MapB);
		}
		
		output.append(score + "\n");
		
		int sum = 0;
		for(int r=0; r<R; r++) {
			sum += cnt[r][0] + cnt[r][1];
		}
		output.append(sum);
		System.out.println(output);
	}
}