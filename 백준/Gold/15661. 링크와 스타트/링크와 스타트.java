import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	static int N;
	static int[][] Map;
	static int Min = Integer.MAX_VALUE;
	
	public static void team(int nth, boolean[] choosed, int cntA) {
		if(nth == N) {
			if(cntA == 0 || cntA == N) {
				return;
			}
			
			int cntB = N - cntA;
			int[] teamA = new int[cntA];
			int[] teamB = new int[cntB];
			int a = 0;
			int b = 0;
			for(int i=0; i<N; i++) {
				if(choosed[i]) {
					teamA[a++] = i;
				}else {
					teamB[b++] = i;
				}
			}
			
			int sumA = 0;
			for(int i=0; i<cntA-1; i++) {
				for(int j=i+1; j<cntA; j++) {
					sumA += Map[teamA[i]][teamA[j]];
					sumA += Map[teamA[j]][teamA[i]];
				}
			}
			
			int sumB = 0;
			for(int i=0; i<cntB-1; i++) {
				for(int j=i+1; j<cntB; j++) {
					sumB += Map[teamB[i]][teamB[j]];
					sumB += Map[teamB[j]][teamB[i]];
				}
			}
			
			Min = Math.min(Min, Math.abs(sumA - sumB));
			return;
		}
		
		choosed[nth] = true;
		team(nth + 1, choosed, cntA + 1);
		choosed[nth] = false;
		team(nth + 1, choosed, cntA);
	}
	
	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(input.readLine());
		Map = new int[N][N];
		for(int r=0; r<N; r++) {
			tokens = new StringTokenizer(input.readLine());
			for(int c=0; c<N; c++) {
				Map[r][c] = Integer.parseInt(tokens.nextToken());
			}
		} // 입력
		
		team(0, new boolean[N], 0);
		System.out.println(Min);
	}
}