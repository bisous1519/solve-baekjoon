import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static int N, A, B;
	static boolean prime[] = new boolean[1000001];
	static boolean visited[] = new boolean[1000001];
	static Queue<Pair> q = new LinkedList<>();

	static void init() {
		Arrays.fill(visited, false);
	}

	static void bfs() {
		q.clear();
		q.add(new Pair(0, N));
		visited[N] = true;
		while (!q.isEmpty()) {
			Pair p = q.poll();
			int cnt = p.cnt;
			int val = p.val;
			if (A <= val && val <= B && !prime[val]) {
				System.out.println(cnt);
				return;
			}
			if (!visited[val / 2]) {
				q.add(new Pair(cnt + 1, val / 2));
				visited[val / 2] = true;
			}
			if (!visited[val / 3]) {
				q.add(new Pair(cnt + 1, val / 3));
				visited[val / 3] = true;
			}
			if (val + 1 <= 1000000 && !visited[val + 1]) {
				q.add(new Pair(cnt + 1, val + 1));
				visited[val + 1] = true;
			}
			if (val - 1 >= 0 && !visited[val - 1]) {
				q.add(new Pair(cnt + 1, val - 1));
				visited[val - 1] = true;
			}
		}
		// return 이 안됐을 경우.
		System.out.println(-1);
	}

	static void primegogo() {
		prime[0] = prime[1] = true;
		for (int i = 2; i <= Math.sqrt(1_000_000); i++) {
			if (prime[i])
				continue;
			for (int j = i + i; j <= 1_000_000; j += i) {
				prime[j] = true;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		primegogo();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		StringTokenizer st;
		for (int tc = 1; tc <= T; tc++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			A = Integer.parseInt(st.nextToken());
			B = Integer.parseInt(st.nextToken());
			bfs();
			init();
		}
	}

	static class Pair {
		int cnt, val;

		Pair(int cnt, int val) {
			this.cnt = cnt;
			this.val = val;
		}
	}
}