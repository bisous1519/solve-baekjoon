import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	static int C, P;
	static int[] Map;
	static int[][][] shape = {{{0}, {0, 0, 0, 0}},
							  {{0, 0}},
							  {{0, 0, 1}, {1, 0}},
							  {{1, 0, 0}, {0, 1}},
							  {{0, 0, 0}, {0, 1}, {1, 0, 1}, {1, 0}},
							  {{0, 0, 0}, {0, 0}, {0, 1, 1}, {2, 0}},
							  {{0, 0, 0}, {0, 2}, {1, 1, 0}, {0, 0}}};

	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		C = Integer.parseInt(tokens.nextToken());
		P = Integer.parseInt(tokens.nextToken()) - 1;
		Map = new int[C];
		tokens = new StringTokenizer(input.readLine());
		for(int c=0; c<C; c++) {
			Map[c] = Integer.parseInt(tokens.nextToken());
		} // 입력
		
		int cnt = 0;
		for(int i=0; i<shape[P].length; i++) {
			for(int j=0; j<=C-shape[P][i].length; j++) {
				int temp = Map[j] - shape[P][i][0];
				boolean flag = true;
				for(int k=1; k<shape[P][i].length; k++) {
					if(Map[j+k] - shape[P][i][k] != temp) {
						flag = false;
						break;
					}
				}
				
				if(flag) {
					cnt++;
				}
			}
		}
		
		System.out.println(cnt);
	}
}