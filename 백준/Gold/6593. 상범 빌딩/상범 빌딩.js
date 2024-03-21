// 상범빌딩 L(층수) * R행 * C열
    // # : 금(지나갈 수 X)
    // . : 비어있음
    // S : 시작점
    // E : 출구
    // 시작지점과 출구는 항상 하나
    // 인접한 6개 칸으로 이동 (동서남북상하)
// => 출력
    // 상범 빌딩을 탈출하는데 필요한 최단 시간
// => 풀이
    // 기본 map도는 bfs. 근데 이제 3차원인!
    // 와 정신없넹 입력받는거 실수해서 입력만 한시간째받았음 ㅜ

let Map;
let L, R, C;
let S, E;
let answer = '';
const dl = [0, 0, 0, 0, 1, -1]; // 동서남북 상하
const dr = [0, 0, 1, -1, 0, 0];
const dc = [1, -1, 0, 0, 0, 0];

const Loc = function(l, r, c) {
    this.l = l;
    this.r = r;
    this.c = c;
}

const isIn = (l, r, c) => {
    return 0<=l && l<L && 0<=r && r<R && 0<=c && c<C;
}

const bfs = () => {
    let queue = [S];
    let isVisited = new Array(L).fill(null).map(() => new Array(R).fill(null).map(() => new Array(C).fill(false)));
    isVisited[S.l][S.r][S.c] = true;

    let qIdx = 0;
    let day = 0;
    while(qIdx < queue.length) {
        let size = queue.length - qIdx;
        day ++;
        while(size -- > 0) {
            const cur = queue[qIdx ++];

            for(let d=0; d<6; d++) {
                const goL = cur.l + dl[d];
                const goR = cur.r + dr[d];
                const goC = cur.c + dc[d];

                if(!isIn(goL, goR, goC)) continue;
                if(isVisited[goL][goR][goC]) continue;
                if(Map[goL][goR][goC] === '#') continue;

                if(Map[goL][goR][goC] === 'E') {
                    answer += `Escaped in ${day} minute(s).\n`;
                    return;
                }

                isVisited[goL][goR][goC] = true;
                queue.push(new Loc(goL, goR, goC));
            }
        }
    }

    answer += 'Trapped!\n';
}

const main = () => {
    const input = require('fs').readFileSync('/dev/stdin').toString().trim().split('\n');

    while(true) {
        [L, R, C] = input.shift().split(' ').map(Number);
        if(L === 0 && R === 0 && C === 0) break;

        // 입력
        Map = new Array(L).fill(null).map(() => new Array(R).fill(null).map(() => new Array(C)));
        for(let l=0; l<L; l++) {
            for(let r=0; r<R; r++) {
                input.shift().split('').forEach((el, c) => {
                    Map[l][r][c] = el;
                })
            }
            input.shift();
        }

        // 출발지점 찾기
        Map.some((flr, l) => flr.some((row, r) => row.some((el, c) => {
            if(el == 'S') {
                S = new Loc(l, r, c);
                return true;
            }
        })))

        bfs();
    }

    console.log(answer);
}

main();