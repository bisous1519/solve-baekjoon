import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	
	static String str;
	static int cnt; // 열린괄호 개수
	static int ironBar; // 쇠막대기 개수
	
	public static void main(String[] args) throws IOException {
		str = input.readLine();
		for(int i=0; i<str.length(); i++) {
			if(str.charAt(i) == '(') {
				cnt++;
			}else {
				cnt--;
				if(str.charAt(i-1) == '(') { // 레이저임!
					ironBar += cnt;
				}else { // 막대기 하나가 끝난거
					ironBar += 1;
				}
			}
		}
		System.out.println(ironBar);
	}
}