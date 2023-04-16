import java.io.*;
import java.util.*;

class Solution {
    public String[] solution(String[] players, String[] callings) {
        // players들을 hashmap에 담기
        HashMap<String, Integer> hashMap = new HashMap<>();
        for(int idx=0; idx<players.length; idx++) {
            hashMap.put(players[idx], idx);
        }
        
        // 추월하기
        for(int idx=0; idx<callings.length; idx++) {
            String curCalling = callings[idx];
            int nth = hashMap.get(curCalling);
            // System.out.println(nth);
            hashMap.put(curCalling, nth - 1);
            String prePlayer = players[nth - 1];
            players[nth - 1] = curCalling;
            players[nth] = prePlayer;
            hashMap.put(prePlayer, nth);
        }
        
        return players;
    }
}