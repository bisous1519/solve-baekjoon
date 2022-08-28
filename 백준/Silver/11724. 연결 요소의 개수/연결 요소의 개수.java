import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	static int N;
	static int M;
	static List<Integer>[] list;
	static boolean[] check;
	
	public static void bfs(int n) {
		Queue<Integer> queue = new LinkedList<>();
		check[n] = true;
		queue.offer(n);
		
		while(!queue.isEmpty()) {
			int size = queue.size();
			while(size-- > 0) {
				int cur = queue.poll();
				for(int a : list[cur]) {
					if(!check[a]) {
						check[a] = true;
						queue.offer(a);
					}
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		list = new List[N+1];
		check = new boolean[N+1];
		for(int n=1; n<=N; n++) {
			list[n] = new ArrayList<>();
		}
		for(int m=0; m<M; m++) {
			tokens = new StringTokenizer(input.readLine());
			int u = Integer.parseInt(tokens.nextToken());
			int v = Integer.parseInt(tokens.nextToken());
			list[u].add(v);
			list[v].add(u);
		}
		
		int cnt = 0;
		for(int n=1; n<=N; n++) {
			if(!check[n]) {
				bfs(n);
				cnt++;
			}
		}
		
		System.out.println(cnt);
	}
}