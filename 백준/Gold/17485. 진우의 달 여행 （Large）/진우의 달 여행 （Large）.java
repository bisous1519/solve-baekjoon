// **
import java.io.*;
import java.util.*;

public class Main {
	static int n, m;						// n행 m열
	static int[][] map;
	static int[][][] dp;
	static int minCost = Integer.MAX_VALUE;	// 출력, 최소 비용

	static void solution() {
		for (int i = 1; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (isValid(i - 1, j + 1))
					dp[i][j][0] = Math.min(dp[i-1][j+1][1], dp[i-1][j+1][2]) + map[i][j];

				if (isValid(i - 1, j))
					dp[i][j][1] = Math.min(dp[i-1][j][0], dp[i-1][j][2]) + map[i][j];

				if (isValid(i - 1, j - 1))
					dp[i][j][2] = Math.min(dp[i-1][j-1][0], dp[i-1][j-1][1]) + map[i][j];
			}
		}

		// dp 배열의 마지막 행 원소들 중, minCost 찾기
		for (int j = 0; j < m; j++) {
			for (int k = 0; k < 3; k++) {
				if (minCost > dp[n-1][j][k])
					minCost = dp[n-1][j][k];
			}
		}
	}

	static boolean isValid(int y, int x) {
		return (0 <= y && y < n) &&
				(0 <= x && x < m);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());

		map = new int[n][m];
		dp = new int[n][m][3];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());

			for (int j = 0; j < m; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());

				if (i == 0) {
					// 초기식 ①: 첫 행 dp[0][j][k] = map[0][j]
					dp[i][j][0] = map[i][j];
					dp[i][j][1] = map[i][j];
					dp[i][j][2] = map[i][j];
				}
				else if (j == m - 1) {
					// 초기식 ②: 오른쪽 끝 칸 [i][m-1] 으로 못 오는 경우
					dp[i][j][0] = Integer.MAX_VALUE;
				}
				else if (j == 0) {
					// 초기식 ③: 왼쪽 끝 칸 [i][0] 으로 못 오는 경우
					dp[i][j][2] = Integer.MAX_VALUE;
				}
			}
		}

		solution();
		System.out.println(minCost);
	}
}