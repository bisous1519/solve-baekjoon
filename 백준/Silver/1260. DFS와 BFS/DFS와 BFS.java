import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();
	
	static int N; // 정점 수
	static int E; // 간선 수
	static int V; // 시작 정점
	static Node[] graph;
	
	public static class Node implements Comparable<Node> {
		int to;
		Node next;
		public Node(int to, Node next) {
			this.to = to;
			this.next = next;
		}
		@Override
		public int compareTo(Node o) {
			return Integer.compare(this.to, o.to);
		}
	}
	
	public static void dfs(int v, boolean[] isVisited) {
		List<Node> tList = new ArrayList<>();
		isVisited[v] = true;
		output.append(v).append(" ");
		
		// list에 v번째 정점과 연결된 정점들 미리 뽑아놓기
		Node next = graph[v];
		while(next != null) { // != null
			tList.add(next);
			next = next.next;
		}
		
		// 지금 정점에서 더 이상 들어갈 곳이 없으면 return
		if(tList.isEmpty()) {
			return;
		}
		
		// 작은 수 부터 탐색하기 위해 오름차순 정렬
		Collections.sort(tList);
		
		// 방문한 정점이 아니면 탐색!
		for(Node n : tList) {
			if(!isVisited[n.to]) {
				dfs(n.to, isVisited);
			}
		}
	}
	
	public static void bfs(int v, boolean[] isVisited) {
		List<Node> tList = new ArrayList<>();
		Queue<Node> queue = new LinkedList<>();
		
		isVisited[v] = true;
		queue.offer(new Node(v, null));
		
		while(!queue.isEmpty()) {
			int cV = queue.poll().to;
			output.append(cV).append(" ");
			
			// v정점과 연결된 간선들 쭉 찾아서 list에 넣기
			Node next = graph[cV];
			while(next != null) {
				tList.add(next);
				next = next.next;
			}
			
			// 작은수 부터 탐색하기 위해 list 정렬
			Collections.sort(tList);
			
			// v정점과 연결된 곳들 bfs탐색하기 위해 queue에 넣기 (방문안한곳만)
			for(Node n : tList) {
				if(!isVisited[n.to]) {
					isVisited[n.to] = true; 
					queue.offer(n);
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		E = Integer.parseInt(tokens.nextToken());
		V = Integer.parseInt(tokens.nextToken());
		graph = new Node[N+1];
		for(int e=0; e<E; e++) {
			tokens = new StringTokenizer(input.readLine());
			int from = Integer.parseInt(tokens.nextToken());
			int to = Integer.parseInt(tokens.nextToken());
			graph[from] = new Node(to, graph[from]);
			graph[to] = new Node(from, graph[to]);
		}
		dfs(V, new boolean[N+1]);
		output.append("\n");
		bfs(V, new boolean[N+1]);
		System.out.println(output);
	}
}