import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	static int R, C;
	static int M; // 상어 몇 마리
	static Shark[] shark;
	static int[][] Map;
	static int[] dy = {0, -1, 1, 0, 0}; // - 상 하 우 좌
	static int[] dx = {0, 0, 0, 1, -1 };
	
	static class Shark implements Comparable<Shark> {
		int r, c, s, d, z;
		int sec;
		boolean alive;
		public Shark(int r, int c, int s, int d, int z) {
			this.r = r;
			this.c = c;
			if(d == 1 || d == 2) { // 상하
				s %= R*2 - 2;
			}else { // 좌우
				s %= C*2 - 2;
			}
			this.s = s;
			this.d = d;
			this.z = z;
			this.sec = 0;
			this.alive = true;
		}
		public void move() {
			for(int i=1; i<=this.s; i++) {
				// 맵 끝에 닿으면
				if(this.d == 1 || this.d == 2) { // 상하로 움직이는애
					if(this.r == 1) {
						this.d = 2;
					}else if(this.r == R) {
						this.d = 1;
					}
				}
				else if(this.d == 3 || this.d == 4) { // 좌우로 움직이는애
					if(this.c == 1) {
						this.d = 3;
					}else if(this.c == C) {
						this.d = 4;
					}
				}
				// 가
				this.r += dy[this.d];
				this.c += dx[this.d];
			}
		}
		@Override 
		public int compareTo(Shark o) {
			return Integer.compare(this.z, o.z);
		}
		@Override
		public String toString() {
			return "Shark [r=" + r + ", c=" + c + ", s=" + s + ", d=" + d + ", z=" + z + "]";
		}
	}
	
	public static boolean isBorder(int r, int c) {
		return r==1 || r==R || c==1 || c==C;
	}
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		R = Integer.parseInt(tokens.nextToken());
		C = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		shark = new Shark[M+1];
		shark[0] = new Shark(0, 0, 0, 0, 0);
		for(int m=1; m<=M; m++) {
			tokens = new StringTokenizer(input.readLine());
			int r = Integer.parseInt(tokens.nextToken());
			int c = Integer.parseInt(tokens.nextToken());
			int s = Integer.parseInt(tokens.nextToken());
			int d = Integer.parseInt(tokens.nextToken());
			int z = Integer.parseInt(tokens.nextToken());
			shark[m] = new Shark(r, c, s, d, z);
		} // 입력
		
		// 상어 크기 순서대로 정렬
		Arrays.sort(shark);
		// Map에 상어 위치 체크하기
		Map = new int[R+1][C+1];
		int idx = 0;
		for(Shark s : shark) {
			Map[s.r][s.c] = idx++; 
		}
		
		// 이동하면서 상어잡기!
		int zSum = 0;
		for(int c=1; c<=C; c++) { // 1) 이동

			// 2) c열에서 땅에 가장 가까운 상어 잡기
			for(int r=1; r<=R; r++) {
				if(Map[r][c] != 0) { // 가장 가까운 상어!
					zSum += shark[Map[r][c]].z;
					shark[Map[r][c]].alive = false;
					Map[r][c] = 0;
					break;
				}
			}
			
			// 3) 상어 이동
			for(int m=1; m<=M; m++) {
				// 이미 잡은 상어는 pass
				if(!shark[m].alive) {
					continue;
				}
				
				// 기존에 있던 자리 저장
				int prevR = shark[m].r;
				int prevC = shark[m].c;
				
				// 이동
				shark[m].move();
				int toR = shark[m].r;
				int toC = shark[m].c;

				// 이동할 자리에(Map) 상어가 이미 있는데 같은 초의 상어
				if(Map[toR][toC] != 0) {
					if(shark[Map[toR][toC]].sec == c) {
						shark[Map[toR][toC]].alive = false; // 이미 있던 상어 먹힘
						
					}
				}
				
				// 뽑아둔 자리에 상어 이동시키기 (Map:새로운자리에 상어 적고 기존자리에 0, sec:++)
				if(Map[prevR][prevC] == m) {
					Map[prevR][prevC] = 0;
				}
				Map[toR][toC] = m;
				shark[m].sec = c;
				
			}
//			System.out.println("------");
//			for(int[] a : Map) {
//				System.out.println(Arrays.toString(a));
//			}
		}
		
		System.out.println(zSum);
	}
}