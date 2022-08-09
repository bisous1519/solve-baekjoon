import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Main {

	private static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	private static StringTokenizer tokens;
	
	private static int N;
	private static int[][] recipes;
	private static int S;
	private static int B;
	private static boolean[] isSelected;
	private static int Min = Integer.MAX_VALUE;
	
	public static void cook(int nth, int cnt) {
		if(nth > N) {
			if(cnt > 0) {
				S = 1;
				B = 0;
				for(int i=1; i<=N; i++) {
					if(isSelected[i]) {
						S *= recipes[i][0];
						B += recipes[i][1];
					}
				}
				Min = Math.min(Min, Math.abs(S - B));
			}
			return;
		}
		
		// nth번째 재료를 요리에 포함
		isSelected[nth] = true;
		cook(nth + 1, cnt + 1);
		
		// nth번째 재료를 요리에 포함x
		isSelected[nth] = false;
		cook(nth + 1, cnt);
	}
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		recipes = new int[N+1][2];
		isSelected = new boolean[N+1];
		for(int i=1; i<=N; i++) {
			tokens = new StringTokenizer(input.readLine());
			recipes[i][0] = Integer.parseInt(tokens.nextToken());
			recipes[i][1] = Integer.parseInt(tokens.nextToken());
		}
		cook(1, 0);
		System.out.println(Min);
	}

}