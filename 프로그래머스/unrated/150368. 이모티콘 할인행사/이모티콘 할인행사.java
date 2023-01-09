import java.util.*;

class Solution {
    
    static int maxSubmit = -1; // 임티플 최대 가입자 수
    static int maxIncome = -1; // 임티 최대 판매액
    static int[] off; // 각 임티 할인율
    
    public int[] solution(int[][] users, int[] emoticons) {
        int N = users.length;
        int M = emoticons.length;
        
        off = new int[M];
        Arrays.fill(off, 10); // 전부 10% 할인할 때로 할인율 초기화
        
        for(int i=0; i<(int)Math.pow(4, M); i++) {
            // off 배열 모양으로 임티 할인할 때 각 이모티콘 가격
            int[] emo = new int[M];
            for(int m=0; m<M; m++) {
                emo[m] = emoticons[m] * (100-off[m])/100;
            }
            
            // 현 할인율에서 유저들이 이모티콘 사고 임티플 가입
            int submit = 0;
            int income = 0;
            nextUser : for(int n=0; n<N; n++) { // n번 유저
                int cost = 0;
                for(int m=0; m<M; m++) { // m번째 임티
                    // 이 사용자가 원하는 할인율보다 높으면 삼
                    if(off[m] >= users[n][0]) {
                        cost += emo[m];
                    }
                    // 이 사용자의 마지노선 가격보다 넘어가면 임티플함
                    if(cost >= users[n][1]) {
                        submit++;
                        continue nextUser; // 다음유저~
                    }
                }
                
                // 이 사용자는 마지노선 가격보다 임티 적게 삼
                income += cost;
            }
            
            // 이번 경우에서 임티플 가입자 수랑 판매액 비교해서 갱신
            if(maxSubmit < submit) {
                maxSubmit = submit;
                maxIncome = income;
            }else if(maxSubmit == submit && maxIncome < income) {
                maxIncome = income;
            }
            
            // 할인율 바꾸기
            for(int m=0; m<M; m++) {
                off[m] += 10;
                if(off[m] < 50) break;
                off[m] = 10;
            }
        }
        
        int[] answer = {maxSubmit, maxIncome};
        return answer;
    }
}