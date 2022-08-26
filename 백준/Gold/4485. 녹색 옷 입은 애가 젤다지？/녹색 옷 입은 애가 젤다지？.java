import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();
	
	static int N;
	static int[][] Map;
	static int[] D;
	static boolean[] isVisited;
	static int[] dy = {-1, 0, 1, 0}; // 상 우 하 좌
	static int[] dx = {0, 1, 0, -1};
	
	public static boolean isIn(int r, int c) {
		return 0<=r && r<N && 0<=c && c<N;
	}

	public static void main(String[] args) throws IOException {
		int t = 0;
		while(true) {
			t++;
			N = Integer.parseInt(input.readLine());
			if(N == 0) { // 0 입력되면 종료
				break;
			}
			Map = new int[N][N];
			D = new int[N*N];
			isVisited = new boolean[N*N];
			for(int r=0; r<N; r++) {
				tokens = new StringTokenizer(input.readLine());
				for(int c=0; c<N; c++) {
					Map[r][c] = Integer.parseInt(tokens.nextToken());
				}
			}
			
			// 각 노드들 비용 초기화
			Arrays.fill(D, Integer.MAX_VALUE);
			D[0] = Map[0][0];
			
			// 도착지까지 최소 비용 구하기
			for(int i=0; i<N*N; i++) {
				
				// 현재까지 최소 비용인 노드 찾아서 방문
				int min = Integer.MAX_VALUE;
				int minV = 0;
				for(int v=0; v<N*N; v++) {
					if(!isVisited[v] && D[v] < min) {
						min = D[v];
						minV = v;
					}
				}
				isVisited[minV] = true;
				
				// 현재 찾은 노드가 최소비용인데 그게 도착지점이면 끝!
				if(minV == N*N - 1) {
					output.append(String.format("Problem %d: %d\n", t, min));
					break;
				}
				
				// 현재 찾은 노드(minV)에서 인접 노드 비용 최소로 업데이트
				// 현재 찾은 노드의 좌표
				int cr = 0;
				int cc = 0;
				if(minV != 0) {
					cr = minV / N;
					cc = minV % N;
				}
				for(int d=0; d<dy.length; d++) {
					int goR = cr + dy[d]; // Map에서의 좌표
					int goC = cc + dx[d];
					int goNode = goR * N + goC; // 노드번호
					
					// 장외 돌아가
					if(!isIn(goR, goC)) {
						continue;
					}
					
					// 지금노드에서 가는게 더 최소이면 업데이트
					if(!isVisited[goNode] && D[minV] + Map[goR][goC] < D[goNode]) {
						D[goNode] = D[minV] + Map[goR][goC];
					}
				}
			}
		} // while
		
		// 출력
		System.out.println(output);
	}
}