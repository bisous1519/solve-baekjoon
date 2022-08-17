import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();
	
	static int N;
	static char[][] Map;
	
	public static void quadTree(int r, int c, int n) {
		char curNum = Map[r][c]; // 1 또는 0
		boolean isOneNum = true;
		for(int i=r; i<r+n; i++) {
			for(int j=c; j<c+n; j++) {
				if(Map[i][j] != curNum) { // 다른 색 돌이 나옴!
					isOneNum = false;
					// 열고
					output.append("(");
					// 사분할 해서 호출하고
					quadTree(r, c, n/2);
					quadTree(r, c + n/2, n/2);
					quadTree(r + n/2, c, n/2);
					quadTree(r + n/2, c + n/2, n/2);
					// 닫고
					output.append(")");
					return;
				}
			}
		}
		if(isOneNum) { // 하나의 숫자로 이루어진 그룹!
			output.append(curNum);
			return;
		}
	}

	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(input.readLine());
		Map = new char[N][N];
		for(int n=0; n<N; n++) {
			Map[n] = input.readLine().toCharArray();
		}
		quadTree(0, 0, N);
		System.out.println(output);
	}
}