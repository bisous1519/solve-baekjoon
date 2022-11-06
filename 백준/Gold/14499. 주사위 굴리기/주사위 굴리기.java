import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();
	
	static int R, C;
	static int r, c;
	static int K;
	static int[] dice = new int[7];
	static final int TOP = 1;
	static final int FRONT = 5;
	static final int BOTTOM = 6;
	static final int BACK = 2;
	static final int LEFT = 4; 
	static final int RIGHT = 3;
	static int[][] Map;
	static int[] dy = {0, 0, 0, -1, 1}; // x, 오 왼 위 아
	static int[] dx = {0, 1, -1, 0, 0};
	
	public static boolean isIn(int r, int c) {
		return 0<=r && r<R && 0<=c && c<C;
	}

	public static void roll(int dir) {
		int tmp = 0;
		switch(dir) {
		case 1:
			tmp = dice[TOP];
			dice[TOP] = dice[LEFT];
			dice[LEFT] = dice[BOTTOM];
			dice[BOTTOM] = dice[RIGHT];
			dice[RIGHT] = tmp;
			break;
		case 2:
			tmp = dice[TOP];
			dice[TOP] = dice[RIGHT];
			dice[RIGHT] = dice[BOTTOM];
			dice[BOTTOM] = dice[LEFT];
			dice[LEFT] = tmp;
			break;
		case 3:
			tmp = dice[TOP];
			dice[TOP] = dice[FRONT];
			dice[FRONT] = dice[BOTTOM];
			dice[BOTTOM] = dice[BACK];
			dice[BACK] = tmp;
			break;
		case 4:
			tmp = dice[TOP];
			dice[TOP] = dice[BACK];
			dice[BACK] = dice[BOTTOM];
			dice[BOTTOM] = dice[FRONT];
			dice[FRONT] = tmp;
			break;
		}
	}
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		R = Integer.parseInt(tokens.nextToken());
		C = Integer.parseInt(tokens.nextToken());
		r = Integer.parseInt(tokens.nextToken());
		c = Integer.parseInt(tokens.nextToken());
		K = Integer.parseInt(tokens.nextToken());
		Map = new int[R][C];
		for(int r=0; r<R; r++) {
			tokens = new StringTokenizer(input.readLine());
			for(int c=0; c<C; c++) {
				Map[r][c] = Integer.parseInt(tokens.nextToken());
			}
		} // 입력
		
		tokens = new StringTokenizer(input.readLine());
		for(int k=0; k<K; k++) {
			int dir = Integer.parseInt(tokens.nextToken());

			// 칸 옮겨가기
			int goR = r + dy[dir];
			int goC = c + dx[dir];
			
			if(!isIn(goR, goC)) {
				continue;
			}
			
			r = goR;
			c = goC;
			
			// 주사위 굴리기
			roll(dir);
			
			// 칸이랑 주사위 바닥 비교
			if(Map[r][c] == 0) {
				Map[r][c] = dice[BOTTOM];
			}else {
				dice[BOTTOM] = Map[r][c];
				Map[r][c] = 0;
			}
			
			// 상단 출력
			output.append(dice[TOP] + "\n");
		}
		
		System.out.println(output);
	}
}