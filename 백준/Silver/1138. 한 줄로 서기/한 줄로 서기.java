import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Main {

	private static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	private static StringTokenizer tokens;
	private static StringBuilder output = new StringBuilder();
	
	private static int N;
	private static int[] lineUp;	// 입력받는 배열
	private static int[] nth;		// 1번이 자기자리에 줄 서고 남은 자리의 index가 들어있는 배열
	private static boolean result;	// 결과가 나왔는지
	
	public static void permutation(int nthIdx, int[] choosed, boolean[] selected) {
		if(nthIdx == nth.length) {
			// 새로 choose해서 넣은 N-1명 각각의 자리가 맞는지 검사
			for(int i=0; i<N-1; i++) {
				int cnt = 0; // 내 왼쪽에 나보다 큰 사람의 수
				int pNo = choosed[nth[i]]; // 지금 내 키(내 번호)
				for(int j=nth[i]-1; j>=0; j--) {
					// 내 왼쪽에 나보다 큰 사람 있을때마다 cnt++
					if(pNo < choosed[j]) {
						cnt++;
					}
				}
				if(cnt != lineUp[pNo]) {
					return; // 이 사람 이 자리 아니야! --> 순열 다시 만들러가기
				}
			}
			// 지금 choosed의 모양이 답!
			for(int j=1; j<=N; j++) {
				output.append(choosed[j]).append(" ");
			}
			result = true;
			return;
		}
		
		// 이미 자리 정해진 1번 제외하고 나머지 순열 돌리기
		for(int i=2; i<=N; i++) {
			if(!selected[i]) {
				selected[i] = true;
				choosed[nth[nthIdx]] = i;
				permutation(nthIdx + 1, choosed, selected);
				if(result) {
					return; // 답 나왔으니 return!
				}
				selected[i] = false;
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		lineUp = new int[N+1];
		tokens = new StringTokenizer(input.readLine());
		for(int i=1; i<=N; i++) {
			lineUp[i] = Integer.parseInt(tokens.nextToken());
		}
		
		// 배열들 초기화
		int[] choosed = new int[N+1];;
		boolean[] selected = new boolean[N+1];
		nth = new int[N-1];
		choosed[lineUp[1] + 1] = 1;
		selected[1] = true;
		for(int i=1, idx=0; i<=N; i++) {
			if(choosed[i] == 0) {
				nth[idx++] = i;
			}
		}
		
		permutation(0, choosed, selected);
		System.out.println(output);
	}

}