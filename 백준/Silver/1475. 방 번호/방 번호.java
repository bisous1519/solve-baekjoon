import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Main {

	private static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	private static StringTokenizer tokens;
	
	private static String N;
	private static int[] number = new int[10];
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = tokens.nextToken();
		for(int i=0; i<N.length(); i++) {
			int current = Integer.parseInt(N.charAt(i) + "");
			if(current == 6 || current == 9) {
				int temp = (number[6] < number[9]) ? number[6]++ : number[9]++;
			}else {
				number[current]++;
			}
		}
		int max = 0;
		for(int i=0; i<number.length; i++) {
			max = Math.max(max, number[i]);
		}
		System.out.println(max);
	}

}