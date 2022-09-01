import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();
	
	static int TC;
	static int N, M, T; // 정점, 간선, 목적지 후보개수
	static int S, G, H; // 출발점, 경유정점 두개(이 사이 간선)
	static List<Node>[] nodeList;
	static int[] d;
	static boolean[] isVisited; // 최단경로인지
	static boolean[][] fromGH; // g, h 사이 도로 지나온건지 체크
	
	static class Node {
		int to;
		int dis;
		public Node(int to, int dis) {
			this.to = to;
			this.dis = dis;
		}
	}
	
	public static void main(String[] agrs) throws IOException {
		TC = Integer.parseInt(input.readLine());
		for(int tc=1; tc<=TC; tc++) {
			tokens = new StringTokenizer(input.readLine());
			N = Integer.parseInt(tokens.nextToken());
			M = Integer.parseInt(tokens.nextToken());
			T = Integer.parseInt(tokens.nextToken());
			
			tokens = new StringTokenizer(input.readLine());
			S = Integer.parseInt(tokens.nextToken());
			G = Integer.parseInt(tokens.nextToken());
			H = Integer.parseInt(tokens.nextToken());
			
			// 초기화
			nodeList = new List[N+1];
			d = new int[N+1];
			isVisited = new boolean[N+1];
			fromGH = new boolean[2][N+1];
			for(int n=1; n<=N; n++) {
				nodeList[n] = new ArrayList<>();
				d[n] = Integer.MAX_VALUE;
			}
			
			for(int m=0; m<M; m++) {
				tokens = new StringTokenizer(input.readLine());
				int from = Integer.parseInt(tokens.nextToken());
				int to = Integer.parseInt(tokens.nextToken());
				int d = Integer.parseInt(tokens.nextToken());
				
				nodeList[from].add(new Node(to, d));
				nodeList[to].add(new Node(from, d));
			}
			
			for(int t=0; t<T; t++) {
				int a = Integer.parseInt(input.readLine());
				fromGH[0][a] = true; // 목적지 후보에 체크
			}
			// 입력 끝
			
			// 각 노드에 가는 최단거리 갱신
			// 초기화
			Arrays.fill(d, Integer.MAX_VALUE);
			d[S] = 0;
			// 가자
			for(int i=1; i<=N; i++) {
				
				// 현재까지 중 가장 최단거리인 노드찾기
				int min = Integer.MAX_VALUE;
				int minN = -1;
				for(int n=1; n<=N; n++) {
					if(!isVisited[n] && d[n] < min) {
						min = d[n];
						minN = n;
					}
				}
				if(minN == -1) {
					break;
				}
				isVisited[minN] = true;
				
				// 찾은 노드에서 갈 수 있는 노드들 최단거리 갱신
				for(int n=0; n<nodeList[minN].size(); n++) {
					Node node = nodeList[minN].get(n);
					if(!isVisited[node.to]) {
						if(d[minN] + node.dis <= d[node.to]) {
							
							// G-H 간선 경유 여부 체크
							// 지금 노드가 경유간선 양쪽 노드면
							if((minN == G || minN == H) && (node.to == G || node.to == H)) {
								fromGH[1][node.to] = true;
							
							// 지금 노드가 경유간선을 통해 온 노드였으면
							}else if(fromGH[1][minN]) {
								fromGH[1][node.to] = true;
							
							// 경유간선 통하지 않은 노드
							}else if(d[minN] + node.dis < d[node.to] && !fromGH[1][minN]) {
								fromGH[1][node.to] = false; 
							}
							
							d[node.to] = d[minN] + node.dis;
						}
					}
				}
			}
			
			// 목적지 후보들 중 가능했던 목적지 출력
			for(int n=1; n<=N; n++) {
				if(fromGH[0][n] && fromGH[1][n]) {
					output.append(n + " ");
				}
			}
			output.append("\n");
			
		}
			
		System.out.println(output);
	}
}