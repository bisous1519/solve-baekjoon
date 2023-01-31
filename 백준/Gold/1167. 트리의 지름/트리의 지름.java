import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;

	static int V;
	static List<Node>[] list;
	static boolean[] visited;
	static int MaxCost = Integer.MIN_VALUE;
	static int MaxNode;
	
	static class Node {
		int v;
		int cost;
		public Node(int v, int cost) {
			this.v = v;
			this.cost = cost;
		}
	}
	
	public static void dfs(int v, int cost) {
		if(cost > MaxCost) {
			MaxCost = cost;
			MaxNode = v;
		}
		
		for(int i=0; i<list[v].size(); i++) {
			Node node = list[v].get(i);
			if(!visited[node.v]) {
				visited[node.v] = true; 
				dfs(node.v, cost + node.cost);
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		V = Integer.parseInt(input.readLine());
		
		list = new ArrayList[V+1];
		for(int v=1; v<=V; v++) {
			list[v] = new ArrayList<>();
		}
		
		for(int v=1; v<=V; v++) {
			tokens = new StringTokenizer(input.readLine());
			int i = Integer.parseInt(tokens.nextToken());
			while(true) {
				int ver = Integer.parseInt(tokens.nextToken());
				if(ver == -1) break;
				int cost = Integer.parseInt(tokens.nextToken());
				list[i].add(new Node(ver, cost));
			}
		}//입력
		
		
		// 임의의 정점(1)에서 가장 멀리 있는 정점(v1) 찾기
		visited = new boolean[V+1];
		visited[1] = true;
		dfs(1, 0);
		
		// v1에서 다시 가장 멀리 있는 정점(v2) 찾기 -> v1~v2 길이가 트리의 지름이됨
		visited = new boolean[V+1];
		visited[MaxNode] = true;
		dfs(MaxNode, 0);
		
		System.out.println(MaxCost);
	}
}