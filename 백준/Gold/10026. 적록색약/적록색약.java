import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder output = new StringBuilder();
	
	static int N;
	static char[][] Map1;
	static char[][] Map2;
	static Queue<int[]> queue;
	static int[] dy = {-1, 0, 1, 0}; // 상 우 하 좌
	static int[] dx = {0, 1, 0, -1};
	
	public static boolean isIn(int r, int c) {
		return 0<=r && r<N && 0<=c && c<N;
	}
	
	public static void bfs(int r, int c, char[][] Map) {
		queue = new LinkedList<>();
		char find = Map[r][c];
		Map[r][c] = '0';
		queue.offer(new int[] {r, c});
		
		while(!queue.isEmpty()) {
			int size = queue.size();
			while(size-- > 0) {
				int[] cur = queue.poll();
				int cr = cur[0];
				int cc = cur[1];
				for(int d=0; d<dy.length; d++) {
					int goR = cr + dy[d];
					int goC = cc + dx[d];
					if(!isIn(goR, goC)) { // 장외면 돌아가
						continue;
					}
					if(Map[goR][goC] == find) {
						Map[goR][goC] = '0';
						queue.offer(new int[] {goR, goC});
					}
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(input.readLine());
		Map1 = new char[N][N];
		Map2 = new char[N][N];
		for(int r=0; r<N; r++) {
			Map1[r] = input.readLine().toCharArray();
		}
		for(int r=0; r<N; r++) {
			for(int c=0; c<N; c++) {
				if(Map1[r][c] == 'B') {
					Map2[r][c] = 'B';
				}else {
					Map2[r][c] = 'R';
				}
			}
		}
		
		
		// 적록색약 아닌사람
		int cnt = 0;
		for(int r=0; r<N; r++) {
			for(int c=0; c<N; c++) {
				if(Map1[r][c] != '0') {
					bfs(r, c, Map1);
					cnt++;
				}
			}
		}
		output.append(cnt + " ");
		
		// 적록색약 사람
		cnt = 0;
		for(int r=0; r<N; r++) {
			for(int c=0; c<N; c++) {
				if(Map2[r][c] != '0') {
					bfs(r, c, Map2);
					cnt++;
				}
			}
		}
		output.append(cnt);
		
		// 출력
		System.out.println(output);
	}
}