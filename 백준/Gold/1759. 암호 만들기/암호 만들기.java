import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();
	
	static int L;
	static int C;
	static List<Character> moList = new ArrayList<>(); // 입력받은 모음
	static List<Character> jaList = new ArrayList<>(); // 입력받은 자음
	static List<String> moCombi; // 모음 조합
	static List<String> jaCombi; // 자음 조합
	static List<String> ans = new ArrayList<>(); // 모든 경우의 수
	static char[] mo = {'a', 'e', 'i', 'o', 'u'};
	
	public static void combi(int nth, String[] choosed, int start, boolean isMo) {
		if(nth == choosed.length) {
			// 모음 조합 만들어짐!
			if(isMo) moCombi.add(String.join("", choosed));
			else 	 jaCombi.add(String.join("", choosed));
			return;
		}
		
		for(int i=start; i<(isMo ? moList.size() : jaList.size()); i++) {
			if(isMo) choosed[nth] = moList.get(i) + "";
			else 	 choosed[nth] = jaList.get(i) + "";
			combi(nth + 1, choosed, i + 1, isMo);
		}
	}
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		L = Integer.parseInt(tokens.nextToken());
		C = Integer.parseInt(tokens.nextToken());
		tokens = new StringTokenizer(input.readLine());
		for(int c=0; c<C; c++) {
			char al = tokens.nextToken().charAt(0);
			boolean isMo = false;
			for(int m=0; m<mo.length; m++) {
				if(al == mo[m]) {
					moList.add(al); // 모음이면 모음리스트에 추가
					isMo = true;
					break;
				}
			}
			if(!isMo) { // 자음이면 자음리스트에 추가
				jaList.add(al);
			}
		}
		
		// 자음과 모음 리스트 각각 오름차순 정렬
		Collections.sort(moList);
		Collections.sort(jaList);
		
		// 모음을 1개~(자음최소2개뽑는경우)개 각각 경우의 조합 생성
		int moR = Math.min(moList.size(), L-2); // min(모음개수, 자음최소2개뽑은나머지)
		for(int r=1; r<=moR; r++) {
			moCombi = new ArrayList<>();
			jaCombi = new ArrayList<>();
			combi(0, new String[r], 0, true); // 모음조합생성
			combi(0, new String[L-r], 0, false); // 자음조합생성
			
			// 생성한 모음, 자음 조합 합치기
			for(int m=0; m<moCombi.size(); m++) {
				for(int j=0; j<jaCombi.size(); j++) {
					// 각 조합 합쳐서 정렬하고 문자열로 저장
					String[] temp = (moCombi.get(m) + jaCombi.get(j)).split("");
					Arrays.sort(temp);
					ans.add(String.join("", temp));
				} 
			}
		}
		
		// 답 리스트 정렬해서 출력
		Collections.sort(ans);
		for(String a : ans) {
			System.out.println(a);
		}
	}
}