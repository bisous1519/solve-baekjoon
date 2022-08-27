import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();
	
	static int V; // 정점의 개수
	static int E; // 간선의 개수
	static int K; // 시작 정점의 번호
	static List<Vertex>[] vList;
	static int[] d; // 시작노드로부터 모든 정점들간의 거리를 저장할 배열
	static boolean[] isVisited; // 최단 경로 값이 정해졌는지 여부
	
	public static class Vertex {
		int to;
		int weight;
		public Vertex(int to, int weight) {
			this.to = to;
			this.weight = weight;
		}
	}
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		V = Integer.parseInt(tokens.nextToken());
		E = Integer.parseInt(tokens.nextToken());
		K = Integer.parseInt(input.readLine());
		
		// 정점의 방향성과 가중치를 저장할 정점리스트 초기화
		vList = new List[V];
		for(int v=0; v<V; v++) {
			vList[v] = new ArrayList<>();
		}
		
		// 정점의 방향성과 가중치 저장
		for(int e=0; e<E; e++) {
			tokens = new StringTokenizer(input.readLine());
			int from = Integer.parseInt(tokens.nextToken()) - 1;
			int to = Integer.parseInt(tokens.nextToken()) - 1;
			int weight = Integer.parseInt(tokens.nextToken());
			vList[from].add(new Vertex(to, weight));
		}
		
		// 초기화
		d = new int[V]; // 0~V-1
		Arrays.fill(d, Integer.MAX_VALUE);
		d[K-1] = 0; // 시작정점의 최단경로는 0
		isVisited = new boolean[V];
		
		// 최단경로 찾으러 가자!
		for(int i=0; i<V; i++) {
			int min = Integer.MAX_VALUE;
			int minV = -1;
			// 현재 정해진 정점 거리 중 최소값 찾고
			for(int v=0; v<V; v++) {
				if(!isVisited[v] && d[v] < min) {
					min = d[v];
					minV = v;
				}
			}
			if(min == Integer.MAX_VALUE) {
				break; // 이제 더 이상 K번 노드에서 갈 수 있는곳이 없음
			}
			isVisited[minV] = true;
			//해당 정점에서 갈 수 있는 노드들 경로 갱신
			for(Vertex vt : vList[minV]) {
				if(!isVisited[vt.to]
					&& d[vt.to] > d[minV] + vt.weight) {
					d[vt.to] = d[minV] + vt.weight; 
				}
			}
		}
		
		// 출력
		for(int a : d) {
			if(a != Integer.MAX_VALUE) {
				output.append(a + "\n");
			}else {
				output.append("INF\n");
			}
		}
		System.out.println(output);
	}
}