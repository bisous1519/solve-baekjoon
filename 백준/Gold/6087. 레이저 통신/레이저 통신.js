// 지도 W*H -> C*R
    // . : 빈칸
    // * : 벽
    // 거울을 최소로 사용해서 두 C를 연결
// 거울
    // /, \ 두종류
// => 풀이
    // 결국, 가장 적은 depth 만에 도착했을 때 꺾인 갯수세는?
    // bfs
        // queue에 저장할 때, 방향을 같이 저장, 지금까지 꺾인갯수도 저장
        // d랑 cur.d 랑 다르면 cur.cnt+1해서 저장

let R, C;
let Map;
let Start;
let End;
let min = Number.MAX_SAFE_INTEGER;
const dr = [-1, 0, 1, 0]; // 상우하좌
const dc = [0, 1, 0, -1];

/*
4 4
C.**
..**
....
...C
*/
/*
100 7
...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*
C*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*.*
...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*
**************************************************************************************************.*
...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*
C*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*.*
...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*...*
*/

const Loc = function(r, c, d, cnt) {
    this.r = r;
    this.c = c;
    this.d = d;
    this.cnt = cnt;
}

const isIn = (r, c) => {
    return 0<=r && r<R && 0<=c && c<C;
}

const bfs = () => {
    let queue = [Start];
    let isVisited = new Array(R).fill(null).map(() => new Array(C).fill(null).map(() => new Array(4).fill(Number.MAX_SAFE_INTEGER)));
    for(let i=0; i<4; i++) {
        isVisited[Start.r][Start.c][i] = 0;
    }

    let qIdx = 0;
    while(qIdx < queue.length) {
        let size = queue.length - qIdx;
        while(size -- > 0) {
            const cur = queue[qIdx ++];

            for(let d=0; d<4; d++) {
                const goR = cur.r + dr[d];
                const goC = cur.c + dc[d];

                if(!isIn(goR, goC)) continue;
                if(Map[goR][goC] === '*') continue;
                if(goR === Start.r && goC === Start.c) continue;

                const cnt = cur.d === d ? cur.cnt : cur.d === -1 ? 0 : cur.cnt + 1;
                if(isVisited[goR][goC][d] <= cnt) continue;
                
                isVisited[goR][goC][d] = cnt;
                // console.log(goR, goC, d, cnt)
                
                if(goR === End.r && goC === End.c) continue;
                queue.push(new Loc(goR, goC, d, cnt));
            }
        }
    }

    console.log(Math.min(...isVisited[End.r][End.c]));
    // console.log(isVisited.map(row => row.join('')).join('\n'));
}

const main = () => {
    const input = require('fs').readFileSync('/dev/stdin').toString().trim().split('\n');

    [C, R] = input.shift().split(' ').map(Number);
    Map = input.map(row => row.split(''));

    // 출발점 찾기
    // Map.some((row, r) => row.some((el, c) => {
    //     if(el === 'C') {
    //         Start = new Loc(r, c, -1, 0);
    //         return true;
    //     }
    // }))
    // 출발점, 도착점 찾기
    for(let r=0; r<R; r++) {
        for(let c=0; c<C; c++) {
            if(Map[r][c] === 'C') {
                if(!Start) Start = new Loc(r, c, -1, 0);
                else End = new Loc(r, c, -1, 0);
            }
        }
    }

    // 레이저 ㄱㄱ
    bfs();
}

main();