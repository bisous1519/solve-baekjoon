import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;

	static int N; // 트럭 수
	static int W; // 다리의 길이
	static int L; // 다리 최대 하중
	static int[] truck;
	static Queue<tob> bridge = new LinkedList<>();
	
	static class tob {
		int no;  // 트럭 번호
		int sec; // 몇초대에 다리위로 올라왔는지
		public tob(int no, int sec) {
			this.no = no;
			this.sec = sec;
		}
	}
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		W = Integer.parseInt(tokens.nextToken());
		L = Integer.parseInt(tokens.nextToken());
		truck = new int[N+1];
		tokens = new StringTokenizer(input.readLine());
		for(int n=1; n<=N; n++) {
			truck[n] = truck[n-1] + Integer.parseInt(tokens.nextToken()); // 누적해서 저장
		}
		
		// 시간 흐르면서 트럭 다리위로 지나감
		int sec = 0; // 단위시간
		int nth = 1; // 현재 몇번째 트럭 올라갈 차례
		int pass = 0; // 통과한 트럭 몇대
		while(true) {
			sec++;
			
			// 통과한 트럭 있으면 먼저 빼주고
			while(!bridge.isEmpty()) {
				if(sec - bridge.peek().sec == W) { // 이거 이번에 통과하는 트럭
					bridge.poll();
					pass++;
				}else {
					break;
				}
			}
			
			// 다 통과했으면 끝
			if(pass == N) {
				break;
			}
			
			// 다리위로 올려보자
			if(nth <= N) { // 아직 지나갈 트럭이 남았음 
				if(bridge.size() < W) { // 다리위에 더 올라가도 됨
					if(truck[nth] - truck[pass] <= L) { // 이번 트럭 올라가면 다리위 트럭 총 무게
						bridge.offer(new tob(nth, sec)); // nth번 트럭 다리에 올라감!
						nth++;
					}
				}
			}
		}
		
		// 출력
		System.out.println(sec);
	}
}