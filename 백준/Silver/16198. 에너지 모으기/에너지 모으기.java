import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	static int N;
	static int Max = Integer.MIN_VALUE;
	
	public static void energy(List<Integer> wList, int totalE) {
		if(wList.size() < 3) {
			Max = Math.max(Max, totalE);
			return;
		}
		for(int i=1; i<wList.size() - 1; i++) {
//			List<Integer> tempList = new ArrayList<>(wList);
			List<Integer> tempList = new ArrayList<>();
			tempList.addAll(wList);
			tempList.remove(i);
			energy(tempList, totalE + (wList.get(i-1) * wList.get(i+1)));
		}
	}
	public static void main(String[] args) throws Exception {
		List<Integer> wList = new ArrayList<>();
		N = Integer.parseInt(input.readLine());
		tokens = new StringTokenizer(input.readLine());
		for(int n=0; n<N; n++) {
			wList.add(Integer.parseInt(tokens.nextToken()));
		}//입력
		
		energy(wList, 0);
		
		System.out.println(Max);
	}
}