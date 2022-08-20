import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();
	
	static int R1;
	static int C1;
	static int R2;
	static int C2;
	static int[][] ans;
	static int[] dy = {-1, 0, 1, 0}; // 상 좌 / 하 우 
	static int[] dx = {0, -1, 0, 1};
	static int cr, cc, num, gapR, gapC, cnt, max, depth;
	
	public static void isInRange(int r, int c) {
		if(R1 <= r && r<= R2 && C1 <= c && c <= C2) {
			ans[cr - gapR][cc - gapC] = num;
			cnt--;
			max = Math.max(max, num);
		}
	}
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		R1 = Integer.parseInt(tokens.nextToken());
		C1 = Integer.parseInt(tokens.nextToken());
		R2 = Integer.parseInt(tokens.nextToken());
		C2 = Integer.parseInt(tokens.nextToken());
		
		// 초기화
		cr = 0; // 1이 들어가는 인덱스 (0, 0)
		cc = 0;
		num = 1; // 1부터 시작
		gapR = R1 - 0;
		gapC = C1 - 0;
		cnt = (R2 - R1 + 1) * (C2 - C1 + 1); // 출력할 배열에 총 채워야하는 개수
		max = Integer.MIN_VALUE; // 출력하는 수 중 가장 큰수(공백맞추는용)
		depth = 1; // 한 변에서 몇 번 가야하는지
		ans = new int[R2 - R1 + 1][C2 - C1 + 1];
		
		// (0,0)이 출력범위에 포함되는지
		isInRange(cr, cc);
		
		// 소용돌이를 쳐 보자
		while(cnt > 0) { // 채워야할 숫자가 남은 동안
			// 오른쪽으로 한 칸
			cc++;
			num++;
			isInRange(cr, cc);
			// 상 좌
			for(int dIdx=0; dIdx<=1; dIdx++) {
				for(int d=1; d<=depth; d++) {
					cr += dy[dIdx];
					cc += dx[dIdx];
					num++;
					isInRange(cr, cc);
				}
			}
			depth++;
			
			// 왼쪽으로 한 칸
			cc--;
			num++;
			isInRange(cr, cc);
			// 하 우
			for(int dIdx=2; dIdx<=3; dIdx++) {
				for(int d=1; d<=depth; d++) {
					cr += dy[dIdx];
					cc += dx[dIdx];
					num++;
					isInRange(cr, cc);
				}
			}
			depth++;
		}
		
		// 출력
		int maxLen = (int)(Math.log10(max) + 1);
		for(int r=0; r<=R2-R1; r++) {
			for(int c=0; c<=C2-C1; c++) {
				if(c != 0) output.append(" ");
				for(int l=0; l<maxLen-(int)(Math.log10(ans[r][c])+1); l++) {
					output.append(" ");
				}
				output.append(ans[r][c]);
			}
			output.append("\n");
		}
		System.out.println(output);
	}
}