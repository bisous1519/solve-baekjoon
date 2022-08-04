import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	private static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	private static StringTokenizer tokens;
	
	private static int N;
	private static Queue<Integer> queue = new LinkedList<>();
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		for(int i=1; i<=N; i++) {
			queue.offer(i);
		}

		//System.out.println(queue.poll());
		//System.out.println(queue);
		
		while(queue.size() > 1) {
			queue.remove();
			queue.offer(queue.poll());
		}
		
		System.out.println(queue.poll());
		
	}

}