import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.io.IOException;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	
	static final int N = 12;
	static final int M = 6;
	static char[][] Map;
	static int[] toR = new int[M];
	static List<Loc> pList; // 빈 칸 처리해야하는 뿌요좌표 저장
	static boolean[] pullC; // 중력 처리해야하는 col 체크
	static int ans;
	static int[] dy = {-1, 0, 1, 0}; // 상 우 하 좌
	static int[] dx = {0, 1, 0, -1};
	
	public static class Loc {
		int r, c;
		public Loc(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}
	
	public static boolean isIn(int r, int c) {
		return 0<=r && r<N && 0<=c && c<M;
	}
	
	public static boolean bfs(int r, int c, char Puyo) {
		Queue<Loc> queue = new LinkedList<>();
		boolean[][] isVisited = new boolean[N][M];
		pList.add(new Loc(r, c));
		queue.offer(new Loc(r, c));
		isVisited[r][c] = true;
		
		int cnt = 1; // 인접한 뿌요 개수
		while(!queue.isEmpty()) {
			int size = queue.size();
			while(size-- > 0) {
				Loc cur = queue.poll();
				for(int d=0; d<dy.length; d++) {
					int goR = cur.r + dy[d];
					int goC = cur.c + dx[d];
					// 장외는 돌아가~
					if(!isIn(goR, goC)) {
						continue;
					}
					// 같은 색 뿌요임!
					if(!isVisited[goR][goC] && Map[goR][goC] == Puyo) {
						isVisited[goR][goC] = true;
						pList.add(new Loc(goR, goC));
						queue.offer(new Loc(goR, goC));
						cnt++;
					}
				}
			}
		}
		
		return cnt >= 4 ? true : false;
	}
	
	public static void main(String[] args) throws IOException {
		Map = new char[N][M];
		for(int n=0; n<N; n++) {
			Map[n] = input.readLine().toCharArray();
		}
		
		// 각 열에 몇 행 아래부터 뿌요가 있는지 저장
		for(int c=0; c<M; c++) {
			for(int r=0; r<N; r++) {
				if(Map[r][c] != '.') {
					toR[c]++;
				}
			}
			toR[c] = N - 1 - toR[c]; // : c열에는 cntC[c]+1 ~ N-1 까지 뿌요가 있음
		}
		
		// 현 상황에서 만나는 뿌요마다
		// --> BFS
		//     --> 4개 이상이면 빈칸처리
		// --> 현 상황의 뿌요를 다 돌았으면
		//     --> 공중부양한 뿌요들 다 쭉 내려주고
		//     --> 각 열에 어디까지 뿌요 있는지 배열 갱신
		//     --> 연쇄++
		// 이거를 4개 이상인 뿌요무리가 아예 없을때까지 반복
		// 연쇄 출력하고 종료
		while(true) {
			boolean killPuyo = false; // 이번 턴에 터진 뿌요 있는지 체크
			
			// 뿌요 찾으러 떠나기
			for(int c=0; c<M; c++) {
				for(int r=N-1; r>toR[c]; r--) {
					
					// 뿌요 찾음
					if(Map[r][c] != '.') {
						pList = new ArrayList<>(); // 빈 칸 처리해야하는 뿌요 좌표 저장
						
						// 만난 뿌요 기준으로 bfs
						if(bfs(r, c, Map[r][c])) {
							
							// 인접뿌요가 4개 이상임 --> 빈 칸 처리
							killPuyo = true;
							pullC = new boolean[M];
							for(Loc p : pList) {
								Map[p.r][p.c] = '.';
								pullC[p.c] = true; // 중력 처리해야하는 col 체크
							}
						}
					}
				}
			}
			
			// 이번 턴에 터진 뿌요 없음!!! 뿌요 연쇄살인 끝
			if(!killPuyo) {
				System.out.println(ans);
				break;
			}
			
			// 이번 턴에 터진 뿌요 있음
			ans++;
			
			// 공중부양한 뿌요 다 내려주기
			for(int c=0; c<M; c++) {
				
				// 중력처리 해야하는 열이면
				if(pullC[c]) {
					int dotR = -1;
					// 빈 칸 채워서 내려주고
					for(int r=N-1; r>toR[c]; r--) {
						if(dotR == -1 && Map[r][c] == '.') {
							dotR = r;
						}else if(dotR != -1 && Map[r][c] != '.') {
							Map[dotR--][c] = Map[r][c];
							Map[r][c] = '.';
						}
					}
					// 어디까지 뿌요있는지 갱신
					toR[c] = dotR;
				}
			}
	 	} // while
	}
}