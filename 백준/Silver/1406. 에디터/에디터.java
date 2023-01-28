import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder output = new StringBuilder();
	static StringTokenizer tokens;
	
	static String str;
	static int length;
	static int M;
	static int cursor;
	static List<Character> list = new LinkedList<>();
	static ListIterator<Character> itr;
	
	public static void main(String[] args) throws IOException {
		str = input.readLine();
		for(int c=0; c<str.length(); c++) {
			list.add(str.charAt(c));
		}
		
		itr = list.listIterator(list.size());
		
		M = Integer.parseInt(input.readLine());
		for(int m=0; m<M; m++) {
			tokens = new StringTokenizer(input.readLine());
			char letter = tokens.nextToken().charAt(0);
			switch(letter) {
			case 'L' :
				if(itr.hasPrevious()) itr.previous();
				break;
			case 'D' :
				if(itr.hasNext()) itr.next();
				break;
			case 'B' :
				if(itr.hasPrevious()) {
					itr.previous();
					itr.remove();
				}
				break;
			case 'P' :
				itr.add(tokens.nextToken().charAt(0));
				break;
			}
		}
		
		for(char a : list) {
			output.append(a);
		}
		System.out.println(output);
	}
}