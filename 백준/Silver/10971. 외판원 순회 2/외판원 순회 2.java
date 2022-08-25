import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	static int N;
	static int Min;
	static int[][] Map;	
	
	static int calc(int[] Value) {
		int totalsum = 0;
		for(int i = 1; i < N ; i++) {
			int temp = Map[Value[i-1]][Value[i]];
			if(temp == 0) {
				return Integer.MAX_VALUE;
			}
			totalsum += temp;
		}	
		int temp = Map[Value[N-1]][Value[0]];
		if(temp == 0) {
			return Integer.MAX_VALUE;
		}
		totalsum += temp;
		return totalsum;
	}
	
	static void permu(int countnow, int BM, int [] Value) {
		if(countnow == N) {
			Min = Math.min(calc(Value), Min);
			return;
		}
		for(int i = 0; i < N ; i++) {
			if((BM & 1<<i) != 0) {
				continue;
			}
			BM |= 1<<i;
			Value[countnow] = i;
			permu(countnow + 1, BM , Value);
			BM &= ~(1<<i);
		}
	}
   
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		Map = new int [N][N];
		for(int i = 0; i < N ; i++) {
			tokens = new StringTokenizer(input.readLine());
			for(int j = 0; j < N; j++) {
				Map[i][j] = Integer.parseInt(tokens.nextToken());
			}
		}
		
		Min = Integer.MAX_VALUE;
		permu(0, 0, new int [N]);
		System.out.println(Min);
	}
}