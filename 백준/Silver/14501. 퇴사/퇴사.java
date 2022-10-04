import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	static int N;
	static Data[] Pay;
	
	static class Data {
		int sDate;
		int paySum;
		int nextDate;
		public Data(int sDate, int paySum, int nextDate) {
			this.sDate = sDate;
			this.paySum = paySum;
			this.nextDate = nextDate;
		}
	}
	
	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(input.readLine());
		Pay = new Data[N+1];
		
		for(int n=1; n<=N; n++) {
			tokens = new StringTokenizer(input.readLine());
			int startDate = n;
			int endDate = Integer.parseInt(tokens.nextToken()) + n - 1;
			int curPay = Integer.parseInt(tokens.nextToken());
			
			if(endDate > N) {
				continue;
			}
			
			int MaxIdx = -1;
			int MaxPaySum = Integer.MIN_VALUE;
			for(int i=startDate - 1; i>=1; i--) {
				// 현재 상담 이전에 할 수 있는 상담이 있음! 이익 누적 가능!
				if(Pay[i] != null) { 
					if(MaxPaySum < Pay[i].paySum) {
						MaxPaySum = Pay[i].paySum;
						MaxIdx = i;
					}
				}
			}
			
			if(MaxIdx != -1) {
				curPay += MaxPaySum;
				Pay[MaxIdx].nextDate = endDate;
			}
			
			// 이 날짜에 끝나는 상담은 이번이 처음
			if(Pay[endDate] == null) {
				Pay[endDate] = new Data(startDate, curPay, -1);
				
			// 이미 오늘 날짜에 끝나는 상담이 있었음!
			}else if(Pay[endDate].paySum < curPay) {
				 // 어쨌든 이익이 더 크거나 || 더 늦게 시작해서 같은 이익을 낼 수 있는
				Pay[endDate] = new Data(startDate, curPay, Pay[endDate].nextDate);
				
				if(Pay[endDate].nextDate != -1) {
					// 타고가면서 갱신
					int plus = curPay - Pay[endDate].paySum;
					int i = Pay[endDate].nextDate;
					while(i != -1) {
						Pay[i].paySum += plus;
						i = Pay[i].nextDate;
					}
				}
			}else if(Pay[endDate].paySum == curPay) {
				Pay[endDate] = new Data(startDate, curPay, Pay[endDate].nextDate);
			}
		} // 누적 이익 처리 완료
		
		int Max = Integer.MIN_VALUE;
		for(int n=1; n<=N; n++) {
			if(Pay[n] == null) {
				continue;
			}
			Max = Math.max(Max, Pay[n].paySum);
		}
		
		if(Max == Integer.MIN_VALUE) {
			System.out.println(0);
		}else {
			System.out.println(Max);
		}
	}
}