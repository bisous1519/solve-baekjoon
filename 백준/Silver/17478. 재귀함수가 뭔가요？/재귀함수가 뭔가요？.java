import java.util.Scanner;

public class Main {
	
	static Scanner sc = new Scanner(System.in);
	
	static int N;
	
	public static void jeagui(int n) {
		if(n == N) {
			for(int i=0; i<n*4; i++) {
				System.out.print("_");
			}
			System.out.print(str1);
			for(int i=0; i<n*4; i++) {
				System.out.print("_");
			}
			System.out.print(a1);
			for(int i=0; i<n*4; i++) {
				System.out.print("_");
			}
			System.out.print(a2);
			
			return;
		}
		
		for(int i=0; i<n*4; i++) {
			System.out.print("_");
		}
		System.out.print(str1);
		for(int i=0; i<n*4; i++) {
			System.out.print("_");
		}
		System.out.print(str2);
		for(int i=0; i<n*4; i++) {
			System.out.print("_");
		}
		System.out.print(str3);
		for(int i=0; i<n*4; i++) {
			System.out.print("_");
		}
		System.out.print(str4);
		
		jeagui(n + 1);

		for(int i=0; i<n*4; i++) {
			System.out.print("_");
		}
		System.out.print(a2);
	}
	
	public static void main(String[] args) {
		N = sc.nextInt();
		System.out.print(q);
		jeagui(0);
	}
	
	static String q = "어느 한 컴퓨터공학과 학생이 유명한 교수님을 찾아가 물었다.\n";
	
	static String str1 = "\"재귀함수가 뭔가요?\"\n";
	
	static String str2 = "\"잘 들어보게. 옛날옛날 한 산 꼭대기에 이세상 모든 지식을 통달한 선인이 있었어.\n";	
	static String str3 = "마을 사람들은 모두 그 선인에게 수많은 질문을 했고, 모두 지혜롭게 대답해 주었지.\n";
	static String str4 = "그의 답은 대부분 옳았다고 하네. 그런데 어느 날, 그 선인에게 한 선비가 찾아와서 물었어.\"\n";
	
	static String a1 = "\"재귀함수는 자기 자신을 호출하는 함수라네\"\n";
	
	static String a2 ="라고 답변하였지.\n";
}