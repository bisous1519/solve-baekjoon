import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Main {

	private static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	private static StringTokenizer tokens;
	private static StringBuilder output = new StringBuilder();
	
	private static int N;
	private static int M;
	private static int R;
	private static int[][] Map;
	
	public static void roll(int base) {
		for(int i=0; i<R; i++) {
			int baseNum = Map[base][base];
			// 맨 윗줄
			for(int j=base; j<=M-base; j++) {
				Map[base][j] = Map[base][j+1];
			}
			// 오른쪽 사이드
			for(int j=base; j<=N-base; j++) {
				Map[j][M-base+1] = Map[j+1][M-base+1];
			}
			// 맨 아랫줄
			for(int j=M-base+1; j>=base+1; j--) {
				Map[N-base+1][j] = Map[N-base+1][j-1];
			}
			// 왼쪽 사이드
			for(int j=N-base+1; j>=base+1; j--) {
				Map[j][base] = Map[j-1][base];
			}
			Map[base+1][base] = baseNum;
		}
	}
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		R = Integer.parseInt(tokens.nextToken());
		Map = new int[N+1][M+1];
		for(int r=1; r<=N; r++) {
			tokens = new StringTokenizer(input.readLine());
			for(int c=1; c<=M; c++) {
				Map[r][c] = Integer.parseInt(tokens.nextToken());
			}
		}
		for(int i=1; i<=Math.min(N, M)/2; i++) {
			roll(i);
		}
		for(int r=1; r<=N; r++) {
			for(int c=1; c<=M; c++) {
				output.append(Map[r][c]).append(" ");
			}
			output.append("\n");
		}
		System.out.println(output);
	}
}