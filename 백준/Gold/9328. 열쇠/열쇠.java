import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();
	
	static int TC;
	static int R, C;
	static char[][] Map;
	static boolean[] Keys;
	static boolean[][] check;
	static boolean isOpenNewDoor = false;
	static boolean isGetNewKey = false;
	static int getBook = 0;
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
		return 0<=r && r<=R+1 && 0<=c && c<=C+1;
	}
	
	public static void checkBorder(int rS, int rE, int cS, int cE) {
		for(int r=rS; r<rE; r++) {
			for(int c=cS; c<cE; c++) {
				Map[r][c] = '.';
			}
		}
	}
	
	public static void bfs() {
		Queue<Loc> queue = new LinkedList<>();
		check[0][0] = true;
		queue.offer(new Loc(0, 0));
		
		while(!queue.isEmpty()) {
			int size = queue.size();
			while(size-- > 0) {
				Loc cur = queue.poll();
				
				for(int d=0; d<dy.length; d++) {
					int goR = cur.r + dy[d];
					int goC = cur.c + dx[d];
					
					if(!isIn(goR, goC)) { // 장외
						continue;
					}
					if(check[goR][goC]) { // 이미 방문한 곳
						continue;
					}
					
					// 가!
					char curChar = Map[goR][goC];
					check[goR][goC] = true;
					if(curChar == '*') { // 벽
						continue;
					}
					if(curChar == '$') { // 문서 찾음!
						getBook++;
					}
					else if('A' <= curChar && curChar <= 'Z') { // 문
						if(Keys[curChar - 'A']) {
							isOpenNewDoor = true;
						}else {
							continue;
						}
					}
					else if('a' <= curChar && curChar <= 'z') { // 열쇠
						isGetNewKey = true;
						Keys[curChar - 'a'] = true;
					}
					
					Map[goR][goC] = '.';
					queue.offer(new Loc(goR, goC));
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		TC = Integer.parseInt(input.readLine());
		for(int tc=1; tc<=TC; tc++) {
			tokens = new StringTokenizer(input.readLine());
			R = Integer.parseInt(tokens.nextToken());
			C = Integer.parseInt(tokens.nextToken());
			Map = new char[R+2][C+2];
			Keys = new boolean[26];
			for(int r=1; r<=R; r++) {
				String[] temp = new String[C];
				temp = input.readLine().split("");
				for(int c=1; c<=C; c++) {
					Map[r][c] = temp[c-1].charAt(0);
				}
			}
			String temp = "";
			temp = input.readLine();
			// 입력
			
			// 키 저장
			if(temp.charAt(0) != '0') {
				for(int r=0; r<temp.length(); r++) {
					Keys[temp.charAt(r) - 'a'] = true;
				}
			}
			
			// 빌딩 밖에서 들어올 수 있게 테두리에 . 넣기
			checkBorder(0, 1, 0, C+1); // 맨 윗줄
			checkBorder(1, R+2, 0, 1); // 왼쪽줄
			checkBorder(0, R+1, C+1, C+2); // 오른쪽줄
			checkBorder(R+1, R+2, 1, C+2); // 맨 아랫줄
			
			// 문서 찾으러 Map 돌기 시작!
			getBook = 0;
			do {
				isOpenNewDoor = false;
				isGetNewKey = false;
				check = new boolean[R+2][C+2];
				bfs();
			}while(isOpenNewDoor || isGetNewKey); // 문 못열었거나 새 키 못찾았으면 끝
			
			output.append(getBook + "\n");
		}
		
		System.out.println(output);
	}
}