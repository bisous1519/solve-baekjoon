// 연구소 N*M
//   - 0 빈칸
//   - 1 벽
//   - 2 바이러스 : 상하좌우 인접한 빈 칸으로 퍼짐
// 벽을 세울거야
//   - 3개
// 안전 영역
//   - 벽 세운 뒤, 바이러스가 퍼질 수 없는 곳
// => 출력
//    안전영역 크기 최댓값
// => 풀이
//    - 3 <= N, M <= 8
//    - 2 <= 바이러스 개수 <= 10
//    - 최대 64칸 중 3칸을 고름 -> 64C3 = 4만 1천 어쩌고
//      -> 최대 10개 바이러스를 각각 BFS로 퍼뜨림
//      -> 안전영역 최댓값 갱신

let R, C;
let Map;
let copyMap;
let virus;
let max = 0;
const dr = [-1, 0, 1, 0]; // 상 우 하 좌
const dc = [0, 1, 0, -1];

const Loc = function(r, c) {
    this.r = r;
    this.c = c;
}

const isIn = (r, c) => {
    return 0<=r && r<R && 0<=c && c<C;
}

const virusBFS = () => {
    let queue = [];
    let isVisited = new Array(R).fill(null).map(() => new Array(C).fill(false));
    virus.forEach(loc => {
        queue.push(loc);
        isVisited[loc.r][loc.c] = true;
    })

    let qIdx = 0;
    while(qIdx < queue.length) {
        let size = queue.length - qIdx;
        while(size-- > 0) {
            const cur = queue[qIdx++];

            for(let d=0; d<4; d++) {
                const goR = cur.r + dr[d];
                const goC = cur.c + dc[d];

                if(!isIn(goR, goC)) continue;
                if(isVisited[goR][goC]) continue;
                if(copyMap[goR][goC] === 1) continue;

                copyMap[goR][goC] = 2;
                queue.push(new Loc(goR, goC));
                isVisited[goR][goC] = true;
            }
        }
    }
}

const combi = (sr, sc, nth, selected) => {
    if(nth === 3) {
        // console.log('-----------', selected);
        // Map 클론하고
        copyMap = Map.map(row => [...row]);
        
        // 3곳에 벽 세우고
        selected.forEach(loc => {
            copyMap[loc.r][loc.c] = 1;
        })
        // console.log(copyMap);

        // 바이러스 BFS
        virusBFS();

        // 안전영역 최댓값 갱신
        let cnt = 0;
        copyMap.forEach(row => {
            cnt += row.filter(el => el === 0).length;
        })
        if(max < cnt) {
            max = cnt;
        }

        return;
    }

    for(let r=sr; r<R; r++) {
        for(let c=sc; c<C; c++) {
            if(Map[r][c] !== 0) continue;
            
            selected[nth] = new Loc(r, c);
            
            if(c === C - 1) {
                combi(r+1, 0, nth+1, [...selected]);
            } else {
                combi(r, c+1, nth+1, [...selected]);
            }
        }
        
        sc = 0;
    }
}

const main = () => {
    const input = require('fs').readFileSync('/dev/stdin').toString().trim().split('\n');

    [R, C] = input.shift().split(' ').map(Number);
    Map = input.map(row => row.split(' ').map(Number));

    // 바이러스 자리 알아놓기
    virus = [];
    Map.forEach((row, r) => row.forEach((el, c) => {
        if(el === 2) {
            virus.push(new Loc(r, c));
        }
    }))

    // 벽 세울 3곳 고름
    combi(0, 0, 0, new Array(3).fill(null));

    console.log(max);
}

main();