import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();

	static int N; // 물건 개수
	static int M; // 주어지는 무개 잰 개수
	static List<Node>[] list;
	static final boolean HEAVIER = true; // A>B : true
	static final boolean LIGHTER = false; // B<A : false
	
	static class Node {
		int num;
		boolean w; // A>B : T, A<B: F
		public Node(int num, boolean w) {
			this.num = num;
			this.w = w;
		}
	}
	
	public static boolean[] find(int start, boolean[] isVisited, boolean w) {
		Queue<Integer> queue = new LinkedList<>();
		queue.add(start);
		isVisited[start] = true;
		
		while(!queue.isEmpty()) {
			int cur = queue.poll();
			
			int size = list[cur].size();
			for(int i=0; i<size; i++) {
				Node temp = list[cur].get(i);
				if(temp.w == w && !isVisited[temp.num]) {
					isVisited[temp.num] = true; 
					queue.add(temp.num);
				}
			}
		}
		
		return isVisited;
	}
	
	public static void main(String[] args) throws Exception {
		N = Integer.parseInt(input.readLine());
		M = Integer.parseInt(input.readLine());
		
		list = new List[N+1];
		for(int n=1; n<=N; n++) {
			list[n] = new ArrayList<>();
		}//list 초기화
		
		for(int m=0; m<M; m++) {
			tokens = new StringTokenizer(input.readLine());
			int a = Integer.parseInt(tokens.nextToken()); // a>b
			int b = Integer.parseInt(tokens.nextToken());
			
			// list에 무게대로 입력받기
			list[a].add(new Node(b, true));
			list[b].add(new Node(a, false));
		}//입력
		
		// 1번~N번 물건 각각 점점큰것(T), 점점작은것(F) 돌면서 무게를 비교할 수 없는 물건 개수 구하기
		for(int n=1; n<=N; n++) {
			boolean[] isVisited = new boolean[N+1];
			isVisited = find(n, isVisited, HEAVIER);
			isVisited = find(n, isVisited, LIGHTER);
			
			int cnt = 0;
			for(int i=1; i<=N; i++) {
				if(!isVisited[i]) {
					cnt++;
				}
			}
			
			output.append(cnt).append("\n");
		}
		
		System.out.println(output);
	}
}