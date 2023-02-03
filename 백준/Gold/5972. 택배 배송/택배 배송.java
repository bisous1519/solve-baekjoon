import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	static int N;
	static int M;
	static List<Node>[] list;
	static Queue<Integer> queue = new LinkedList<>();
	static int[] DP;
	
	static class Node {
		int node;
		int value;
		public Node(int node, int value) {
			this.node = node;
			this.value = value;
		}
	}

	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		list = new List[N+1];
		for(int n=1; n<=N; n++) {
			list[n] = new ArrayList<>();
		}
		for(int m=1; m<=M; m++) {
			tokens = new StringTokenizer(input.readLine());
			int from = Integer.parseInt(tokens.nextToken());
			int to = Integer.parseInt(tokens.nextToken());
			int value = Integer.parseInt(tokens.nextToken());
			list[from].add(new Node(to, value));
			list[to].add(new Node(from, value));
		}//입력
		
		// 초기화
		DP = new int[N+1];
		Arrays.fill(DP, -1);
		DP[1] = 0;
		
		// 최대 M만큼 돎?
		queue.offer(1);
		while(true) {
			int idx = queue.poll();
			
			for(int i=0; i<list[idx].size(); i++) {
				Node cur = list[idx].get(i);
				
				if(DP[cur.node] == -1 ||
						DP[cur.node] != -1 && DP[cur.node] > cur.value + DP[idx]) {
					DP[cur.node] = cur.value + DP[idx];
					queue.offer(cur.node);
				}
			}
			
			if(queue.isEmpty()) break;
		}
		
		System.out.println(DP[N]);
	}
}