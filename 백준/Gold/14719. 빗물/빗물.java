import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	static int H; // 세로길이
	static int W; // 가로길이
	static int[] Blocks;
	static int Sum; // 고인 빗물 합
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		H = Integer.parseInt(tokens.nextToken());
		W = Integer.parseInt(tokens.nextToken());
		Blocks = new int[W];
		tokens = new StringTokenizer(input.readLine());
		for(int i=0; i<W; i++) {
			Blocks[i] = Integer.parseInt(tokens.nextToken());
		}
		// 왼쪽에서부터 고일 곳(양쪽이 높아서 둘러싸인곳)이 있는지 탐색
		for(int i=1; i<W-1; i++) {
			if(Blocks[i] < Blocks[i+1]) { // 내 오른쪽이 일단 나보다 높음
				for(int j=i-1; j>=0; j--) {
					if(Blocks[i] < Blocks[j]) { // 왼쪽에 나보다 높은 애 찾음! 
						int min = Math.min(Blocks[j], Blocks[i+1]);
						for(int k=j+1; k<=i; k++) {
							// 왼쪽에 높았던애 다음부터 나까지 오면서 빗물 고여주기!
							Sum += min - Blocks[k];
							Blocks[k] = min;
						}
						if(Blocks[i] == Blocks[i+1]) {
							break;
						}
					}
				}
			}
		}
		System.out.println(Sum);
	}
}