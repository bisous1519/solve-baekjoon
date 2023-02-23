import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
	
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>();
		
		for(int i=1; i<=10; i++) {
			int num = sc.nextInt();
			list.add(num % 42);
		}
		
		Collections.sort(list);
		
		int cnt = 1;
		for(int i=1; i<10; i++) {
			if(list.get(i-1) != list.get(i)) {
				cnt++;
			}
		}
		
		System.out.println(cnt);
	}
}