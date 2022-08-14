import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;

	static int N; // 책의 개수
	static int M; // 한번에 드는 책의 개수
	static int[] Books;
	static int MyIdx; // 내 위치
	static int Move; // 총 움직이는 걸음수
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		Books = new int[N + 1];
		tokens = new StringTokenizer(input.readLine());
		for(int n=0; n<N; n++) {
			Books[n] = Integer.parseInt(tokens.nextToken());
		}
		Books[N] = 0; // 내 위치
		Arrays.sort(Books);
		for(int n=0; n<=N; n++) {
			if(Books[n] == 0) {
				MyIdx = n;
				break;
			}
		}
		// 내 왼쪽으로 가기
		int goToIdx = 0;
		while(goToIdx < MyIdx) {
			Move += (Books[goToIdx] * -1) * 2;
			goToIdx += M;
		}
		// 내 오른쪽으로 가기
		goToIdx = Books.length - 1;
		while(goToIdx > MyIdx) {
			Move += (Books[goToIdx]) * 2;
			goToIdx -= M;
		}
		// 나랑 가장 먼 놈 빼주기
		Move -= Math.max(Math.abs(Books[0]), Math.abs(Books[Books.length - 1]));
		System.out.println(Move);
	}
}