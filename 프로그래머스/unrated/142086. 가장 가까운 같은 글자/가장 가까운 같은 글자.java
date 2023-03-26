import java.util.*;
import java.io.*;
class Solution {
    public int[] solution(String s) {
        int[] answer = new int[s.length()];
        int[] alpha = new int[26];
        Arrays.fill(alpha, -1);
        for(int i=0; i<s.length(); i++) {
            int at = s.charAt(i) - 'a';
            if(alpha[at] == -1) {
                answer[i] = -1;
            }else {
                answer[i] = i - alpha[at];
            }
            alpha[at] = i;
        }
        return answer;
    }
}