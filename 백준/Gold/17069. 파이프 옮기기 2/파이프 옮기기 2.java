import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	private static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	private static StringTokenizer tokens;

	static int N;
	static int[][] Map;
	static long[][][] memo; // (r, c)에 d로 접근할 수 있는 경우의 수 : [r][c][d]
	static final int HOR = 0; // 가로
	static final int VER = 1; // 세로
	static final int DIA = 2; // 대각선

	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(input.readLine());
		Map = new int[N + 1][N + 1];
		for (int r = 1; r <= N; r++) {
			tokens = new StringTokenizer(input.readLine());
			for (int c = 1; c <= N; c++) {
				Map[r][c] = Integer.parseInt(tokens.nextToken());
			}
		}
		// 입력

		// 초기화
		memo = new long[N+1][N+1][3];
		for(int r=2; r<=N; r++) {
			if(Map[1][r] == 1) {
				break;
			}
			memo[1][r][HOR] = 1;
		}
		
		// dp!
		for(int r=2; r<=N; r++) {
			for(int c=3; c<=N; c++) {
				if(Map[r][c] != 1) {
					// 이번에 놓일 파이프가 가로
					memo[r][c][HOR] = memo[r][c-1][HOR] + memo[r][c-1][DIA];
				
					// 이번에 놓일 파이프가 세로
					memo[r][c][VER] = memo[r-1][c][VER] + memo[r-1][c][DIA];

					if(Map[r-1][c] != 1 && Map[r][c-1] != 1) {
						// 이번에 놓일 파이프가 대각선
						memo[r][c][DIA] = memo[r-1][c-1][HOR] + memo[r-1][c-1][VER] + memo[r-1][c-1][DIA];
					}
				}
			}
		}
		
		long result = memo[N][N][HOR] + memo[N][N][VER] + memo[N][N][DIA];
		System.out.println(result);
	}
}