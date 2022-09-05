import java.io.BufferedReader;
//import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();
	
	static int TC;
	static int N, M, K;
	static Group[] group;
	static int[] dy = {0, -1, 1, 0, 0}; // 0 상 하 좌 우
	static int[] dx = {0, 0, 0, -1, 1};
	
	static class Group {
		int r;
		int c;
		int cnt;
		int dir;
		public Group(int r, int c) {
			this.r = r;
			this.c = c;
		}
		public Group(int r, int c, int cnt, int dir) {
			this.r = r;
			this.c = c;
			this.cnt = cnt;
			this.dir = dir;
		}
	}
	
	static class Des {
		int k;
		int sum;
		public Des(int k, int sum) {
			this.k = k;
			this.sum = sum;
		}
	}
	
	public static boolean isInRedCell(int r, int c) {
		return r == 0 || r == N-1 || c == 0 || c == N-1;
	}

	public static void main(String[] args) throws IOException {
//		input = new BufferedReader(new FileReader("./input.txt"));
		TC = Integer.parseInt(input.readLine());
		for(int tc=1; tc<=TC; tc++) {
			tokens = new StringTokenizer(input.readLine());
			N = Integer.parseInt(tokens.nextToken());
			M = Integer.parseInt(tokens.nextToken());
			K = Integer.parseInt(tokens.nextToken());
			group = new Group[K];
			for(int k=0; k<K; k++) {
				tokens = new StringTokenizer(input.readLine());
				int r = Integer.parseInt(tokens.nextToken());
				int c = Integer.parseInt(tokens.nextToken());
				int cnt = Integer.parseInt(tokens.nextToken());
				int dir = Integer.parseInt(tokens.nextToken());
				group[k] = new Group(r, c, cnt, dir);
			} // 입력
			
			// 시간 흐르기 시작
			for(int m=1; m<=M; m++) {
				Des[][] Map = new Des[N][N];
				for(int k=0; k<K; k++) {
					if(group[k].r == -1) { // 사라진 군집
						continue;
					}
					group[k].r += dy[group[k].dir];
					group[k].c += dx[group[k].dir];
					
					// 약품 셀에 도착 처리
					if(isInRedCell(group[k].r, group[k].c)) {
						group[k].cnt /= 2;
						if(group[k].cnt == 0) {
							group[k].r = -1;
							continue;
						}
						group[k].dir = group[k].dir == 1 ? 2 : group[k].dir == 2 ? 1 : group[k].dir == 3 ? 4 : 3;
						continue;
						// 어짜피 셀에 도착하는애는 하나니까 Map에 체크 안해줘도 됨
					}
					
					// Map에 위치 체크하기
					if(Map[group[k].r][group[k].c] != null) { // 아무것도 없는게 null이 맞는지 확인해보기
						// 겹침! 이 셀에 같이 도착하는 군집이 있음!
						int preK = Map[group[k].r][group[k].c].k; // 기존에 있던 k
						int curK = k;
						Map[group[k].r][group[k].c].sum += group[curK].cnt;
						if(group[preK].cnt > group[curK].cnt) {
							// 원래 있던게 미생물 더 많음
							group[curK].r = -1; // 현재 군집 없애기
						}else {
							// 현재 군집에 미생물 더 많음
							Map[group[k].r][group[k].c].k = curK;
							group[preK].r = -1; // 기존 군집 없애기
						}
						
						
					}else {
						// 안겹침 (나만도착) --> 체크해주기
						Map[group[k].r][group[k].c] = new Des(k, group[k].cnt);
					}
				}
				
				for(int k=0; k<K; k++) {
					if(group[k].r != -1 && !isInRedCell(group[k].r, group[k].c)) {
						group[k].cnt = Map[group[k].r][group[k].c].sum;
					}
				}
			}
			
			// 최종 종 미생물 수
			int sum = 0;
			for(int k=0; k<K; k++) {
				if(group[k].r != -1) {
					sum += group[k].cnt;
				}
			}
			output.append(String.format("#%d %d\n", tc, sum));
		}
		
		System.out.println(output);
	}
}
