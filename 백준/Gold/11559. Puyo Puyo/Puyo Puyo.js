// 뿌요뿌요게임!
// 필드가 주어졌을 때, 연쇄가 몇 번 연속으로 일어날지 계산
// 필드
    // 12 * 6
    // . : 빈공간
    // R, G, B, P, Y 다섯종류 뿌요
// => 입력
    // 전부 아래로 떨어진 뒤의 상태
    // (뿌요 아래 빈칸이 있는 경우는 없음)
// => 풀이
    // 같은 색 뿌요-4개 이상-상하좌우로 연결 => 한번에 없어짐
    // -> 1연쇄
    // 한턴마다 1연쇄 (단, 한턴에 여러그룹이 터지는건 1로침)
    // 맨 아랫줄 돌면서 래

let Map;
let queue;
let isVisited;
const R = 12, C = 6;
const dr = [-1, 0, 1, 0]; // 상우하좌
const dc = [0, 1, 0, -1];

const Loc = function(r, c) {
    this.r = r;
    this.c = c;
}

const isIn = (r, c) => {
    return 0<=r && r<R && 0<=c && c<C;
}

const dropPuyo = () => {
    for(let c=0; c<C; c++) {
        let bottom = -1;
        for(let r=R-1; r>=0; r--) {
            if(Map[r][c] !== '.') {
                if(bottom !== -1 && bottom !== r) {
                    Map[bottom][c] = Map[r][c];
                    Map[r][c] = '.';
                    bottom --;
                }
                else bottom = r-1;
            }
            if(Map[r][c] === '.') {
                if(bottom === -1) bottom = r;
            }
        }
    }
}

const bfs = (target) => {
    let qIdx = 0;
    while(qIdx < queue.length) {
        let size = queue.length - qIdx;
        while(size -- > 0) {
            const cur = queue[qIdx ++];

            for(let d=0; d<4; d++) {
                const goR = cur.r + dr[d];
                const goC = cur.c + dc[d];

                if(!isIn(goR, goC)) continue;
                if(Map[goR][goC] !== target) continue;
                if(isVisited[goR][goC]) continue;

                queue.push(new Loc(goR, goC));
                isVisited[goR][goC] = true;
            }
        }
    }
}

const main = () => {
    const input = require('fs').readFileSync('/dev/stdin').toString().trim().split('\n');

    Map = input.map(row => row.split(''));

    let turn = 0;
    while(true) {
        // Map돌면서 bfs보냄
        let isBoom = false;
        let willBoom = [];
        isVisited = new Array(R).fill(null).map(() => new Array(C).fill(false));
        for(let c=0; c<C; c++) {
            for(let r=R-1; r>=0; r--) {
                if(Map[r][c] === '.') break; // 아래서 위로 돌면 불필요한곳 탐색 줄일 수 있음. 조금이겠지만
                if(isVisited[r][c]) continue;

                // bfs시작
                queue = [new Loc(r, c)];
                isVisited[r][c] = true;
                bfs(Map[r][c]);
                // console.log(queue);

                // 터질 뿌요였는가?
                queue.length >= 4 && willBoom.push(...queue);
            }
        }

        // 이번 턴에서 터질 뿌요가 있으면 파팝, 아니면 종료
        if(willBoom.length === 0) break;
        else willBoom.forEach(loc => Map[loc.r][loc.c] = '.');

        // 공중에 뜬 뿌요 떨어뜨리기
        dropPuyo();
        
        turn ++;
    }

    console.log(turn);
}

main();