import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer tokens;
    
    public static void main(String[] args) throws IOException {
        tokens = new StringTokenizer(input.readLine());
        int a = (int) Math.pow(Integer.parseInt(tokens.nextToken()), 2);
        int b = (int) Math.pow(Integer.parseInt(tokens.nextToken()), 2);
        int c = (int) Math.pow(Integer.parseInt(tokens.nextToken()), 2);
        int d = (int) Math.pow(Integer.parseInt(tokens.nextToken()), 2);
        int e = (int) Math.pow(Integer.parseInt(tokens.nextToken()), 2);
        
        System.out.println((a + b + c + d + e) % 10);
    }
}