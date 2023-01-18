import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * -문제
 * 1층부터 N층까지.
 * K자리의 수. (1 <= K <= 6)
 * 최소 1개 최대 P개 반전시킴. (1<=P<=42)
 * 반전 이후에 올바른 수가 되어야 하고, 숫자는 1이상 N이하.
 * 현재 엘레베이터는 실제로 X층.
 * 반전으로 만들 수 있는 수의 개수.
 * -풀이
 * 각 숫자마다 몇 번의 반전으로 다른 숫자를 만들 수 있는지 테이블을 만들어놓고. 숫자는 1이상 N이하.
 * 반전개수로 백트래킹. *N이하*
 */

public class Main {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder output = new StringBuilder();
	static StringTokenizer tokens;

	// 각 숫자에 어떤 불이 켜져있는지. 위 왼쪽 순서.
	static int[][] numbers = { { 1, 1, 1, 0, 1, 1, 1 }, { 0, 0, 1, 0, 0, 1, 0 }, { 1, 0, 1, 1, 1, 0, 1 },
			{ 1, 0, 1, 1, 0, 1, 1 }, { 0, 1, 1, 1, 0, 1, 0 }, { 1, 1, 0, 1, 0, 1, 1 }, { 1, 1, 0, 1, 1, 1, 1 },
			{ 1, 0, 1, 0, 0, 1, 0 }, { 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 0, 1, 1 } };

	static int _n, _k, _p, _x;
	static int[][] _flipCnt;
	static int _answer;

	// x를 y로 바꾸려면 몇 개를 flip해야 하는지.
	static int findFlipCnt(int x, int y) {
		int result = 0;

		for (int i = 0; i < 7; i++) {
			if (numbers[x][i] != numbers[y][i]) {
				result += 1;
			}
		}

		return result;
	}

	// int[] result를 int로 바꿔줌.
	static int resultToNum(int[] result) {
		int n = 0;
		int digit = 1;

		for (int i = result.length - 1; i > -1; i--) {
			n += result[i] * digit;
			digit *= 10;
		}

		return n;
	}

	// digit: 앞에서부터 몇 번째 숫자인지, fCnt은 flip한 개수, result는 뽑은 결과값.
	static void recur(int digit, int fCnt, int[] result) {

		// 백트래킹 - 반전시킨 개수가 P보다 크면 return.
		if (fCnt > _p) {
			return;
		}

		// 종료 조건. _k개를 무사히 뽑았으면 _answer+=1.
		if (digit == _k) {
			// 1개라도 바꿨고, 1 <= 층 <= N.
			if (fCnt > 0 && resultToNum(result) >= 1 && resultToNum(result) <= _n) {
				_answer += 1;
			}
			return;
		}

		for (int i = 0; i < 10; i++) {
			int temp = result[digit]; // 돌려놓기 위해 temp에 잠시 저장.
			result[digit] = i; // 숫자를 바꿔주고,
			recur(digit + 1, fCnt + _flipCnt[temp][i], result);
			result[digit] = temp; // 돌려놓음.
		}

	}

	public static void main(String[] args) throws Exception {
		tokens = new StringTokenizer(input.readLine());
		_n = Integer.parseInt(tokens.nextToken());
		_k = Integer.parseInt(tokens.nextToken());
		_p = Integer.parseInt(tokens.nextToken());
		_x = Integer.parseInt(tokens.nextToken());

		// _flipCnt에 숫자 i를 숫자 j로 바꾸기 위해 몇 개를 flip해야하는지 저장.
		_flipCnt = new int[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				_flipCnt[i][j] = findFlipCnt(i, j);
			}
		}

		// 입력 확인.
//		for (int[] row : _flipCnt) {
//			System.out.println(Arrays.toString(row));
//		}

		_answer = 0;

		// result는 청므에 현재 층(X)로 초기화.
		int[] result = new int[_k]; // k자리수.
		int idx = _k - 1;
		while (_x > 0) {
			result[idx--] = _x % 10;
			_x /= 10;
		}

		// 입력 확인.
//		System.out.println(Arrays.toString(result));

		recur(0, 0, result);

		System.out.println(_answer);

	}

}