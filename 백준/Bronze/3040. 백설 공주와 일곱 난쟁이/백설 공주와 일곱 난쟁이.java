import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.io.IOException;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	
	static int N = 9;
	static int S = 7;
	static int[] Dwarf = new int[N];
	
	public static boolean combi(int nth, int[] choosed, int start) {
		if(nth == S) {
			int sum = 0;
			for(int i=0; i<S; i++) {
				sum += Dwarf[choosed[i]];
			}
			if(sum == 100) { // 찾았다!
				for(int i=0; i<S; i++) {
					System.out.println(Dwarf[choosed[i]]);
				}
				return true;
			}
			return false;
		}
		for(int i=start; i<N; i++) {
			choosed[nth] = i;
			if(combi(nth + 1, choosed, i + 1)) return true;
		}
		return false;
	}
	
	public static void main(String[] args) throws IOException {
		for(int n=0; n<N; n++) {
			Dwarf[n] = Integer.parseInt(input.readLine());
		}
		combi(0, new int[S], 0);
	}
}