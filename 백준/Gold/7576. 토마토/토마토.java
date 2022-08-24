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
	static int[][] Map;
	static Queue<Tomato> ripeT = new LinkedList<>();
	static int raw;
	static int date;
	static int[] dy = {-1, 0, 1, 0}; // 상 우 하 좌
	static int[] dx = {0, 1, 0, -1};
	
	static class Tomato {
		int r;
		int c;
		int date; // 몇일에 익은 토마토인지
		public Tomato(int r, int c, int date) {
			this.r = r;
			this.c = c;
			this.date = date;
		}
	}
	
	public static boolean isIn(int r, int c) {
		return 0<=r && r<R && 0<=c && c<C;
	}
	
	public static int bfs() {
		while(!ripeT.isEmpty()) {
			if(raw == 0) { // 안익은 토마토 없음!
				return date;
			}
			
			if(ripeT.peek().date == date) {
				date++;
			}
			Tomato tomato = ripeT.poll();
			
			// 사방 살피면서 안익은 토마토 익히기!
			for(int d=0; d<dy.length; d++) {
				int goR = tomato.r + dy[d];
				int goC = tomato.c + dx[d];
				if(!isIn(goR, goC)) { // 장외야 돌아가
					continue;
				}
				if(Map[goR][goC] == 0) { // 익지 않은 토마토
					Map[goR][goC] = 1; // 익힘
					raw--; // 익힘
					ripeT.offer(new Tomato(goR, goC, date));
				}
			}
		}
		
		if(raw == 0) {
			return date;
		}else {
			return -1;
		}
	}
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		C = Integer.parseInt(tokens.nextToken());
		R = Integer.parseInt(tokens.nextToken());
		Map = new int[R][C];
		for(int n=0; n<R; n++) {
			tokens = new StringTokenizer(input.readLine());
			for(int m=0; m<C; m++) {
				Map[n][m] = Integer.parseInt(tokens.nextToken());
				if(Map[n][m] == 1) { // 익은 토마토!
					ripeT.offer(new Tomato(n, m, 0));
				}else if(Map[n][m] == 0) { // 안익은 토마토 개수세기!
					raw++;
				}
			}
		}
		System.out.println(bfs());
	}
}