import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;

	static Queue<String> queue = new LinkedList<>();
	static int T; // 톱니바퀴 개수
	static int size = 8; // 톱니개
	static char[][] status;
	static int K; // 회전횟수
	static int[][] rotate;
	static int rIdx = 2; // 내 오른쪽 톱니바퀴랑 맞물릴 오른쪽 톱니 인덱스
	static int lIdx = 6; // 내 왼쪽 톱니바퀴랑 맞물릴 왼쪽 톱니 인덱스
	
	public static void roll(int curT, int direc) {
		if(direc == 1) {
			// 시계 방향
			for(int i=size-1; i>=0; i--) {
				queue.offer(status[curT][i]+"");
			}
			queue.offer(queue.poll());
			for(int i=size-1; i>=0; i--) {
				status[curT][i] = queue.poll().charAt(0);
			}
		}else {
			// 반시계 방향
			for(int i=0; i<size; i++) {
				queue.offer(status[curT][i]+"");
			}
			queue.offer(queue.poll());
			for(int i=0; i<size; i++) {
				status[curT][i] = queue.poll().charAt(0);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		T = Integer.parseInt(tokens.nextToken());
		status = new char[T][size];
		for(int t=0; t<T; t++) {
			tokens = new StringTokenizer(input.readLine());
			status[t] = tokens.nextToken().toCharArray();
		}
		tokens = new StringTokenizer(input.readLine());
		K = Integer.parseInt(tokens.nextToken());
		rotate = new int[K][2];
		for(int k=0; k<K; k++) {
			tokens = new StringTokenizer(input.readLine());
			rotate[k][0] = Integer.parseInt(tokens.nextToken());
			rotate[k][1] = Integer.parseInt(tokens.nextToken());
		}
		// K번 회전 시작
		for(int k=0; k<K; k++) {
			int curT = rotate[k][0] - 1;		// 현재 기준이 되는 톱니바퀴
			int preLeft = status[curT][lIdx];	// 현재 톱니바퀴의 왼쪽톱니가 S(1)인지 N(0)인지
			int preRight = status[curT][rIdx];	// 현재 톱니바퀴의 오른쪽톱니가 S(1)인지 N(0)인지
 			// 현재 톱니바퀴의 왼쪽들 확인
			int direc = rotate[k][1]; // 현재 기준이 되는 톱니바퀴가 도는 방향
			for(int t=curT-1; t>=0; t--) {
				if(status[t][rIdx] == preLeft) { // 맞물리는 톱니가 같으면 내가 안움직여도됨! 더이상 이쪽 볼 필요x
					break;
				}
				preLeft = status[t][lIdx];
				direc *= -1;
				roll(t, direc);
			}
			// 현재 톱니바퀴의 오른쪽들 확인
			direc = rotate[k][1];
			for(int t=curT+1; t<T; t++) {
				if(preRight == status[t][lIdx]) { // 맞물리는 톱니가 같으면 내가 안움직여도됨! 더이상 이쪽 볼 필요x
					break;
				}
				preRight = status[t][rIdx];
				direc *= -1;
				roll(t, direc);
			}
			roll(curT, rotate[k][1]); // 기준이 됐던 나! 돌려줌
		}
		// 12시 방향이 S극인 톱니바퀴 개수 세기
		int cnt = 0;
		for(int t=0; t<T; t++) {
			if(status[t][0] == '1') {
				cnt++;
			}
		}
		System.out.println(cnt);
	}
}