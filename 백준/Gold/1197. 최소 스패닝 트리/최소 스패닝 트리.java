import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();
	
//	static int T;
	static int V;
	static int E;
	static List<Node>[] graph;
	
	static class Node implements Comparable<Node> {
		int no;
		int cost;
		public Node(int no, int cost) {
			this.no = no;
			this.cost = cost;
		}
		@Override
		public int compareTo(Node o) {
			return Integer.compare(this.cost, o.cost);
		}
	}
	
	public static long prim() {
		PriorityQueue<Node> queue = new PriorityQueue<>();
		boolean[] isVisited = new boolean[V+1];
		long totalCost = 0;
		int nodeCnt = V;
		
		queue.offer(new Node(1, 0));
		while(!queue.isEmpty()) {
			Node minCostHead = queue.poll();
			
			if(isVisited[minCostHead.no]) {
				continue;
			}
			
			isVisited[minCostHead.no] = true;
			totalCost += minCostHead.cost;
			nodeCnt--;
			
			if(nodeCnt == 0) {
				break;
			}
			
			for(int v=0; v<graph[minCostHead.no].size(); v++) {
				Node node = graph[minCostHead.no].get(v);
				if(!isVisited[node.no]) {
					queue.offer(node);
				}
			}
		}
		
		return totalCost;
	}
	
	public static void main(String[] args) throws IOException {
//		T = Integer.parseInt(input.readLine());
//		for(int t=1; t<=T; t++) {
			tokens = new StringTokenizer(input.readLine());
			V = Integer.parseInt(tokens.nextToken());
			E = Integer.parseInt(tokens.nextToken());
			graph = new List[V+1];
			for(int v=1; v<=V; v++) {
				graph[v] = new ArrayList<Node>();
			}
			for(int e=0; e<E; e++) {
				tokens = new StringTokenizer(input.readLine());
				int from = Integer.parseInt(tokens.nextToken());
				int to = Integer.parseInt(tokens.nextToken());
				int c = Integer.parseInt(tokens.nextToken());
				graph[from].add(new Node(to, c));
				graph[to].add(new Node(from, c));
			}
			
			output.append(String.format("%d\n", prim()));
//		}
		
		System.out.println(output);
	}
}