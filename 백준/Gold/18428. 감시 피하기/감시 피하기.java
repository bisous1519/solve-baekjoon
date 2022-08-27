import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	static int N;
	static String[][] Map;
	static List<Teacher> tList = new ArrayList<>();
	static int[] dy = {-1, 0, 1, 0}; // 상 우 하 좌
	static int[] dx = {0, 1, 0, -1};
	
	public static class Teacher {
		int r;
		int c;
		public Teacher(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}
	
	public static boolean isIn(int r, int c) {
		return 0<=r && r<N && 0<=c && c<N;
	}
	
	public static boolean canHide() {
		int size = tList.size();
		for(int i=0; i<size; i++) {
			Teacher t = tList.get(i);
			for(int d=0; d<dy.length; d++) {
				int cr = t.r;
				int cc = t.c;
				while(true) {
					cr += dy[d];
					cc += dx[d];
					if(!isIn(cr, cc)) { // 네 다음사방~
						break;
					}
					if(Map[cr][cc].equals("O")) { // 장애물위치! 이쪽방향 탐색 끝냄
						break;
					}
					if(Map[cr][cc].equals("S")) { // 학생 감시에 걸림
						return false;
					}
				}
			}
		}
		return true; // 아무도 감시 걸린 학생 없음!!
	}
	
	public static boolean combi(int nth, int[] choosed, int start) {
		if(nth == 3) { // 장애물 설치할 장소 3곳 고름
			
			// 장애물 위치 체크
			for(int i=0; i<choosed.length; i++) {
				Map[choosed[i]/N][choosed[i]%N] = "O";
			}
			
			// 모든 학생이 피할 수 있는 상황인지 확인
			if(canHide()){
				return true;
				
			// 장애물 위치 체크한거 원복
			}else {
				for(int i=0; i<choosed.length; i++) {
					Map[choosed[i]/N][choosed[i]%N] = "X";
				}
				return false;
			}
		}
		
		for(int i=start; i<N*N; i++) {
			int r = i / N;
			int c = i % N;
			if(Map[r][c].equals("X")) {
				choosed[nth] = i;
				if(combi(nth + 1, choosed, i + 1)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(input.readLine());
		Map = new String[N][N];
		for(int n=0; n<N; n++) {
			Map[n] = input.readLine().split(" ");
		}
		
		// 선생님 위치 미리 저장해놓기
		for(int r=0; r<N; r++) {
			for(int c=0; c<N; c++) {
				if(Map[r][c].equals("T")) {
					tList.add(new Teacher(r, c));
				}
			}
		}
		
		// 빈 칸 중심으로 combi 돌리고
		// 각 조합 상황마다 모든 학생이 감시 피할 수 있는 상황인지 확인
		// 한 명이라도 감시 못피하면 return 해서 다음 조합찾고
		// 모든 학생이 감시 피할 수 있는 상황인 조합이면
		// YES 출력하고 끝
		if(combi(0, new int[3], 0)) {
			System.out.println("YES");
		}else {
			System.out.println("NO");
		}
	}
}