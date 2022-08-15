import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	
	static Queue<int[]> Fire = new LinkedList<>();
	static Queue<int[]> Jihun = new LinkedList<>();
	static int R; // 행
	static int C; // 열
	static char[][] Map;
	static int[] dy = {-1, 1, 0, 0}; // 상 하 좌 우
	static int[] dx = {0, 0, -1, 1};
	
	public static boolean isIn(int r, int c) {
		return 0<=r && r<R && 0<=c && c<C;
	}
	
	public static void tickTock(int min) {
		// 더 이상 지훈이가 이동할 수 없음
		if(Jihun.isEmpty()) {
			System.out.println("IMPOSSIBLE");
			return;
		}
		
		// 불 번지기
		while(!Fire.isEmpty() && Fire.peek()[2] == min) {
			int[] curF = Fire.poll();
			for(int i=0; i<dy.length; i++) {
				int r = curF[0] + dy[i];
				int c = curF[1] + dx[i];
				if(!isIn(r, c) ||  Map[r][c] == '#' || Map[r][c] == 'F') {
					// 장외거나, 벽이거나, 이미 불난곳 --> 안됨
					continue;
				}
				Map[r][c] = 'F';
				Fire.offer(new int[] {r, c, min + 1});
			}
		}
		
		// 지훈이 이동
		while(!Jihun.isEmpty() && Jihun.peek()[2] == min) {
			int[] curJ = Jihun.poll();
			for(int i=0; i<dy.length; i++) {
				int r = curJ[0] + dy[i];
				int c = curJ[1] + dx[i];
				if(!isIn(r, c)) {
					System.out.println(min + 1);
					return;
				}
				if(Map[r][c] == '#' || Map[r][c] == 'F' || Map[r][c] == 'J') {
					// 벽이거나, 불난곳, 이미 갔던 곳 --> 안됨
					continue;
				}
				Map[r][c] = 'J';
				Jihun.offer(new int[] {r, c, min + 1});
			}
		}
		tickTock(min + 1);
	}
	
	public static void main(String[] args) throws IOException {
		String[] str = input.readLine().split(" ");
		R = Integer.parseInt(str[0]);
		C = Integer.parseInt(str[1]);
		Map = new char[R][C];
		for(int r=0; r<R; r++) {
			Map[r] = input.readLine().toCharArray();
		}
		// J랑 F위치 찾아서 queue에 넣기
		for(int r=0; r<R; r++) {
			for(int c=0; c<C; c++) {
				if(Map[r][c] == 'F') {
					Fire.offer(new int[] {r, c, 0});
				}
				if(Map[r][c] == 'J') {
					Jihun.offer(new int[] {r, c, 0});
				}
			}
		}
		tickTock(0);
	}
}