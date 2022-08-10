import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	private static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	private static StringTokenizer tokens;
	private static StringBuilder output = new StringBuilder();
	
	private static int N;
	private static int M;
	private static int R;
	private static int Num;
	private static int[][] Map;
	
	public static void sangHaBanJeon() {
		for(int r=0; r<N/2; r++) {
			int[] temp = Map[r];
			Map[r] = Map[N-1-r];
			Map[N-1-r] = temp;
		}
	}
	
	public static void jwaWoBanJeon() {
		for(int c=0; c<M/2; c++) {
			for(int r=0; r<N; r++) {
				int temp = Map[r][c];
				Map[r][c] = Map[r][M-c-1];
				Map[r][M-c-1] = temp;
			}
		}
	}
	
	public static void right90() {
		int[][] temp = new int[N][M];
		for(int r=0; r<N; r++) {
			for(int c=0; c<M; c++) {
				temp[r][c] = Map[r][c];
			}
		}
		for(int r=0; r<N; r++) {
			for(int c=0; c<M; c++) {
				Map[c][N-1-r] = temp[r][c];
			}
		}
		int temp2 = N;
		N = M;
		M = temp2;
	}
	
	public static void left90() {
		int[][] temp = new int[N][M];
		for(int r=0; r<N; r++) {
			temp[r] = Arrays.copyOf(Map[r], M);
		}
		for(int r=0; r<N; r++) {
			for(int c=0; c<M; c++) {
				Map[M-1-c][r] = temp[r][c];
			}
		}
		int temp2 = N;
		N = M;
		M = temp2;
	}
	
	public static void groupRight90() {
		int[][] temp = new int[N/2][M/2];
		for(int r=0; r<N/2; r++) {
			temp[r] = Arrays.copyOf(Map[r], M/2);
		}
		// 왼쪽 아래꺼 위로
		for(int r=0; r<N/2; r++) {
			for(int c=0; c<M/2; c++) {
				Map[r][c] = Map[N/2+r][c]; 
			}
		}
		// 오른쪽 아래꺼 왼쪽으로
		for(int r=N/2; r<N; r++) {
			for(int c=0; c<M/2; c++) {
				Map[r][c] = Map[r][M/2+c];
			}
		}
		// 오른쪽 위꺼 아래로
		for(int r=N/2; r<N; r++) {
			for(int c=M/2; c<M; c++) {
				Map[r][c] = Map[r-N/2][c];
			}
		}
		// 오른쪽 위에 temp
		for(int r=0; r<N/2; r++) {
			for(int c=M/2; c<M; c++) {
				Map[r][c] = temp[r][c-M/2];
			}
		}
	}
	
	public static void groupLeft90() {
		int[][] temp = new int[N/2][M/2];
		for(int r=0; r<N/2; r++) {
			temp[r] = Arrays.copyOf(Map[r], M/2);
		}
		// 오른쪽 위꺼 왼쪽으로
		for(int r=0; r<N/2; r++) {
			for(int c=0; c<M/2; c++) {
				Map[r][c] = Map[r][M/2+c];
			}
		}
		// 오른쪽 아래꺼 위로
		for(int r=0; r<N/2; r++) {
			for(int c=M/2; c<M; c++) {
				Map[r][c] = Map[N/2+r][c];
			}
		}
		// 왼쪽 아래꺼 오른쪽으로
		for(int r=N/2; r<N; r++) {
			for(int c=M/2; c<M; c++) {
				Map[r][c] = Map[r][c-M/2];
			}
		}
		// 왼쪽 아래에 temp
		for(int r=N/2; r<N; r++) {
			for(int c=0; c<M/2; c++) {
				Map[r][c] = temp[r-N/2][c];
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		R = Integer.parseInt(tokens.nextToken());
		Map = new int[Math.max(N, M)][Math.max(N, M)];
		for(int r=0; r<N; r++) {
			tokens = new StringTokenizer(input.readLine());
			for(int c=0; c<M; c++) {
				Map[r][c] = Integer.parseInt(tokens.nextToken());
			}
		}
		tokens = new StringTokenizer(input.readLine());
		for(int i=0; i<R; i++) {
			Num = Integer.parseInt(tokens.nextToken());
			switch(Num) {
			case 1: sangHaBanJeon(); break;
			case 2: jwaWoBanJeon(); break;
			case 3: right90(); break;
			case 4: left90(); break;
			case 5: groupRight90(); break;
			case 6: groupLeft90(); break;
			}
		}
		for(int r=0; r<N; r++) {
			for(int c=0; c<M; c++) {
				output.append(Map[r][c]).append(" ");
			}
			output.append("\n");
		}
		System.out.println(output);
	}
}