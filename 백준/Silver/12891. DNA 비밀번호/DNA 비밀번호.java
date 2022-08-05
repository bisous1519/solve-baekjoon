import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	// 슬라이딩윈도우!

	private static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	private static StringTokenizer tokens;
	private static int S; // 문자열 길이
	private static int P; // 부분 문자열 길이
	private static char[] str;
	private static int A, C, G, T;
	private static int a, c, g, t;
	private static int cnt;
	
	private static void countChar(char strC, int pm) {
		switch(strC) {
		case 'A': a+=pm; break;
		case 'C': c+=pm; break;
		case 'G': g+=pm; break;
		case 'T': t+=pm; break;
		}
	}
	
	private static boolean checkStr() {
		return a >= A && c >= C && g >= G && t >= T;
	}
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		S = Integer.parseInt(tokens.nextToken());
		P = Integer.parseInt(tokens.nextToken());
		tokens = new StringTokenizer(input.readLine());
		str = (tokens.nextToken()).toCharArray();
		tokens = new StringTokenizer(input.readLine());
		A = Integer.parseInt(tokens.nextToken());
		C = Integer.parseInt(tokens.nextToken());
		G = Integer.parseInt(tokens.nextToken());
		T = Integer.parseInt(tokens.nextToken());
		for(int i=0; i<P; i++) {
			countChar(str[i], 1);
		}
		if(checkStr()) cnt++;
		for(int i=P; i<S; i++) {
			countChar(str[i], 1);
			countChar(str[i - P], -1);
			if(checkStr()) cnt++;
		}
		System.out.println(cnt);
	}

}