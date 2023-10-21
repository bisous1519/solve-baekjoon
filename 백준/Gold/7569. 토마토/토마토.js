// 상자에 토마토 보관!
//   - 상자에는 토마토가 안들어있는 칸도 있음
//   - M(C), N(R), H높이 (다 100이하)
// 토마토
//   -  1 : 익음
//   -  0 : 안익음
//   - -1 : 토마토없음
// 보관 후 하루가 지나면,
//   -> 익은 토마토에 인접한 안익은 토마토가 익음
//      인접 : 위 아 왼 오 앞 뒤 (6방)
// => 출력
//    며칠이 지나면 다 익게 되는지 최소 일수
//    (저장될 때부터 모든 토마토가 익어있으면 0)
//    (모든 토마토가 익지 못하는 상황이면 -1)
// => 입력
//    가장 밑 상자부터 R*C 씩 입력됨
// => 풀이
//    전형적인 BFS
//    Map[H][R][C]
//    0 개수 먼저 세어놓고(: raw)
//      -> 하나 익을때마다 raw--
//      -> raw === 0 이면 종료
//    익은 토마토들을 순서대로 queue에 먼저 넣어놓고
//      -> 6방으로 익힘

let R, C, H;
let Map;
const [RIPE, UNRIPE, BLANK] = [1, 0, -1];
let unripe = 0;
const dh = [0, 0, 0, 0, 1, -1]; // 위 아 왼 오 위층 아래층
const dr = [-1, 1, 0, 0, 0, 0];
const dc = [0, 0, -1, 1, 0, 0];

const Loc = function(h, r, c) {
    this.h = h;
    this.r = r;
    this.c = c;
}

const isIn = (h, r, c) => {
    return 0<=h && h<H && 0<=r && r<R && 0<=c && c<C;
}

const bfs = (queue) => {
    let day = -1;

    while(queue.length > 0) {
        day++;

        let size = queue.length;
        let curIdx = 0;
        let nextQueue = [];
        while(size -- > 0) {
            const cur = queue[curIdx++];

            for(let d = 0; d < 6; d++) {
                const goH = cur.h + dh[d];
                const goR = cur.r + dr[d];
                const goC = cur.c + dc[d];

                if(!isIn(goH, goR, goC)) continue; // 장외

                if(Map[goH][goR][goC] === UNRIPE) {
                    Map[goH][goR][goC] = RIPE;
                    nextQueue.push(new Loc(goH, goR, goC));
                    unripe--;
                }
            }
        }
        queue = nextQueue;
    }

    if(unripe === 0) return day;
    else return -1;
}

const main = () => {
    const input = require('fs').readFileSync(process.platform === 'linux' ? '/dev/stdin' : `{$__dirname}/input.txt`).toString().trim().split('\n');

    [C, R, H] = input.shift().split(' ').map(el => +el);
    
    Map = new Array(H).fill(null).map(() => new Array(R));
    let queue = [];
    for(let h = 0; h < H; h++) {
        for(let r = 0; r < R; r++) {
            const row = input.shift().split(' ').map(el => +el);

            Map[h][r] = row;
            
            // 안익은 토마토 개수
            unripe += row.filter(el => el === UNRIPE).length;

            // 익은 토마토 위치 -> queue에 넣기
            row.forEach((el, c) => {
                if(el === RIPE) queue.push(new Loc(h, r, c));
            })
        }
    }

    if(unripe === 0) console.log(0);
    else console.log(bfs(queue));
}

main();