import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	static int N;
	static int M;
	static int islandCnt;
	static int bLength;
	static int[][] Map;
	static int[][] bridge = new int[7][7]; // 최대 섬의개수 6
	static int[] connect = new int[7];
	static PriorityQueue<Bridge> minHeap = new PriorityQueue<>();
	static int[] dy = {-1, 0, 1, 0}; // 상 우 하 좌
	static int[] dx = {0, 1, 0, -1};
	
	static class Loc {
		int r;
		int c;
		public Loc(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}
	
	static class Bridge implements Comparable<Bridge> {
		int length;
		int from;
		int to;
		public Bridge(int length, int from, int to) {
			this.length = length;
			this.from = from;
			this.to = to;
		}
		@Override
		public int compareTo(Bridge o) {
			return Integer.compare(this.length, o.length);
		}
	}
	
	public static boolean isIn(int r, int c) {
		return 0<=r && r<N && 0<=c && c<M;
	}
	
	public static void union(int from, int to) {
		int p1 = find(from);
		int p2 = find(to);
		connect[p2] = p1;
	}
	
	public static int find(int num) {
		if(connect[num] == num) {
			return num;
		}
		return connect[num] = find(connect[num]);
	}
	
	public static void makeBridge(int r, int c) {
		for(int d=0; d<dy.length; d++) {
			int goR = r + dy[d];
			int goC = c + dx[d];
			
			if(!isIn(goR, goC)) {
				continue;
			}
			
			if(Map[goR][goC] == Map[r][c]) {
				continue;
			}
			
			int cr = r;
			int cc = c;
			int cnt = 0;
			while(true) {
				cr += dy[d];
				cc += dx[d];
				if(!isIn(cr, cc)) { // 장외임!
					break;
				}
				if(Map[cr][cc] != 0) { // 다른 섬 만남!
					if(cnt >= 2) {
						int from = Map[r][c];
						int to = Map[cr][cc];
						if(bridge[from][to] == 0 ||  bridge[from][to] > cnt) {
							bridge[from][to] = cnt; // 어느 섬이 얼만큼 비용으로 연결되는지 체크
							bridge[to][from] = cnt;
							minHeap.add(new Bridge(cnt, from, to)); // 비용 적은 순으로 힙에 넣음
						}
					}
					break;
				}
				cnt++;
			}
		}
	}
	
	public static void bfs(int r, int c, int num) {
		Queue<Loc> queue = new LinkedList<>();
		Map[r][c] = num;
		queue.offer(new Loc(r, c));
		
		while(!queue.isEmpty()) {
			int size = queue.size();
			while(size-- > 0) {
				Loc cur = queue.poll();
				
				for(int d=0; d<dy.length; d++) {
					int goR = cur.r + dy[d];
					int goC = cur.c + dx[d];
					
					if(!isIn(goR, goC)) { // 장외면 돌아가
						continue;
					}
					
					if(Map[goR][goC] != -1) { // 빈칸이거나 방문한곳이면 pass
						continue;
					}
					
					Map[goR][goC] = num;
					queue.offer(new Loc(goR, goC));
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		Map = new int[N][M];
		for(int r=0; r<N; r++) {
			tokens = new StringTokenizer(input.readLine());
			for(int c=0; c<M; c++) {
				Map[r][c] = Integer.parseInt(tokens.nextToken()) * -1;
			}
		}
		
		// 각 섬을 각 번호로 체크해주기
		for(int r=0; r<N; r++) {
			for(int c=0; c<M; c++) {
				if(Map[r][c] == -1) { // 섬 찾음!
					bfs(r, c, ++islandCnt);
				}
			}
		}
				
		// 놓을 수 있는 다리 모두 놔보기
		for(int r=0; r<N; r++) {
			for(int c=0; c<M; c++) {
				if(Map[r][c] != 0) {
					makeBridge(r, c);
				}
			}
		}
		
		// 최소비용으로 다리 놓기
		for(int i=1; i<=islandCnt; i++) {
			connect[i] = i; // 다 서로소 섬으로 만들기 (부모가 자기자신)
		}
		boolean[] isConn = new boolean[islandCnt+1];
		while(!minHeap.isEmpty()) {
			Bridge b = minHeap.poll();
			
			// 연결된 섬인지 확인
			if(find(b.from) == find(b.to)) {
				continue;
				
			// 연결안된 섬! -> 다리놓기
			}else {
				union(b.from, b.to);
				isConn[b.from] = true;
				isConn[b.to] = true; 
				bLength += b.length;
			}
		}
		
		// 연결 안된 섬 있는지 확인
		for(int i=1; i<=islandCnt; i++) {
			if(!isConn[i]) {
				System.out.println("-1");
				return;
			}
		}
		int p = find(connect[1]);
		for(int i=2; i<=islandCnt; i++) {
			if(p != find(connect[i])) {
				System.out.println("-1");
				return;
			}
		}
		System.out.println(bLength);
	}
}