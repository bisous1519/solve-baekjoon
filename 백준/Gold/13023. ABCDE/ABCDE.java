import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	static int N;
	static int M;
	static List<Integer>[] graph;
	static boolean[] isVisited;
	
	public static int dfs(int n, int cnt, boolean[] isVisited) {
		if(cnt == 5) {
			return 1;
		}
		
		for(int i=0; i<graph[n].size(); i++) {
			int next = graph[n].get(i);
			if(!isVisited[next]) {
				isVisited[next] = true;
				if(dfs(next, cnt + 1, isVisited) == 1) {
					return 1;
				}
				isVisited[next] = false;
			}
		}
		
		return -1;
	}
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		graph = new List[N];
		for(int n=0; n<N; n++) {
			graph[n] = new ArrayList<>();
		}
		
		for(int m=0; m<M; m++) {
			tokens = new StringTokenizer(input.readLine());
			int from = Integer.parseInt(tokens.nextToken());
			int to = Integer.parseInt(tokens.nextToken());
			graph[from].add(to);
			graph[to].add(from);
		}
		
		for(int n=0; n<N; n++) {
			isVisited = new boolean[N];
			isVisited[n] = true;
			if(dfs(n, 1, isVisited) == 1) {
				System.out.println(1);
				return;
			}
		}
		System.out.println(0);
	}
}