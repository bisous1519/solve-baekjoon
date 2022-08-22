import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();
	
	static int R;
	static int C;
	static int N;
	static int[][] Map;
	static int[] dy = {-1, 0, 1, 0}; // 상 우 하 좌
	static int[] dx = {0, 1, 0, -1};
	
	public static boolean isIn(int r, int c) {
		return 0<=r && r<R && 0<=c && c<C;
	}

	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		R = Integer.parseInt(tokens.nextToken());
		C = Integer.parseInt(tokens.nextToken());
		N = Integer.parseInt(tokens.nextToken());
		Map = new int[R][C];
		for(int r=0; r<R; r++) {
			String str = input.readLine();
			for(int c=0; c<C; c++) {
				if(str.charAt(c) == '.') {
					Map[r][c] = -1;	// 빈칸 : -1
				}else {
					Map[r][c] = 1;	// 1초 된 폭탄으로 입력받기 : 1
				}
			}
		}
		
		// 2초 ~ N초
		for(int n=2; n<=N; n++) {
			// 모든 폭탄들 +1초
			for(int r=0; r<R; r++) {
				for(int c=0; c<C; c++) {
					// 빈칸 -> 폭탄 설치 / 0초 -> 1초 / 1초 -> 2초 / 2초 -> 3초
					Map[r][c]++; 
				}
			}
			// 3초 된 폭탄 터뜨리기
			for(int r=0; r<R; r++) {
				for(int c=0; c<C; c++) {
					if(Map[r][c] == 3) {
						Map[r][c] = -1;
						for(int d=0; d<dy.length; d++) {
							int goR = r + dy[d];
							int goC = c + dx[d];
							if(isIn(goR, goC) && Map[goR][goC] != 3) { // 3이면 이번에 터지는 애라 무시
								Map[goR][goC] = -1;
							}

						}
					}
				}
			}
		}
		
		// 출력
		for(int r=0; r<R; r++) {
			for(int c=0; c<C; c++) {
				if(Map[r][c] == -1) {	// 빈칸
					output.append(".");
				}else {					// 폭탄
					output.append("O");
				}
			}
			output.append("\n");
		}
		System.out.println(output);
	}
}