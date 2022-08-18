import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	static int R;
	static int C;
	static char[][] Map;
	static int Max = Integer.MIN_VALUE;
	static int[] dy = {-1, 1, 0, 0}; // 상 하 좌 우
	static int[] dx = {0, 0, -1, 1};
	
	public static boolean isIn(int r, int c) {
		return 0<=r && r<R && 0<=c && c<C;
	}
	
	public static void dfs(int r, int c, int cnt, boolean[] chk) {
		boolean flag = false;
		
		for(int i=0; i<dy.length; i++) {
			int goR = r + dy[i];
			int goC = c + dx[i];
			if(!isIn(goR, goC)) { // 장외야 돌아가
				continue;
			}
			char tmp = Map[goR][goC];
			if(!chk[tmp - 'A']) { // 처음 가는 알파벳
				flag = true;
				chk[tmp - 'A'] = true;
				dfs(goR, goC, cnt + 1, chk);
				chk[tmp - 'A'] = false;
			}
		}
		
		if(!flag) {// 이번 깊이탐색 끝! 더이상 갈데없음!
			Max = Math.max(Max, cnt);
		}
	}

	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		R = Integer.parseInt(tokens.nextToken());
		C = Integer.parseInt(tokens.nextToken());
		Map = new char[R][C];
		for(int r=0; r<R; r++) {
			Map[r] = input.readLine().toCharArray();
		}
		boolean[] chk = new boolean[26];
		chk[Map[0][0]-'A'] = true;
		dfs(0, 0, 1, chk);
		System.out.println(Max);
	}
}