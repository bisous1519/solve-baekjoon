import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static int N;
	static int M;
	static int K;
	static int[][] Map;
	static int[][] ro;
	static int Min = Integer.MAX_VALUE;
	static int[] dx = {1, 0, -1, 0};
	static int[] dy = {0, 1, 0, -1};
	
	public static void rotate(int[][] MapCopy, int[] choosed) {
		Queue<Integer> queue = new LinkedList<>();
		for(int k=0; k<K; k++) {
			for(int cs=ro[choosed[k]][2]; cs>=1; cs--) {
				// 쭉 돌려서 queue에 뽑음
				int cr = ro[choosed[k]][0]-cs;
				int cc = ro[choosed[k]][1]-cs;
				for(int d=0; d<dx.length; d++) {
					for(int oneSide=0; oneSide<cs*2; oneSide++) {
						cr += dx[d];
						cc += dy[d];
						queue.add(MapCopy[cr][cc]);
					}
				}
				// queue에 있는거 한칸 밀어서 도로 넣음
				queue.add(queue.poll());
				cr = ro[choosed[k]][0]-cs;
				cc = ro[choosed[k]][1]-cs;
				for(int d=0; d<dx.length; d++) {
					for(int oneSide=0; oneSide<cs*2; oneSide++) {
						cr += dx[d];
						cc += dy[d];
						MapCopy[cr][cc] = queue.poll();
					}
				}
			}
		}
		// 배열의 값 계산
		for(int r=1; r<=N; r++) {
			int sum = 0;
			for(int c=1; c<=M; c++) {
				sum += MapCopy[r][c];
			}
			Min = Math.min(Min, sum);
		}
	}
	
	public static void permutation(int nth, int[] choosed, boolean[] isSelected) {
		if(nth == K) {
			// 만들어진 순열 순서대로 돌려보자
			int[][] MapCopy = new int[N+1][M+1];
			for(int r=1; r<=N; r++) {
				for(int c=1; c<=M; c++) {
					MapCopy[r][c] = Map[r][c];
				}
			}
			rotate(MapCopy, choosed);
			
		}
		for(int i=0; i<K; i++) {
			if(!isSelected[i]) {
				isSelected[i] = true;
				choosed[nth] = i;
				permutation(nth + 1, choosed, isSelected);
				isSelected[i] = false;
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		K = Integer.parseInt(tokens.nextToken());
		Map = new int[N+1][M+1];
		ro = new int[K][3];
		for(int r=1; r<=N; r++) {
			tokens = new StringTokenizer(input.readLine());
			for(int c=1; c<=M; c++) {
				Map[r][c] = Integer.parseInt(tokens.nextToken());
			}
		}
		for(int i=0; i<K; i++) {
			tokens = new StringTokenizer(input.readLine());
			ro[i][0] = Integer.parseInt(tokens.nextToken());
			ro[i][1] = Integer.parseInt(tokens.nextToken());
			ro[i][2] = Integer.parseInt(tokens.nextToken());
		}
		permutation(0, new int[K], new boolean[K]);
		System.out.println(Min);
	}
}