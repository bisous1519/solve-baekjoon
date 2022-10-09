import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();

	static int N, M;
	static boolean[][] Map;
	static int result;

	public static void fw() {
		for (int c = 1; c <= N; c++) {
			for (int a = 1; a <= N; a++) {
				for (int b = 1; b <= N; b++) {
					if(Map[a][b]) {
						continue;
					}
					if(Map[a][c] && Map[c][b]) {
						Map[a][b] = true;
					}
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		Map = new boolean[N + 1][N + 1];
		for (int m = 0; m < M; m++) {
			tokens = new StringTokenizer(input.readLine());
			int a = Integer.parseInt(tokens.nextToken());
			int b = Integer.parseInt(tokens.nextToken());

			Map[a][b] = true;
		} // 입력
		
		// 바로 연결되지 않고 경유해서 연결되는 애들도 체크해주기
		fw();
		
		for(int n=1; n<=N; n++) {
			int cnt = 0;
			for(int i=1; i<=N; i++) {
				if(Map[n][i]) cnt++;
				if(Map[i][n]) cnt++;
			}
			
			if(cnt == N-1) {
				result++;
			}
		}
		
		System.out.println(result);
	}
}