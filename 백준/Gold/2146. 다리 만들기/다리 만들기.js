// 여러 섬
// 두개의 섬만 잇기로 함 -> 다리 한개 두기
// 가장 짧은 다리!
// => 출력
    // 가장 짧은 다리의 길이
// => 풀이
    // 대륙 나누기
        // Map 전부 돌면서
        // 1만날 때 마다 bfs 돌리면서 다른 숫자로 저장
    // 다리 놓기
        // Map 전부 돌면서
        // 0이 아닌곳에서 출발!
        // isVisited : 다리 몇개 놔서 도착한 곳인지 누적개수 저장
let N;
let Map;
let min = Number.MAX_SAFE_INTEGER;
const dr = [-1, 0, 1, 0]; // 상우하좌
const dc = [0, 1, 0, -1];

const Loc = function(r, c) {
    this.r = r;
    this.c = c;
}

const isIn = (r, c) => {
    return 0<=r && r<N && 0<=c && c<N;
}

const areaBfs = (sR, sC, nth) => {
    let queue = [new Loc(sR, sC)];

    let qIdx = 0;
    while(qIdx < queue.length) {
        let size = queue.length - qIdx;
        while(size -- > 0) {
            const cur = queue[qIdx ++];

            for(let d=0; d<4; d++) {
                const goR = cur.r + dr[d];
                const goC = cur.c + dc[d];
                
                if(!isIn(goR, goC)) continue;
                if(Map[goR][goC] !== 1) continue;

                Map[goR][goC] = nth;
                queue.push(new Loc(goR, goC));
            }
        }
    }
}

const bridgeBfs = (sR, sC, from) => {
    let queue = [new Loc(sR, sC)];
    let isVisited = new Array(N).fill(null).map(() => new Array(N).fill(false));
    isVisited[sR][sC] = true;

    let qIdx = 0;
    let cnt = -1;
    while(qIdx < queue.length) {
        cnt ++;
        if(cnt >= min) break;
        
        let size = queue.length - qIdx;
        while(size -- > 0) {
            const cur = queue[qIdx ++];

            for(let d=0; d<4; d++) {
                const goR = cur.r + dr[d];
                const goC = cur.c + dc[d];

                if(!isIn(goR, goC)) continue;
                if(isVisited[goR][goC]) continue;

                if(Map[goR][goC] !== 0) {
                    // 다른 섬에 도착
                    if(Map[goR][goC] !== from) {
                        min = Math.min(min, cnt);
                    }
                    continue;
                }
                
                queue.push(new Loc(goR, goC));
                isVisited[goR][goC] = true;
            }
        }
    }
}

const main = () => {
    const input = require('fs').readFileSync('/dev/stdin').toString().trim().split('\n');

    N = +input.shift();
    Map = input.map(row => row.split(' ').map(Number));

    // 대륙 나누기
    let nth = 2;
    for(let r=0; r<N; r++) {
        for(let c=0; c<N; c++) {
            if(Map[r][c] === 1) {
                Map[r][c] = nth;
                areaBfs(r, c, nth ++);
            }
        }
    }
    // console.log(Map.map(row => row.join(' ')).join('\n'));

    // 다리 놓기
    for(let r=0; r<N; r++) {
        for(let c=0; c<N; c++) {
            if(Map[r][c] !== 0) bridgeBfs(r, c, Map[r][c]);
        }
    }

    console.log(min);
}

main();