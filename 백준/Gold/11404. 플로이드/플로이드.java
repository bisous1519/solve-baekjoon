import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();
	
	static int N; // 도시
	static int M; // 버스
	static int[][] Bus;
	
	public static void printCost(int[] cost) {
		for(int n=1; n<=N; n++) {
			if(cost[n] == Integer.MAX_VALUE) {
				cost[n] = 0;
			}
			output.append(cost[n] + " ");
		}
		output.append("\n");
	}
	
	public static void findMinCost(int Start) {
		Queue<Integer> queue = new LinkedList<>();
		queue.offer(Start);
		
		int[] cost = new int[N+1];
		Arrays.fill(cost, Integer.MAX_VALUE);
		cost[Start] = 0;
		
		while(!queue.isEmpty()) {
			int cur = queue.poll();
			for(int n=1; n<=N; n++) {
				if(Bus[cur][n] == 0) continue;	// 버스 없 도시 안봄
				if(n == Start) continue;		// 출발지점은 안봄
				
				int newCost = cost[cur] + Bus[cur][n];
				if(newCost < cost[n]) {
					queue.offer(n);
					cost[n] = newCost;
				}
			}
		}
		
		// 최소비용 배열 출력
		printCost(cost);
	}
	
	public static void main(String[] args) throws Exception {
		N = Integer.parseInt(input.readLine());
		M = Integer.parseInt(input.readLine());
		Bus = new int[N+1][N+1];
		for(int m=0; m<M; m++) {
			tokens = new StringTokenizer(input.readLine());
			int a = Integer.parseInt(tokens.nextToken());
			int b = Integer.parseInt(tokens.nextToken());
			int c = Integer.parseInt(tokens.nextToken());
			
			if(Bus[a][b] == 0 || Bus[a][b] > c) {
				Bus[a][b] = c;
			}
		}//입력
		
		for(int start=1; start<=N; start++) {
			// 각 도시가 출발지점일 때 다른 도시로 가는 최소비용 찾기
			findMinCost(start);
		}
		
		System.out.println(output);
	}
}