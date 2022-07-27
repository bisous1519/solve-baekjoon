class Solution {
    public int solution(int n, int[] lost, int[] reserve) {
        int answer = 0;
        int[] check = new int[n+2];
        for(int r=0; r<lost.length; r++) {
            check[lost[r]]--;
        }
        for(int r=0; r<reserve.length; r++) {
            check[reserve[r]]++;
        }
        for(int r=1; r<=n; r++) {
            if(check[r] == 1) {
                if(check[r - 1] == -1) {
                    check[r - 1]++;
                    check[r]--;
                }else if(check[r + 1] == -1) {
                    check[r + 1]++;
                    check[r]--;
                }
            }
        }
        for(int r=1; r<=n; r++) {
            if(check[r] != -1) {
                answer++;
            }
        }
        return answer;
    }
}