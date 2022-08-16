import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static int N;
	static int[] Kg = {5, 3};
	
	public static int delivery(int n, int kgIdx, int sum) {
		if(n == 0 || kgIdx == Kg.length) {
			if(n == 0) { // 봉지 최소 개수 출력
				return sum;
			}else { // 정확하게 N키로 못만듦
				return -1;
			}
		}
		
		int tSum = n / Kg[kgIdx];
		int remainKg = n % Kg[kgIdx];
		
		while(true) {
			int result = delivery(remainKg, kgIdx + 1, sum + tSum);
			if(result == -1) { // 이 방법 아님
				tSum--;
				if(tSum < 0) { // 아예 이전 설탕봉지 나누는 단계로 돌아가야됨
					return -1;
				}
				remainKg = n - Kg[kgIdx] * tSum;
			}else {
				return result; // 이 방법 맞음
			}
		}
	}

	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(input.readLine());
		System.out.println(delivery(N, 0, 0));
	}
}