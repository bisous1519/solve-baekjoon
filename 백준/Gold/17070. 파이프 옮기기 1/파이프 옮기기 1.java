import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	private static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	private static StringTokenizer tokens;

	static int N;
	static int[][] Map;
	static Queue<Loc> queue = new LinkedList<>();
	static int cnt = 0;

	static class Loc {
		int shape; // 0: 가로, 1: 세로, 2: 대각선
		int r;
		int c;

		public Loc(int shape, int r, int c) {
			this.shape = shape;
			this.r = r;
			this.c = c;
		}
	}

	public static boolean isIn(int r, int c) {
		return 1 <= r && r <= N && 1 <= c && c <= N;
	}

	public static boolean des(int r, int c) { // 도착여부 판단
		if (r == N && c == N) { // 도착
			cnt++;
			return true;
		} else
			return false;
	}

	public static void pushRight(int r, int c) {
		if (!isIn(r, c + 1))
			return;
		if (Map[r][c + 1] == 0) {
			if (!des(r, c + 1)) {
				queue.offer(new Loc(0, r, c + 1));
			}
		}
	}

	public static void pushDown(int r, int c) {
		if (!isIn(r + 1, c))
			return;
		if (Map[r + 1][c] == 0) {
			if (!des(r + 1, c)) {
				queue.offer(new Loc(1, r + 1, c));
			}
		}
	}

	public static void pushDiag(int r, int c) {
		if (!isIn(r + 1, c + 1))
			return;
		if (Map[r][c + 1] == 0 && Map[r + 1][c] == 0 && Map[r + 1][c + 1] == 0) {
			if (!des(r + 1, c + 1)) {
				queue.offer(new Loc(2, r + 1, c + 1));
			}
		}
	}

	public static void movePipe() {
		queue.offer(new Loc(0, 1, 2));

		while (!queue.isEmpty()) {
			Loc cur = queue.poll();

			switch (cur.shape) {
			case 0: // 이전 모양이 가로 -> 오른쪽, 대각선
				pushRight(cur.r, cur.c);
				pushDiag(cur.r, cur.c);
				break;
			case 1: // 이전 모양이 세로 -> 아래, 대각선
				pushDown(cur.r, cur.c);
				pushDiag(cur.r, cur.c);
				break;
			case 2: // 이전 모양이 대각선 -> 오른쪽, 아래, 대각선
				pushRight(cur.r, cur.c);
				pushDown(cur.r, cur.c);
				pushDiag(cur.r, cur.c);
				break;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(input.readLine());
		Map = new int[N + 1][N + 1];
		for (int r = 1; r <= N; r++) {
			tokens = new StringTokenizer(input.readLine());
			for (int c = 1; c <= N; c++) {
				Map[r][c] = Integer.parseInt(tokens.nextToken());
			}
		}
		// 입력

		if(Map[N][N] != 1) {
			movePipe();
			System.out.println(cnt);
		}else {
			System.out.println(0);
		}
	}

}