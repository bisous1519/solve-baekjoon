// n개의 송전탑이 하나로 연결됨
// 전선을 하나 끊어서 2개로 분할
//   - 두 전력망의 송전탑 개수가 최대한 비슷하게
// => 출력
//    두 전력망 송전탑 개수가 최대한 비슷할 때 송전탑 개수의 차이
// => 풀이
//    n-1개의 wires 중 하나를 끊음
//    끊은 상태에서 두개 그룹의 노드 개수 세서(bfs? union-find)
//    차이의 최솟값 갱신

let N;
let Wires;
let list;
let min = Number.MAX_SAFE_INTEGER;

const bfs = (num, isVisited) => {
    isVisited[num] = true;
    let queue = [];
    queue.push(num);
    
    let cnt = 1;
    while(queue.length > 0) {
        let size = queue.length;
        while(size -- > 0) {
            const cur = queue.shift();
            
            list[cur].length > 0 && list[cur].forEach(v2 => {
                if(!isVisited[v2]) {
                    isVisited[v2] = true;
                    queue.push(v2);
                    cnt ++;
                }
            })
        }
    }
    
    return cnt;
}

function solution(n, wires) {
    N = n;
    Wires = wires;
    
    for(let i=0; i<N-1; i++) {
        list = new Array(N+1).fill(null).map(() => []);
        
        // 연결리스트 만들기
        Wires.forEach(([v1, v2], idx) => {
            if(idx === i) {
                // i번째는 연결 끊고
            } else {
                list[v1].push(v2);
                list[v2].push(v1);
            }
        })
        
        // 두개그룹 송전탑 개수 세기
        let isVisited = new Array(N + 1).fill(false);
        let cnt = [];
        for(let i=1; i<=N; i++) {
            if(!isVisited[i]) {
                cnt.push(bfs(i, isVisited));
            }
        }
        
        min = Math.min(min, Math.abs(cnt[0] - cnt[1]));
    }
    
    return min;
}