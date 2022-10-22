import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String str = sc.next();
		int[] letter = new int[26];
		for(int i=0; i<str.length(); i++) {
			if('A' <= str.charAt(i) && str.charAt(i) <= 'Z') {
				letter[str.charAt(i) - 'A'] ++;
			}else if('a' <= str.charAt(i) && str.charAt(i) <= 'z'){
				letter[str.charAt(i) - 'a'] ++;
			}
		}
		
		int max = 0;
		boolean tag = false;
		char c = '-';
		for(int i=0; i<letter.length; i++) {
			if(letter[i] != 0 && max == letter[i]) {
				tag = true;
			}
			if(max < letter[i]) {
				max = letter[i];
				c = (char)('A' + i);
				tag = false;
			}
		}
		System.out.println(tag ? "?" : c);
	}
}