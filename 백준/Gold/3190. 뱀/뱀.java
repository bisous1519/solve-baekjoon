import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	static int N; // 맵크기
	static int K; // 사과 개수
	static boolean[][] Map;
	static int L; // 방향전환 횟수
	static int X; // 몇초후에
	static char C; // 방향전환
	static Deque<Loc> snake = new LinkedList<>();
	static int[] dy = {0, -1, 0, 1}; // 우 상 좌 하 --> L 일 때의 순서
	static int[] dx = {1, 0, -1, 0};
	
	public static class Loc {
		int r;
		int c;
		public Loc(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}
	
	public static boolean isIn(int r, int c) {
		return 1<=r && r<=N && 1<=c && c<=N;
	}
	
	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(input.readLine());
		K = Integer.parseInt(input.readLine());
		Map = new boolean[N+1][N+1];
		for(int k=0; k<K; k++) {
			tokens = new StringTokenizer(input.readLine());
			int r = Integer.parseInt(tokens.nextToken());
			int c = Integer.parseInt(tokens.nextToken());
			Map[r][c] = true; // 사과 위치는 true
		}
		
		// 뱀 출발!
		int sec = 0;
		int dir = 0; // 처음 방향은 오른쪽
		snake.offerFirst(new Loc(1, 1)); // 첫 위치는 (1,1)
		L = Integer.parseInt(input.readLine());
		outer : 
		for(int l=0; l<=L; l++) {
			// 마지막 입력받고서도 방향전환없이 계속 직진해야됨
			if(l < L) {
				tokens = new StringTokenizer(input.readLine());
				X = Integer.parseInt(tokens.nextToken());
				C = tokens.nextToken().charAt(0);
			}else {
				X = 0;
			}
			
			// x초 까지 이동하고 x초 되면 할일 후에 방향전환
			while(sec != X) {
				// 초 지나감
				sec++;
				
				// 머리 늘려서 한 칸 전진 할건데!
				Loc pre = snake.peekFirst(); // 머리가 있던 좌표
				int goR = pre.r + dy[dir];	 // 이동할 좌표
				int goC = pre.c + dx[dir];
				
				// 벽 넘어가거나 내 몸과 닿으면 게임 끝
				if(!isIn(goR, goC)) { // 벽 넘어감
					break outer;
				}
				for(Loc s : snake) {
					if(s.r == goR && s.c == goC) {	// 내 몸과 닿음
						break outer;				// 꼬리 따라가기 전에 닿는것도 게임 끝
					}
				}
				
				// 전진!
				snake.offerFirst(new Loc(goR, goC));
				
				// 사과가 있었으면 먹고! 없었으면 꼬리 한 칸 따라가고!
				if(Map[goR][goC]) {
					Map[goR][goC] = !Map[goR][goC];
				}else {
					snake.pollLast();
				}
			}
			
			// 방향전환
			if(C == 'L') {
				dir = dir + 1 == dy.length ? 0 : dir + 1;
			}else {
				dir = dir - 1 < 0 ? dy.length - 1 : dir - 1;
			}
			
		} // outer
		
		System.out.println(sec);
	}
}