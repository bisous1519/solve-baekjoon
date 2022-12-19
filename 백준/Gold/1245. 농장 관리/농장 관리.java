import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	static int R;
	static int C;
	static int[][] Map;
	static boolean[][] isVisited;
	static PriorityQueue<Loc> queue = new PriorityQueue<>();
	static int[] dy = {-1, -1, 0, 1, 1, 1, 0, -1}; // 상 상우 우 우하 하 하좌 좌 좌상
	static int[] dx = {0, 1, 1, 1, 0, -1, -1, -1};
	
	static class Loc implements Comparable<Loc> {
		int r;
		int c;
		int h;
		public Loc(int r, int c, int h) {
			this.r = r;
			this.c = c;
			this.h = h;
		}
		@Override
		public int compareTo(Loc o) {
			// 높이가 높은 것 부터
			return Integer.compare(this.h, o.h) * -1;
		}
	}
	
	public static boolean isIn(int r, int c) {
		return 0<=r && r<R && 0<=c && c<C;
	}
	
	public static boolean isBong(Loc base) {
		Queue<Loc> queue = new LinkedList<>();
		queue.offer(base); 
		Queue<Loc> sameBong = new LinkedList<>();
		sameBong.offer(base);  // 봉우리 위치 쭉 넣어줬다가 봉우리가 맞다면 방문체크
		boolean[][] isV = new boolean[R][C];
		isV[base.r][base.c] = true;
		
		while(!queue.isEmpty()) {
			int size = queue.size();
			while(size-- > 0) {
				Loc cur = queue.poll();
				
				for(int d=0; d<dy.length; d++) {
					int goR = cur.r + dy[d];
					int goC = cur.c + dx[d];
					
					if(!isIn(goR, goC)) continue; // 장외면 pass
					if(isV[goR][goC]) continue; // 이미 본 곳 pass
					if(Map[goR][goC] > base.h) return false; // 내가 봉우리가 아니었음
					
					isV[goR][goC] = true;
					if(Map[goR][goC] < base.h) continue; // 나보다 낮으면 pass
					if(Map[goR][goC] == base.h) {
						queue.offer(new Loc(goR, goC, base.h));
						sameBong.offer(new Loc(goR, goC, base.h));
					}
				}
			}
		}
		
		// 봉우리가 맞음! 체크해주고 끝내기
		while(!sameBong.isEmpty()) {
			Loc cur = sameBong.poll();
			isVisited[cur.r][cur.c] = true;
		}
		return true;
	}
	
	public static int countBong() {
		int count = 0;
		while(!queue.isEmpty()) {
			Loc cur = queue.poll();
			if(isVisited[cur.r][cur.c]) continue; // 이미 산봉우리
			
			// 산봉우리가 맞는지
			if(isBong(cur)) count++;
		}
		
		return count;
	}

	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		R = Integer.parseInt(tokens.nextToken());
		C = Integer.parseInt(tokens.nextToken());
		
		Map = new int[R][C];
		isVisited = new boolean[R][C];
		for(int r=0; r<R; r++) {
			tokens = new StringTokenizer(input.readLine());
			for(int c=0; c<C; c++) {
				Map[r][c] = Integer.parseInt(tokens.nextToken());
				queue.offer(new Loc(r, c, Map[r][c]));
			}
		}// 입력
		
		// 산봉우리 개수세러 떠람
		System.out.println(countBong());
	}
}