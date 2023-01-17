import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	static int N; // 도시의 수
	static int M; // 여행 계획한 도시의 수
	static int[][] Metrix;

	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(input.readLine());
		M = Integer.parseInt(input.readLine());
		Metrix = new int[N+1][N+1];
		for(int r=1; r<=N; r++) {
			tokens = new StringTokenizer(input.readLine());
			for(int c=1; c<=N; c++) {
				Metrix[r][c] = Integer.parseInt(tokens.nextToken());
			}
			Metrix[r][r] = 1;
		}
		
		for(int i=1; i<=N; i++) {
			for(int j=1; j<=N; j++) {
				for(int k=1; k<=N; k++) {
					if(Metrix[i][j] == 1 && Metrix[j][k] == 1) {
						Metrix[i][k] = 1;
						Metrix[k][i] = 1;
					}
				}
			}
		}
		
		tokens = new StringTokenizer(input.readLine());
		int firstArea = Integer.parseInt(tokens.nextToken());
		for(int m=1; m<M; m++) {
			int curArea = Integer.parseInt(tokens.nextToken());
			if(Metrix[firstArea][curArea] == 0) {
				System.out.println("NO");
				return;
			}
		}
		
		System.out.println("YES");
	}
}