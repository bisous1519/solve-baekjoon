import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Stack;

public class Main {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();
	
	static int R; // 행
	static int C; // 열
	static int[][] Map;
	static int[] dy = {-1, -1, 0, 1, 1, 1, 0, -1}; // 위부터 시계방향
	static int[] dx = {0, 1, 1, 1, 0, -1, -1, -1};
	static int Cnt; // 섬의 개수
	
	public static void dfs(int cr, int cc) {
		Stack<int[]> stack = new Stack<>();
		Map[cr][cc] = 0;
		stack.push(new int[] {cr, cc});
		while(!stack.isEmpty()) {
			int[] cur = stack.pop();
			for(int i=0; i<dy.length; i++) {
				int r = cur[0] + dy[i];
				int c = cur[1] + dx[i];
				if(!(0<=r && r<R && 0<=c && c<C)) { // 장외
					continue;
				}
				if(Map[r][c] == 1) {
					Map[r][c] = 0;
					stack.push(new int[] {r, c});
				}
			}
		}
		Cnt++;
	}
	
	public static void main(String[] args) throws IOException {
		while(true) {
			tokens = new StringTokenizer(input.readLine());
			C = Integer.parseInt(tokens.nextToken());
			R = Integer.parseInt(tokens.nextToken());
			if(C == 0 && R == 0) {
				System.out.println(output);
				break;
			}
			Map = new int[R][C];
			for(int r=0; r<R; r++) {
				tokens = new StringTokenizer(input.readLine());
				for(int c=0; c<C; c++) {
					Map[r][c] = Integer.parseInt(tokens.nextToken());
				}
			}
			Cnt = 0;
			for(int r=0; r<R; r++) {
				for(int c=0; c<C; c++) {
					if(Map[r][c] == 1) {
						dfs(r, c);
					}
				}
			}
			output.append(Cnt).append("\n");
		}
	}
}