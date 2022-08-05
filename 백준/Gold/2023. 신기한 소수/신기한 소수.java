import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	private static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	private static StringBuilder sb = new StringBuilder();
	private static StringTokenizer tokens;
	
	private static int N; // N자리 수
	private static int[] start = {2, 3, 5, 7};	// 첫째자리에 올 수 있는 숫자
	private static int[] end = {1, 3, 7, 9};	// 그 외의 자리에 올 수 있는 숫자 
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		permutationDup(0, new int[N]);
		System.out.println(sb);
	}
	
	public static void permutationDup(int nth, int[] choosed) {
		if(nth == N) { // 기저조건
			// 신기한 소수가 맞으면 출력, 아니면 그냥 리턴
			if(isSpecialPrime(choosed)) {
				int num = 0;
				for(int i=0; i<N; i++) {
					num *= 10;
					num += choosed[i];
				}
				sb.append(num).append("\n");
			}
			return;
		}
		if(nth == 0) {
			// start 배열에서만 가져와서 선택
			for(int i=0; i<start.length; i++) {
				choosed[nth] = start[i];
				permutationDup(nth + 1, choosed);
			}
		}else {
			// end 배열에서만 가져와서 선택
			for(int i=0; i<end.length; i++) {
				choosed[nth] = end[i];
				permutationDup(nth + 1, choosed);
			}
		}
	}
	
	public static boolean isSpecialPrime(int[] choosed) {
		int num = 0;
		for(int i=0; i<N; i++) {
			num *= 10;
			num += choosed[i];
			for(int j=2; j<=(int)Math.sqrt(num); j++) {
				if(num % j == 0) {
					return false;
				}
			}
		}
		return true;
	}
}