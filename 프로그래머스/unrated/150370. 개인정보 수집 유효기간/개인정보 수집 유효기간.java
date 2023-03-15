import java.util.*;
import java.io.*;

class Solution {
    public int[] solution(String today, String[] terms, String[] privacies) {
        
        // today 날짜를 나눠
        String[] temp = today.split("[.]");
        int todayY = Integer.parseInt(temp[0]);
        int todayM = Integer.parseInt(temp[1]);
        int todayD = Integer.parseInt(temp[2]);
        
        // 약관 유효기간 배열을 보기편한 int[]로 만들기
        int[] newTerms = new int[26];
        for(int i=0; i<terms.length; i++) {
            String[] arr = terms[i].split(" ");
            char item = arr[0].charAt(0);
            int term = Integer.parseInt(arr[1]);
            newTerms[item - 'A'] = term;
        }
        
        // 개인정보 배열 for문 돌면서 하나씩 봄
        List<Integer> list = new ArrayList<>();
        for(int i=0; i<privacies.length; i++) {
            String[] arr = privacies[i].split("[ ]");
            String[] privacy = arr[0].split("[.]");
            
            int year = Integer.parseInt(privacy[0]);
            int month = Integer.parseInt(privacy[1]);
            int day = Integer.parseInt(privacy[2]);
            int item = arr[1].charAt(0) - 'A';
            
            month += newTerms[item];
            year += month / 12;
            month = month % 12;                
            if(month == 0) {
                month = 12;
                year--;
            }
            
            if(todayY < year) continue; // 아직 많이 남음
            if(todayY > year) {
                list.add(i+1); // 파기!
                continue;
            }
            
            if(todayM < month) continue; // 아직 좀 남음
            if(todayM > month) {
                list.add(i+1); // 파기!
                continue;
            }
            
            if(todayD < day) continue; // 아직 몇일 남음
            if(todayD >= day) {
                list.add(i+1); // 파기!
                continue;
            }
        }
        
        // 정답 배열에 담기
        int[] answer = new int[list.size()];
        int idx = 0;
        for(int a : list) {
            answer[idx++] = a;
        }
        
        return answer;
    }
}