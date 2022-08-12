import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	static int N; // N*N Map 크기
	static int M; // M개 빼곤 치킨집 다 폐업
	static List<int[]> Home = new ArrayList<>(); // 집 좌표
	static List<int[]> Chicken = new ArrayList<>(); // 치킨집 좌표
	static int[][] dis; // 치킨집-집 간의 거리
	static int Min = Integer.MAX_VALUE;
	
	public static void combi(int nth, int[] choosed, int start) {
		if(nth == M) {
			int sum = 0;
			for(int i=0; i<Home.size(); i++) {
				int min = Integer.MAX_VALUE;
				for(int j=0; j<choosed.length; j++) {
					min = Math.min(min, dis[choosed[j]][i]);
				}
				sum += min;
			}
			Min = Math.min(Min, sum);
			return;
		}
		for(int i=start; i<Chicken.size(); i++) {
			choosed[nth] = i;
			combi(nth + 1, choosed, i + 1);
		}
	}
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		for(int r=0; r<N; r++) {
			tokens = new StringTokenizer(input.readLine());
			for(int c=0; c<N; c++) {
				int map = Integer.parseInt(tokens.nextToken());
				if(map == 1) {
					Home.add(new int[] {r, c});
				}else if(map == 2) {
					Chicken.add(new int[] {r, c});
				}
			}
		}
		// 각 치킨집부터 집까지의 거리 구하기
		dis = new int[Chicken.size()][Home.size()];
		for(int c=0; c<Chicken.size(); c++) {
			for(int h=0; h<Home.size(); h++) {
				dis[c][h] = Math.abs(Chicken.get(c)[0] - Home.get(h)[0])
						  + Math.abs(Chicken.get(c)[1] - Home.get(h)[1]);
			}
		}		
		if(M > Chicken.size()) M = Chicken.size();
		combi(0, new int[M], 0);
		System.out.println(Min);
	}
}