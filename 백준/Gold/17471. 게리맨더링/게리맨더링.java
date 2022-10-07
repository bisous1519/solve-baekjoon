import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	static int N;
	static int[] perpul;
	static boolean[][] link;
	static int Min = Integer.MAX_VALUE;

	public static int bfs(int[] area) {
		Queue<Integer> queue = new LinkedList<>();
		boolean[] visited = new boolean[N+1];
		visited[area[0]] = true;
		queue.offer(area[0]);
		
		int sum = perpul[area[0]];
		int linkedCnt = 1;
		while(!queue.isEmpty()) {
			int size = queue.size();
			while(size-- > 0) {
				int cur = queue.poll();
				
				int n = 0;
				for( ; n<area.length; n++) {
					// 들어가있는 간선 만큼만 돌아야됨
					if(area[n] == 0) {
						break;
					}
					// 이미 방문한 점이면 말고
					if(visited[area[n]]) {
						continue;
					}
					// 현재 지역이랑 연결되어있는 지역이면
					if(link[cur][area[n]]) {
						visited[area[n]] = true;
						queue.offer(area[n]);
						sum += perpul[area[n]];
						linkedCnt++;
					}
				}
				
				if(linkedCnt == n) {
					return sum;
				}
			}
		}
		
		if(linkedCnt == area.length) {
			return sum;
		}else {
			return -1;
		}
	}
	
	public static void set(int nth, int[] areaA, int[] areaB, int cntA, int cntB) {
		if(nth == N+1) {
			// 구역이 1개면 안됨
			if(cntA == 0 || cntA == N) {
				return;
			}
			
			// 각 부분집합을 bfs -> 이어져 있는지 -> 인구수 몇인지 확인
			int pA = bfs(areaA);
			int pB = bfs(areaB);
			if(pA != -1 && pB != -1) { // 둘 다 각자 이어져있음
				Min = Math.min(Min, Math.abs(pA - pB));
			}
			
			return;
		}
		
		areaA[cntA] = nth;
		set(nth + 1, areaA, areaB, cntA + 1, cntB);
		areaA[cntA] = 0;
		
		areaB[cntB] = nth;
		set(nth + 1, areaA, areaB, cntA, cntB + 1);
		areaB[cntB] = 0;
	}
	
	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(input.readLine());
		perpul = new int[N+1];
		link = new boolean[N+1][N+1];
		tokens = new StringTokenizer(input.readLine());
		for(int n=1; n<=N; n++) {
			perpul[n] = Integer.parseInt(tokens.nextToken());
		}
		for(int n=1; n<=N; n++) {
			tokens = new StringTokenizer(input.readLine());
			int cnt = Integer.parseInt(tokens.nextToken());
			for(int i=0; i<cnt; i++) {
				int m = Integer.parseInt(tokens.nextToken());
				link[n][m] = true;
				link[m][n] = true;
			}
		} // 입력
		
		// 부분집합 -> 구역 두개로 나누기
		set(1, new int[N], new int[N], 0, 0);
		
		if(Min == Integer.MAX_VALUE) {
			System.out.println(-1);
		}else {
			System.out.println(Min);
		}
	}
}