import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();

	static int TC;
	static int N;
	static Loc[] conArr;
	static Loc Start;
	static Loc End;
	
	static class Loc {
		int r;
		int c;
		public Loc(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}
	
	public static int dis(Loc l1, Loc l2) {
		return Math.abs(l1.r - l2.r) + Math.abs(l1.c - l2.c);  
	}
	
	public static String bfs() {
		Queue<Loc> queue = new LinkedList<>();
		boolean[] visited = new boolean[N];
		queue.offer(Start);
		
		while(!queue.isEmpty()) {
			int size = queue.size();
			while(size-- > 0) {
				Loc cur = queue.poll();
				
				// 지금 있는 곳에서 락페 갈 수 있음! happy해!!
				if(dis(cur, End) <= 1000) {
					return "happy";
				}
				
				for(int n=0; n<N; n++) {
					if(!visited[n] && dis(cur, conArr[n]) <= 1000) {
						visited[n] = true;
						queue.offer(conArr[n]);
					}
				}
			}
		}
		
		return "sad";
	}
	
	public static Loc inputLoc() throws IOException {
		tokens = new StringTokenizer(input.readLine());
		int r = Integer.parseInt(tokens.nextToken());
		int c = Integer.parseInt(tokens.nextToken());
		Loc loc = new Loc(r, c);
		return loc;
	}
	
	public static void main(String[] args) throws IOException {
		TC = Integer.parseInt(input.readLine());
		for(int tc=1; tc<=TC; tc++) {
			N = Integer.parseInt(input.readLine());
			Start = inputLoc();
			conArr = new Loc[N];
			for(int n=0; n<N; n++) {
				conArr[n] = inputLoc();
			}
			End = inputLoc();
			
			output.append(bfs()).append("\n");
		}
		
		System.out.println(output);
	}
}