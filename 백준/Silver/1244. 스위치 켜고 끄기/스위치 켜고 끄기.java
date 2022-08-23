import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	static int swN; // 스위치 개수
	static int stN; // 학생 수
	static boolean[] status;
	
	public static void main(String[] args) throws IOException {
		swN = Integer.parseInt(input.readLine());
		
		status = new boolean[swN+1];
		tokens = new StringTokenizer(input.readLine());
		for(int n=1; n<=swN; n++) {
			if(tokens.nextToken().charAt(0) == '0') {
				status[n] = false;
			}else {
				status[n] = true;
			}
		}
		
		stN = Integer.parseInt(input.readLine());
		for(int n=0; n<stN; n++) {
			tokens = new StringTokenizer(input.readLine());
			int gen = Integer.parseInt(tokens.nextToken());
			int num = Integer.parseInt(tokens.nextToken());
			
			// 남학생
			if(gen == 1) {
				for(int i=num; i<=swN; i+=num) {
					status[i] = !status[i];
				}
				
			// 여학생
			}else {
				status[num] = !status[num];
				for(int i=1; num-i>=1 && num+i<=swN; i++) {
					if(status[num-i] == status[num+i]) {
						status[num-i] = !status[num-i];
						status[num+i] = !status[num+i];
					}
					else break;
				}
			}
		}
		
		for(int n=1; n<=swN; n++) {
			if(status[n]) {
				System.out.print(1 + " ");
			}else {
				System.out.print(0 + " ");
			}
			if(n % 20 == 0) {
				System.out.println();
			}
		}
		System.out.println();
	}
}