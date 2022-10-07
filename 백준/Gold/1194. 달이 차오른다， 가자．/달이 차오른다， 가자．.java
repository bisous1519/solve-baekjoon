import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	static int R, C;
	static final int KeySize = 6; // a, b, c, d, e, f
	static char[][] Map;
	static Loc Start;
	static int[] dy = {-1, 0, 1, 0}; // 상 우 하 좌
	static int[] dx = {0, 1, 0, -1};
	
	static class Loc {
		int keys;
		int r;
		int c;
		public Loc(int keys, int r, int c) {
			this.keys = keys;
			this.r = r;
			this.c = c;
		}
	}
	
	public static boolean isIn(int r, int c) {
		return 0<=r && r<R && 0<=c && c<C;
	}
	
	public static int bfs() {
		Queue<Loc> queue = new LinkedList<>();
		boolean[][][] visited = new boolean[1<<KeySize][R][C];
		visited[Start.keys][Start.r][Start.c] = true;
		queue.offer(new Loc(Start.keys, Start.r, Start.c));
		
		int depth = 0;
		while(!queue.isEmpty()) {
			depth++;
			int size = queue.size();
			while(size-- > 0) {
				Loc cur = queue.poll();
				
				for(int d=0; d<dy.length; d++) {
					int goR = cur.r + dy[d];
					int goC = cur.c + dx[d];
						
					if(!isIn(goR, goC)) { // 장 외
						continue;
					}
					if(Map[goR][goC] == '1') { // 탈출!!
						return depth;
					}
					if(visited[cur.keys][goR][goC]) { // 이미 방문한 곳
						continue;
					}
					if(Map[goR][goC] == '#') { // 벽
						continue;
					}
					
					// 가
					if(Map[goR][goC] == '.') { // 가도됨
						visited[cur.keys][goR][goC] = true;
						queue.offer(new Loc(cur.keys, goR, goC));
					}
					else if('a' <= Map[goR][goC] && Map[goR][goC] <= 'f') { // 열쇠
						int newKeys = cur.keys | (1 << (Map[goR][goC]-'a'));
						visited[newKeys][goR][goC] = true; // 방문체크
						queue.offer(new Loc(newKeys, goR, goC)); // 큐에넣고
					}
					else if('A' <= Map[goR][goC] && Map[goR][goC] <= 'F') { // 문
						// 열쇠가 있는지 확인
						if((cur.keys & (1<<(Map[goR][goC]-'A'))) != 0) {
							visited[cur.keys][goR][goC] = true; // 방문체크
							queue.offer(new Loc(cur.keys, goR, goC)); // 큐에 넣고
						}else {
							continue;
						}
					}
				}
			} // while
		} // while
		
		return -1;
	}
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		R = Integer.parseInt(tokens.nextToken());
		C = Integer.parseInt(tokens.nextToken());
		Map = new char[R][C];
		for(int r=0; r<R; r++) {
			String str = input.readLine();
			for(int c=0; c<C; c++) {
				Map[r][c] = str.charAt(c);
				if(Map[r][c] == '0') {
					Start = new Loc(0, r, c);
					Map[r][c] = '.';
				}
			}
		} // 입력
		
		System.out.println(bfs());
	}
}