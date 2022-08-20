import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	static int N;
	static int M;
	static int D;
	static List<Enemy> eList = new ArrayList<>();
	static int eSize; // 적의 수
	static int Max = Integer.MIN_VALUE; // 죽일 수 있는 최대
	
	public static class Enemy implements Comparable<Enemy> {
		int r;
		int c;
		public Enemy(int r, int c) {
			this.r = r;
			this.c = c;
		}
		@Override
		// 적의 위치를 r기준 내림차순, r같으면 c기준 오름차순 정렬
		public int compareTo(Enemy o) {
			return this.r != o.r
				   ? Integer.compare(this.r, o.r) * -1
				   : Integer.compare(this.c, o.c);  
		}
	}
	
	public static void killEnemy(int[] choosed, int[][] eTmp) {
		int cnt = 0; // 궁수가 죽인 적의 수
		
		// 턴 돌리기 - 적 죽이러가기
		int turnEnd = N - eTmp[eSize - 1][0]; // 가장 멀리있는 적과 캐슬의 거리
		for(int turn=0; turn<=turnEnd; turn++) {
			Queue<Integer> killed = new LinkedList<>();
			int nthK = 0;
			for(int nthA=0; nthA<choosed.length; nthA++) { // 궁수
				int aR = N;
				int aC = choosed[nthA];
				int minDis = D + 1;
				int minE = 0;
				for(int nthE=0; nthE<eSize; nthE++) { // 적
					if(eTmp[nthE][0] == N) { // 이미 죽은 적
						continue;
					}
					int eR = eTmp[nthE][0] + turn;
					int eC = eTmp[nthE][1];
					if(eR >= N) { // 캐슬에 도착한 적
						eTmp[nthE][0] = N;
						continue;
					}
					int dis = Math.abs(aR - eR) + Math.abs(aC - eC);
					if(dis <= D) { // 나랑 가장 가까운 적인지 확인
						if(dis == minDis) {
							if(eTmp[nthE][1] < eTmp[minE][1]) {
								minDis = dis;
								minE = nthE;
							}
						}
						else if(dis < minDis) {
							minDis = dis;
							minE = nthE;
						}
					}
				}
				if(minDis < D + 1) { // nthA가 죽인 적이 있음!
					killed.offer(minE);
				}
			}
			while(!killed.isEmpty()) { // 이번턴에서 화살맞은 적들 처리
				int nthE = killed.poll();
				if(eTmp[nthE][0] != N) {
					eTmp[nthE][0] = N;
					cnt++;
				}
			}
		}
		Max = Math.max(Max, cnt);
	}
	
	public static void armyComb(int nth, int[] choosed, int start) {
		if(nth == choosed.length) {
			// 궁수 3명 위치선정 완료
			int[][] eTmp = new int[eSize][2];
			for(int i=0; i<eSize; i++) {
				eTmp[i][0] = eList.get(i).r;
				eTmp[i][1] = eList.get(i).c;
			}
			// 이제 죽여보자!
			killEnemy(choosed, eTmp);
			return;
		}
		for(int i=start; i<M; i++) {
			choosed[nth] = i;
			armyComb(nth + 1, choosed, i + 1);
		}
	}
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		D = Integer.parseInt(tokens.nextToken());
		for(int n=0; n<N; n++) {
			tokens = new StringTokenizer(input.readLine());
			for(int m=0; m<M; m++) {
				if(tokens.nextToken().charAt(0) == '1') {
					eList.add(new Enemy(n, m)); // 적의 위치 (n,m) list에 저장
				}
			}
		}
		
		// 적 위치 정렬 - 궁수와 가까운 순 / D거리 범위로 만들기
		Collections.sort(eList);
		eSize = eList.size();
		int t = N - eList.get(0).r; // 궁수(N행)와 가장 가까이 있는 적의 거리
		if(t > D) {
			t -= D; // D 거리 까지 적들 끌어옴
			for(int i=0; i<eSize; i++) {
				eList.get(i).r += t;
			}
		}
		
		// 궁수 위치 선정해서 최대죽이는 적 찾기
		armyComb(0, new int[3], 0);
		System.out.println(Max);
	}
}