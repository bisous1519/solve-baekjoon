import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();
	
	static int T;
	static int N;
	static int[][] Map;
	static List<Loc> Tor;
	static Loc Start;
	static Loc End;
	static int ans;
	static int[] dy = {-1, 0, 1, 0}; // 상 우 하 좌
	static int[] dx = {0, 1, 0, -1};
	
	static class Loc {
		int r;
		int c;
		public Loc(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}
	
	public static boolean isIn(int r, int c) {
		return 0<=r && r<N && 0<=c && c<N;
	}
	
	public static void bfs() {
		Queue<Loc> queue = new LinkedList<>();
		queue.offer(Start);
		
		boolean[][] visited = new boolean[N][N];
		visited[Start.r][Start.c] = true;
		           
		int sec = 0;
		while(!queue.isEmpty()) {
			sec++;
			int size = queue.size();
			while(size-- > 0) {
				Loc cur = queue.poll();
				
				for(int d=0; d<dy.length; d++) {
					int goR = cur.r + dy[d];
					int goC = cur.c + dx[d];
					
					if(!isIn(goR, goC)) { // 장외
						continue;
					}
					if(goR == End.r && goC == End.c) {
						ans = sec;
						return;
					}
					if(visited[goR][goC]) { // 해당 초로 갔던 곳
						continue;
					}
					if(Map[goR][goC] == 1) { // 장애물
						continue;
					}
					
					// 소용돌이
					// -> 기다렸다 가보려고 지금자리 다시 넣음
					if(Map[goR][goC] == -2 || Map[goR][goC] == -1) {
						queue.offer(new Loc(cur.r, cur.c));
						visited[cur.r][cur.c] = true;
					}
					// -> 멈춘 소용돌이랑 0! 갈 수 있는 곳
					else {
						queue.offer(new Loc(goR, goC));
						visited[goR][goC] = true;
					}
				}// 방향 for문
			}// 1초에 이동하는 가짓수
            
			// 소용돌이 업데이트
			for(Loc tor : Tor) {
				Map[tor.r][tor.c]++;
				if(Map[tor.r][tor.c] == 0) {
					Map[tor.r][tor.c] = -3;
				}
			}
		}// outer while문
	}

	public static void main(String[] args) throws IOException {
		T = Integer.parseInt(input.readLine());
		for(int t=1; t<=T; t++) {
			N = Integer.parseInt(input.readLine());
			Map = new int[N][N];
			Tor = new ArrayList<Loc>();
			for(int r=0; r<N; r++) {
				tokens = new StringTokenizer(input.readLine());
				for(int c=0; c<N; c++) {
					Map[r][c] = Integer.parseInt(tokens.nextToken());
					if(Map[r][c] == 2) {
						Map[r][c] *= -1; // 소용돌이는 표시해주고
						Tor.add(new Loc(r, c)); // 리스트에 넣어주기
					}
				}
			}
			tokens = new StringTokenizer(input.readLine());
			Start = new Loc(Integer.parseInt(tokens.nextToken()), Integer.parseInt(tokens.nextToken()));
			
			tokens = new StringTokenizer(input.readLine());
			End = new Loc(Integer.parseInt(tokens.nextToken()), Integer.parseInt(tokens.nextToken()));
			// 입력
			
			ans = -1;
			bfs();
			output.append(String.format("#%d %d\n", t, ans));
		}
		
		System.out.println(output);
	}
}