import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	
	private static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	private static StringTokenizer tokens;
	private static StringBuilder output = new StringBuilder();
	
	private static int N;
	private static List<Person> pList = new ArrayList<>();
	
	public static class Person implements Comparable<Person> {
		private int num;
		private int weight;
		private int height;
		private int rank;
		public Person(int num, int weight, int height) {
			this.num = num;
			this.weight = weight;
			this.height = height;
			this.rank = 0;
		}
		public int getNum() {
			return num;
		}
		public int getHeight() {
			return height;
		}
		public int getRank() {
			return rank;
		}
		public void setRank(int rank) {
			this.rank = rank;
		}
		@Override
		public int compareTo(Person o) {
			if(this.weight == o.weight) {
				return Integer.compare(this.height, o.height);
			}
			return Integer.compare(this.weight, o.weight) * -1;
		}
	}
	
	public static class numComparator implements Comparator<Person> {
		@Override
		public int compare(Person o1, Person o2) {
			return Integer.compare(o1.getNum(), o2.getNum());
		}
	}

	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		for(int i=0; i<N; i++) {
			tokens = new StringTokenizer(input.readLine());
			int weight = Integer.parseInt(tokens.nextToken());
			int height = Integer.parseInt(tokens.nextToken());
			
			// 입력 받아서 List<Person> 형태로 저장
			pList.add(new Person(i, weight, height));
		}
		
		// 몸무게 기준으로 내림차순 정렬
		// 몸무게가 같으면 키 기준 오름차순
		Collections.sort(pList);
		
		for(int i=0; i<N; i++) {
			int rank = 1;
			int currentH = pList.get(i).getHeight();
			for(int j=i-1; j>=0 ; j--) {
				// 순위 계산하려는 지금 애보다 위로 탐색
				// 키가 나보다 크면 --> 몸무게도 많이나가고 키도 큰 것 
				if(pList.get(j).getHeight() > currentH) {
					rank++;
				}
			}
			pList.get(i).setRank(rank);
		}
		
		// 입력받았던 순서대로 다시 돌려놓기
		Collections.sort(pList, new numComparator());
		
		for(Person p : pList) {
			output.append(p.getRank()).append(" ");
		}
		System.out.println(output);
	}
}