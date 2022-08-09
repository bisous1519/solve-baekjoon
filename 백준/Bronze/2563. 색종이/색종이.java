import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Main {

	private static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	private static StringTokenizer tokens;
	
	private static int N;
	private static int Size = 10;
	private static int MapSize = 100;
	private static int Area;
	private static boolean[][] Map = new boolean[MapSize+1][MapSize+1];
	
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		for(int n=0, x, y; n<N; n++) {
			tokens = new StringTokenizer(input.readLine());
			x = Integer.parseInt(tokens.nextToken());
			y = Integer.parseInt(tokens.nextToken());
			for(int i=y+1; i<=y+Size; i++) {
				for(int j=x+1; j<=x+Size; j++) {
					Map[i][j] = true;
				}
			}
		}
		for(int i=1; i<=MapSize; i++) {
			for(int j=1; j<=MapSize; j++) {
				if(Map[i][j]) {
					Area++;
				}
			}
		}
		System.out.println(Area);
	}
}