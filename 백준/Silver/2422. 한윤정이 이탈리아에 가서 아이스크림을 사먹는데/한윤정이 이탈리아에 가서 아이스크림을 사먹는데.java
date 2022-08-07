import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Main {
	
	private static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	private static StringTokenizer tokens;
	
	private static int N;
	private static int M;
	private static boolean[][] noCombi;
	private static int totalCnt;
	
	public static void combination(int idx, int[] choosed, int startNum) {
		if(idx == choosed.length) {
			totalCnt++;
			return;
		}
		for(int i=startNum; i<=N; i++) {
			choosed[idx] = i;
			// 피하는 조합에 걸리지 않는 경우에만 해당 수 choosed에 넣기
			if(!noCombi[choosed[0]][choosed[1]]
				&& !noCombi[choosed[0]][choosed[2]]
				&& !noCombi[choosed[1]][choosed[2]]) {
				combination(idx + 1, choosed, i + 1);
			}
			choosed[idx] = 0;
		}
	}
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		noCombi = new boolean[N+1][N+1];
		for(int i=0; i<M; i++) {
			tokens = new StringTokenizer(input.readLine());
			int tmp1 = Integer.parseInt(tokens.nextToken());
			int tmp2 = Integer.parseInt(tokens.nextToken());
			noCombi[tmp1][tmp2] = true;
			noCombi[tmp2][tmp1] = true;
		}
		combination(0, new int[3], 1);
		System.out.println(totalCnt);
	}
}