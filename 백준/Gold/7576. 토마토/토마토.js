// 토마토
//   - 보관 후 하루가 지나면 -> 익은 토마토와 인접한 안익은 토마토가 익음
//   - 인접 : 상우하좌 4방
//   - 1 : 익은 토마토
//   - 0 : 안익은 토마토
//   - -1 : 빈칸
// 주의
//   - 입력 : M, N => 가로, 세로
//   - 처음부터 모든 토마토가 익어있는 상태 : 0 출력
//   - 모두 익지 못하는 상황이면 : -1 출력
// => 출력
//    며칠이 지나면 다 익는지 최소일수
// => 풀이
//    - bfs

let R, C;
let Map;
let done = [];
let yet = 0;
let isVisited;
const dr = [-1, 0, 1, 0]; // 상우하좌
const dc = [0, 1, 0, -1];

const Loc = function(r, c) {
    this.r = r;
    this.c = c;
}

const isIn = (r, c) => {
    return 0<=r && r<R && 0<=c && c<C;
}

const bfs = () => {
    let queue = done;

    let qIdx = 0;
    let day = 0;
    while(qIdx < queue.length) {
        let size = queue.length - qIdx;
        day++;
        while(size-- > 0) {
            const cur = queue[qIdx++];

            for(let d=0; d<4; d++) {
                const goR = cur.r + dr[d];
                const goC = cur.c + dc[d];

                if(!isIn(goR, goC)) continue;
                if(isVisited[goR][goC]) continue;
                if(Map[goR][goC] !== 0) continue;

                Map[goR][goC] = 1;
                queue.push(new Loc(goR, goC));
                isVisited[goR][goC] = true;
                yet--;

                if(yet === 0) {
                    console.log(day);
                    return;
                }
            }
        }
    }

    console.log(-1);
}

const main = () => {
    const input = require('fs').readFileSync('/dev/stdin').toString().trim().split('\n');

    [C, R] = input.shift().split(' ').map(Number);
    Map = input.map(row => row.split(' ').map(Number));

    // 익은토마토 위치, 안익은 토마토 개수파악
    isVisited = new Array(R).fill(null).map(() => new Array(C).fill(false));
    Map.forEach((row, r) => row.forEach((el, c) => {
        if(el === 1) {
            done.push(new Loc(r, c));
            isVisited[r][c] = true;
        }
        else if(el === 0) yet++;
    }))

    // 이미 모든토마토 익음
    if(yet === 0) console.log(0);
        
    else bfs();
}

main();