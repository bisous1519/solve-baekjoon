import java.util.*;

class Solution {
    
    static int M;       // key 길이
    static int N;       // lock 길이
    static int mapL;    // map 길이
    static int[][] map; // lock이랑 key 두는 map
    
    public boolean isIn(int r, int c) {
        return M-1<=r && r<M+N-1 && M-1<=c && c<M+N-1;
    }
    
    public int[][] rotateKey(int[][] key) {
        Queue<Integer> queue = new LinkedList<>();
        
        // System.out.println(M);
        // for(int r=0; r<M; r++){
        //     System.out.println(Arrays.toString(key[r]));
        // }
        
        for(int k=0; k<M/2; k++) { // (0,0) 부터 넣고 그다음은 (1,1), ..
            // 넣고
            for(int c=k; c<M-(k+1); c++) queue.offer(key[k][c]); // 윗줄
            for(int r=k; r<M-(k+1); r++) queue.offer(key[r][M-1-k]); // 오른쪽줄
            for(int c=(M-1)-k; c>k; c--) queue.offer(key[M-1-k][c]); // 아랫줄
            for(int r=(M-1)-k; r>k; r--) queue.offer(key[r][k]); // 왼쪽줄
            // 빼고
            for(int r=k; r<M-(k+1); r++) key[r][M-1-k] = queue.poll(); // 오른쪽줄
            for(int c=(M-1)-k; c>k; c--) key[M-1-k][c] = queue.poll(); // 아랫줄
            for(int r=(M-1)-k; r>k; r--) key[r][k] = queue.poll(); // 왼쪽줄
            for(int c=k; c<M-(k+1); c++) key[k][c] = queue.poll(); // 윗줄
        }
        
        // for(int r=0; r<M; r++){
        //     System.out.println(Arrays.toString(key[r]));
        // }
        return key;
    }
    
    public boolean solution(int[][] key, int[][] lock) {
        M = key.length;
        N = lock.length;
        
        // 열쇠와 자물쇠가 모두 들어가는 큰 map 만들기
        mapL = M*2 + N - 2;
        map = new int[mapL][mapL];
        int countOne = 0; // 자물쇠에 돌기(1)인 개수 세기
        for(int r=M-1; r<M+N-1; r++) {
            for(int c=M-1; c<M+N-1; c++) {
                // map 한가운데에 자물쇠 박기
                map[r][c] = lock[r-M+1][c-M+1];
                countOne += map[r][c];
            }
        }
        // printMap();
        
        // 열쇠를 4방향 각각으로 돌려서 해볼거야
        for(int turn=1; turn<=4; turn++) {
            
            // map에 열쇠 올려서 열리는지 보기
            for(int r=0; r<mapL-M+1; r++) { // map에 열쇠 올릴 자리 _ 맨 왼쪽 위 꼭지점
                keyOuter : for(int c=0; c<mapL-M+1; c++) {
                    
                    int becomeOne = 0;
                    for(int kR=0; kR<M; kR++) {
                        for(int kC=0; kC<M; kC++) {
                            // 자물쇠랑 겹치는 부분일 때
                            if(isIn(r+kR, c+kC)) {
                                // 둘이 안맞으면 바로 back
                                if(map[r+kR][c+kC] + key[kR][kC] != 1) continue keyOuter;
                                // 자물쇠에서 0이었는데 1이 된 부분 개수 세기
                                if(map[r+kR][c+kC] == 0) becomeOne++;
                            }
                        }
                    }
                    // 이번에 열쇠 둔 자리에서 자물쇠랑 겹치는 부분이 모두 맞았음
                    
                    // 자물쇠의 모든 홈이 채워졌다면
                    if(countOne + becomeOne == N*N) {
                        return true;
                    }
                }
            }
            
            // 열쇠 회전
            key = rotateKey(key);
        }
        
        return false;
    }
    
    public void printMap() {
        for(int r=0; r<mapL; r++){
            System.out.println(Arrays.toString(map[r]));
        }
    }
}