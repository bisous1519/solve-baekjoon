import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	static int N;	// 교차로 개수
	static int M;	// 골목 개수
	static int A;	// 시작 교차로 번호
	static int B;	// 도착 교차로 번호
	static int C;	// 가진 돈
	static int Min = Integer.MAX_VALUE;
	static List<Node>[] list;
	
	static class Node {
		int num;
		int cost;
		public Node(int num, int cost) {
			this.num = num;
			this.cost = cost;
		}
	}
	
	public static void dfs(int curN, boolean[] visited, int totalCost, int maxCost) {
		// C원을 초과한 경우 빠꾸
		if(totalCost > C) return;
		
		// C원 이하로 도착 교차로에 도착한 경우
		if(curN == B) {
			Min = Math.min(Min, maxCost);
			return;
		}
		
		for(int i=0; i<list[curN].size(); i++) {
			int nextN = list[curN].get(i).num;
			if(!visited[nextN]) { // 아직 방문하지 않은곳
				int nextC = list[curN].get(i).cost;
				visited[nextN] = true;
				
				dfs(nextN, visited,	totalCost+nextC, Math.max(maxCost, nextC));
				visited[nextN] = false;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		A = Integer.parseInt(tokens.nextToken());
		B = Integer.parseInt(tokens.nextToken());
		C = Integer.parseInt(tokens.nextToken());
		list = new List[N+1];
		for(int i=1; i<=N; i++) {
			list[i] = new ArrayList<>();
		}
		for(int i=0; i<M; i++) {
			tokens = new StringTokenizer(input.readLine());
			int a = Integer.parseInt(tokens.nextToken());
			int b = Integer.parseInt(tokens.nextToken());
			int c = Integer.parseInt(tokens.nextToken());
			list[a].add(new Node(b, c));
		}// 입력
		
		boolean[] visited = new boolean[N+1];
		visited[A] = true;
		dfs(A, visited, 0, -1);
		
		if(Min == Integer.MAX_VALUE) {
			System.out.println(-1);
		}else {
			System.out.println(Min);
		}
	}
}