import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();
	
	static int L;
	static int R;
	static int C;
	static Idx sIdx; // start index (l, r, c)
	static Idx eIdx; // end index (l, r, c)
	static char[][][] Map;
	static int[] dz = {0, 0, 0, 0, 1, -1}; // 동, 서, 남, 북, 상, 하
	static int[] dy = {0, 0, 1, -1, 0, 0};
	static int[] dx = {1, -1, 0, 0, 0, 0};
	static Queue<Idx> queue = new LinkedList<>();
	static int ans;
	
	static class Idx {
		int l;
		int r;
		int c;
		int cnt;
		public Idx(int l, int r, int c, int cnt) {
			this.l = l;
			this.r = r;
			this.c = c;
			this.cnt = cnt;
		}
	}
	
	public static boolean isIn(int l, int r, int c) {
		return 0<=l && l<L && 0<=r && r<R && 0<=c && c<C;
	}
	
	public static void bfs() {
		while(!queue.isEmpty()) {
			Idx cur = queue.poll();
			
			for(int i=0; i<dz.length; i++) {
				int toL = cur.l + dz[i];
				int toR = cur.r + dy[i];
				int toC = cur.c + dx[i];
				if(!isIn(toL, toR, toC)) { // 장외면 안됨
					continue;
				}
				if(Map[toL][toR][toC] == '.') { // 가도됨
					Map[toL][toR][toC] = '#'; // 방문표시
					queue.offer(new Idx(toL, toR, toC, cur.cnt + 1));
				}
				else if(Map[toL][toR][toC] == 'E') { // 도착!
					output.append(String.format("Escaped in %d minute(s).\n", cur.cnt + 1));
					return;
				}
			}
		}
		output.append("Trapped!\n");
	}
	
	public static void main(String[] args) throws IOException {
		while(true) {
			tokens = new StringTokenizer(input.readLine());
			L = Integer.parseInt(tokens.nextToken());
			R = Integer.parseInt(tokens.nextToken());
			C = Integer.parseInt(tokens.nextToken());
			if(L == 0 && R == 0 && C == 0) {
				break; // 종료
			}
			// 입력
			Map = new char[L][R][C];
			for(int l=0; l<L; l++) {
				for(int r=0; r<R; r++) {
					Map[l][r] = input.readLine().toCharArray();
				}
				input.readLine();
			}
			// 출발, 도착 인덱스 저장
			for(int l=0; l<L; l++) {
				for(int r=0; r<R; r++) {
					for(int c=0; c<C; c++) {
						if(Map[l][r][c] == 'S') {
							sIdx = new Idx(l, r, c, 0);
						}else if(Map[l][r][c] == 'E'){
							eIdx = new Idx(l, r, c, 0);
						}
					}
				}
			}
			queue.clear();
			queue.offer(sIdx);
			bfs();
		}
		System.out.print(output);
	}
}