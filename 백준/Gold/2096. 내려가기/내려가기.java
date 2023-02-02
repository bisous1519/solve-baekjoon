import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();
	
	static int R;
	static int C = 3;
	static int[][] Map;
	static int[][] Max;
	static int[][] Min;
	
	public static void main(String[] args) throws IOException {
		R = Integer.parseInt(input.readLine());
		Map = new int[R][C];
		for(int r=0; r<R; r++) {
			tokens = new StringTokenizer(input.readLine());
			for(int c=0; c<C; c++) {
				Map[r][c] = Integer.parseInt(tokens.nextToken());
			}
		}//입력
		
		// 최대점수 구함
		Max = new int[R][C];
		Max[0][0] = Map[0][0];
		Max[0][1] = Map[0][1];
		Max[0][2] = Map[0][2];
		
		int max = -1;
		for(int r=1; r<R; r++) {
			for(int c=0; c<C; c++) {
				if(c == 0) {
					max = Math.max(Max[r-1][c], Max[r-1][c+1]);
					
				}else if(c == 1) {
					max = Math.max(Max[r-1][c-1], Max[r-1][c]);
					max = Math.max(max, Max[r-1][c+1]);
					
				}else {
					max = Math.max(Max[r-1][c-1], Max[r-1][c]);
					
				}
				
				Max[r][c] = Map[r][c] + max;
			}
		}
		
		max = -1;
		for(int c=0; c<C; c++) {
			max = Math.max(max, Max[R-1][c]);
		}
		output.append(max + " ");
		
		// 최소점수 구함
		Min = new int[R][C];
		Min[0][0] = Map[0][0];
		Min[0][1] = Map[0][1];
		Min[0][2] = Map[0][2];
		
		int min = Integer.MAX_VALUE;
		for(int r=1; r<R; r++) {
			for(int c=0; c<C; c++) {
				if(c == 0) {
					min = Math.min(Min[r-1][c], Min[r-1][c+1]);
					
				}else if(c == 1) {
					min = Math.min(Min[r-1][c-1], Min[r-1][c]);
					min = Math.min(min, Min[r-1][c+1]);
					
				}else {
					min = Math.min(Min[r-1][c-1], Min[r-1][c]);
					
				}
				
				Min[r][c] = Map[r][c] + min;
			}
		}
		
		min = Integer.MAX_VALUE;
		for(int c=0; c<C; c++) {
			min = Math.min(min, Min[R-1][c]);
		}
		output.append(min);
		
		System.out.println(output);
	}
}