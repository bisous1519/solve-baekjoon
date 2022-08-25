import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	static int N; // 맵 행
	static int M; // 맵 열
	static int P; // 플레이어 수
	static int HP; // 보스 체력
	static final int pSize = 26; // a~z
	static char[][] Map;
	static Player[] player = new Player[pSize];
	static int[] dy = {-1, 0, 1, 0}; // 상 우 하 좌
	static int[] dx = {0, 1, 0, -1};
	
	static class Player {
		int r;
		int c;
		int dps;
		int depth;
		boolean isGet;
		public Player(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}
	
	public static boolean isIn(int r, int c) {
		return 0<=r && r<N && 0<=c && c<M;
	}
	
	public static int bfs(int r, int c) {
		Queue<Player> queue = new LinkedList<>();
		boolean[][] isVisited = new boolean[N][M];
		isVisited[r][c] = true;
		queue.offer(new Player(r, c));
		
		int depth = 0;
		while(!queue.isEmpty()) {
			depth++;
			int qSize = queue.size();
			while(qSize-- > 0) {
				Player curP = queue.poll();
				
				for(int d=0; d<dy.length; d++) {
					int goR = curP.r + dy[d];
					int goC = curP.c + dx[d];
					
					// 장외면 안됨
					if(!isIn(goR, goC)) {
						continue;
					}
					
					// 방문한 곳이면 안됨
					if(isVisited[goR][goC]) {
						continue;
					}
					
					// Boss 만난거면!
					if(Map[goR][goC] == 'B') {
						return depth; // 최단 거리 반환
					}
					
					// 갈 수 있는 길이면!
					if(Map[goR][goC] != 'X') {
						isVisited[goR][goC] = true;
						queue.offer(new Player(goR, goC));
					}
				}
			}
		}
		
		return -1;
	}
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		P = Integer.parseInt(tokens.nextToken());
		Map = new char[N][M];
		for(int n=0; n<N; n++) {
			Map[n] = input.readLine().toCharArray();
		}
		for(int n=0; n<N; n++) {
			for(int m=0; m<M; m++) {
				if('a' <= Map[n][m] && Map[n][m] <= 'z') { // 플레이어 위치 저장
					int id = Map[n][m] - 'a';
					player[id] = new Player(n, m);
				}
			}
		}
		
		// 플레이어 정보 저장
		for(int p=0; p<P; p++) {
			tokens = new StringTokenizer(input.readLine());
			int id = tokens.nextToken().charAt(0) - 'a'; // a -> 0, z->25 번 인덱스에 정보 저장
			player[id].dps = Integer.parseInt(tokens.nextToken()); 
		}
		
		// 보스 체력 입력 받음
		HP = Integer.parseInt(input.readLine());
		
		// 각 플레이어마다 bfs 돌리면서 보스까지 최단거리 구함
		for(Player p : player) {
			if(p != null) { // 맵에 있는 플레이어
				int depth = bfs(p.r, p.c);
				p.depth = depth;
			}
		}
		
		// 보스 때리기
		int sec = 0;
		while(HP > 0) {
			sec++;
			for(Player p : player) {
				if(p != null) {
					if(sec >= p.depth) {
						p.isGet = true;
						HP -= p.dps;
					}
				}
			}
		}
		
		// 한번도 못때린 플레이어 몇명인지
		int cnt = 0;
		for(Player p : player) {
			if(p != null) {
				if(p.isGet) {
					cnt++; // 보스 때린 적 있는 사람 수 세기
				}
			}
		}
		
		// 출력
		System.out.println(cnt);
	}
}