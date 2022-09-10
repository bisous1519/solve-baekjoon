import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;

	static int N;
	static int M;
	static int G;
	static int R;
	static final int GREEN = 3;
	static final int RED = 4;
	static int[][] Map; // 0:호수, 1:배양액x, 2:배양액o
	static Status[][] MapCopy;
	static Loc[] choosedAll;
	static Queue<Loc> queue;
	static int Max = Integer.MIN_VALUE;
	static int[] dy = { -1, 0, 1, 0 }; // 상 우 하 좌
	static int[] dx = { 0, 1, 0, -1 };

	static class Status {
		int color;
		int sec;

		public Status(int color, int sec) {
			this.color = color;
			this.sec = sec;
		}
	}

	static class Loc {
		int r;
		int c;
		int color;

		public Loc(int r, int c, int color) {
			this.r = r;
			this.c = c;
			this.color = color;
		}
	}

	public static boolean isIn(int n, int m) {
		return 0 <= n && n < N && 0 <= m && m < M;
	}

	public static int bfs() {
		int cnt = 0;
		int sec = 0;
		while (!queue.isEmpty()) {
			int size = queue.size();
			sec++;
			while (size-- > 0) {
				Loc c = queue.poll();

				if (MapCopy[c.r][c.c].sec == -1) {
					continue;
				}

				for (int d = 0; d < dy.length; d++) {
					int goR = c.r + dy[d];
					int goC = c.c + dx[d];
					if (!isIn(goR, goC)) { // 장외 돌아가
						continue;
					}
					if (Map[goR][goC] == 0) { // 호수로는 퍼질 수 없음
						continue;
					}
					if (MapCopy[goR][goC] != null) {
						if (MapCopy[goR][goC].sec == -1) { // 꽃이 있는 자리
							continue;
						}
						if (MapCopy[goR][goC].sec < sec) { // 이전에 퍼진 자리
							continue;
						}
						if (MapCopy[goR][goC].sec == sec) {
							if (MapCopy[goR][goC].color == c.color) {
								continue; // 같은 색 배양액이라 갈 필요x
							}
							// 동시에 도착한 다른색 배양액!
							MapCopy[goR][goC].sec = -1;
							cnt++;
							continue;

						}
					}
					MapCopy[goR][goC] = new Status(c.color, sec);
					queue.offer(new Loc(goR, goC, c.color));
				}
			}
		}

		return cnt;
	}

	public static void combi(int nth, Loc[] choosed, int sN, int sM, int color) {
		if (nth == (color == GREEN ? G : R)) {

			if (color == GREEN) {
				// 뽑은 조합 저장해놓기
				for (int i = 0; i < choosed.length; i++) {
					choosedAll[i] = new Loc(choosed[i].r, choosed[i].c, color);
				}

				// Map에 배양액 뿌릴 자리 표시
				for (Loc c : choosed) {
					Map[c.r][c.c] = color;
				}

				// 빨강 배양액 뿌릴 조합 돌리러가기
				combi(0, new Loc[R], 0, 0, RED);

				// 표시한 자리 돌려놓기
				for (Loc c : choosed) {
					Map[c.r][c.c] = 2;
				}

			} else {
				// 뽑은 조합 저장해놓기
				for (int i = 0; i < choosed.length; i++) {
					choosedAll[G + i] = new Loc(choosed[i].r, choosed[i].c, color);
				}

				// Map에 배양액 뿌릴 자리 표시
				for (Loc c : choosed) {
					Map[c.r][c.c] = color;
				}

				// MapCopy에 표시하고 queue에 넣고
				MapCopy = new Status[N][M];
				queue = new LinkedList<>();
				for (Loc c : choosedAll) {
					MapCopy[c.r][c.c] = new Status(c.color, 0);

					queue.offer(c);
				}

				// 배양액 퍼뜨리고 꽃피우는 개수 max 업데이트
				Max = Math.max(Max, bfs());

				// Map에 자리 표시한거 돌려놓기
				for (Loc c : choosed) {
					Map[c.r][c.c] = 2;
				}
			}

			return;
		}

		if (sM == M) {
			sM = 0;
			sN++;
		}
		for (int n = sN; n < N; n++) {
			for (int m = sM; m < M; m++) {
				if (Map[n][m] == 2) { // 뿌리는건 2에만 가능
					choosed[nth] = new Loc(n, m, color);
					combi(nth + 1, choosed, n, m + 1, color);
				}
			}
			sM = 0;
		}
	}

	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		G = Integer.parseInt(tokens.nextToken());
		R = Integer.parseInt(tokens.nextToken());
		Map = new int[N][M];
		for (int n = 0; n < N; n++) {
			tokens = new StringTokenizer(input.readLine());
			for (int m = 0; m < M; m++) {
				Map[n][m] = Integer.parseInt(tokens.nextToken());
			}
		} // 입력

		// 초록색 배양액 뿌릴 조합 뽑으러 가기
		choosedAll = new Loc[G + R]; // 뽑은 조합을 모두 저장
		combi(0, new Loc[G], 0, 0, GREEN);

		System.out.println(Max);
	}
}