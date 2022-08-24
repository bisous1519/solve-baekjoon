import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Queue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	static int N;
	static int[][] Map;
	static boolean[][] isVisited;
	static Shark shark;
	static List<Fish> fishL;
	static int ans;
	static int[] dy = {-1, 0, 1, 0}; // 상 우 하 좌 
	static int[] dx = {0, 1, 0, -1};
	
	static class Shark {
		int r;
		int c;
		int size;
		int eat;
		public Shark(int r, int c) {
			this.r = r;
			this.c = c;
			this.size = 2;
			this.eat = 0;
		}
		public void eatF() {
			eat++;
			if(size == eat) {
				size++;
				eat = 0;
			}
		}
	}
	
	static class Fish implements Comparable<Fish> {
		int r;
		int c;
		int size;
		int depth;
		public Fish(int r, int c, int size, int depth) {
			this.r = r;
			this.c = c;
			this.size = size;
			this.depth = depth;
		}
		@Override
		// sort 기준 : 1) depth_거리가까운순  2) r_위  3) c_왼
		public int compareTo(Fish o) {
			if(this.depth == o.depth) {
				if(this.r == o.r) {
					return Integer.compare(this.c, o.c);
				}else {
					return Integer.compare(this.r, o.r);
				}
			}else {
				return Integer.compare(this.depth, o.depth);
			}
		}
	}
	
	static class Loc {
		int r;
		int c;
		public Loc(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}
	public static boolean isIn(int r, int c) {
		return 0<=r && r<N && 0<=c && c<N;
	}
	
	public static void bfs() {
		Queue<Loc> queue = new LinkedList<>();
		isVisited = new boolean[N][N];
		isVisited[shark.r][shark.c] = true; 
		queue.offer(new Loc(shark.r, shark.c));
		
		// bfs 탐색 : 먹을 수 있는 물고기 찾아서 위치랑 depth 저장
		fishL = new ArrayList<>();
		int depth = 0;
		while(!queue.isEmpty()) {
			depth++;
			int qSize = queue.size();
			while(qSize-- > 0) {
				Loc cur = queue.poll();
				
				for(int d=0; d<dy.length; d++) {
					int goR = cur.r + dy[d];
					int goC = cur.c + dx[d];
					
					// 장외면 말고
					if(!isIn(goR, goC)) {
						continue;
					}
					
					// 방문한데면 말고
					if(isVisited[goR][goC]) {
						continue;
					}
					
					// 먹을 수 있는 곳
					if(Map[goR][goC] != 0 && Map[goR][goC] < shark.size) {
						fishL.add(new Fish(goR, goC, Map[goR][goC], depth));
					}
					
					// 갈 수 있는 곳
					if(Map[goR][goC] == 0 || Map[goR][goC] <= shark.size) {
						isVisited[goR][goC] = true;
						queue.offer(new Loc(goR, goC));
					}
				}
			}
		} // bfs while()
		
		// 찾은 물고기 중 기준 맞게 골라서 하나 먹기
		// --> 먹을게 없음
		if(fishL.size() == 0) {
			return; //(?)
		}
		// --> 먹을거 하나거나 여러개
		else if(fishL.size() >= 1) {
			Collections.sort(fishL); // 가까운순, 위, 왼쪽 기준으로 정렬
			// 맨 위에거 먹음
			Fish f = fishL.get(0);
			shark.r = f.r;// 이동
			shark.c = f.c;
			shark.eatF();// 먹음
			Map[f.r][f.c] = 0; 
			ans += f.depth; // 초 누적
		}
		
		// 물고기 먹은 자리에서 다음 물고기 먹으러 출발!
		bfs();
	}
	
	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(input.readLine());
		Map = new int[N][N];
		for(int r=0; r<N; r++) {
			tokens = new StringTokenizer(input.readLine());
			for(int c=0; c<N; c++) {
				Map[r][c] = Integer.parseInt(tokens.nextToken());
				if(Map[r][c] == 9) {
					Map[r][c] = 0;
					shark = new Shark(r, c);
				}
			}
		}
		
		bfs();
		System.out.println(ans);
	}
}