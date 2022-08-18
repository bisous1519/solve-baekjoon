import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.io.IOException;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder output = new StringBuilder();
	
	static int N;
	static char[][] Map;
	static int cnt;
	static List<Integer> cList = new ArrayList<>();
	static Queue<int[]> queue = new LinkedList<>();
	static int[] dy = {-1, 1, 0, 0}; // 상 하 좌 우
	static int[] dx = {0, 0, -1, 1};
	
	public static boolean isIn(int r, int c) {
		return 0<=r && r<N && 0<=c && c<N;
	}
	
	public static void bfs() {
		while(!queue.isEmpty()) {
			int[] cur = queue.poll();
			int cr = cur[0];
			int cc = cur[1];
			for(int i=0; i<dy.length; i++) {
				int goR = cr + dy[i];
				int goC = cc + dx[i];
				if(!isIn(goR, goC)) {
					continue; // 장외야 돌아가
				}
				if(Map[goR][goC] == '1') {
					Map[goR][goC] = '0'; // 방문표시
					cnt++;
					queue.offer(new int[] {goR, goC});
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(input.readLine());
		Map = new char[N][N];
		for(int n=0; n<N; n++) {
			Map[n] = input.readLine().toCharArray();
		}
		for(int r=0; r<N; r++) {
			for(int c=0; c<N; c++) {
				if(Map[r][c] == '1') {
					Map[r][c] = '0';
					cnt = 1;
					queue.offer(new int[] {r, c});
					bfs();
					cList.add(cnt);
				}
			}
		}
		output.append(cList.size() + "\n");
		Collections.sort(cList);
		for(int a : cList) {
			output.append(a + "\n");
		}
		System.out.println(output);
	}
}