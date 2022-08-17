import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	
	static String[] arr;
	static int result;
	
	public static void main(String[] args) throws IOException {
		arr = input.readLine().split("-");
		for(int i=0; i<arr.length; i++) {
			String cur = arr[i];
			int sum = 0;
			int makeNum = 0;
			int idx = 0;
			while(idx < cur.length()) {
				if(cur.charAt(idx) >= '0' && cur.charAt(idx) <= '9') {
					makeNum *= 10;
					makeNum += Integer.parseInt(cur.charAt(idx)+"");
				}else {
					// '+'일 경우
					sum += makeNum;
					makeNum = 0;
				}
				idx++;
			}
			sum += makeNum;
			if(i > 0) {
				sum *= -1;
			}
			result += sum;
		}
		System.out.println(result);
	}
}