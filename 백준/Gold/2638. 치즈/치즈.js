// map N*M -> R*C (둘다 <= 100)
//   - 치즈 : 1
//   - 외부공기 : 2 -> 처음 bfs 돌려주고나서 0중에 치즈닿는 공기는 2로 바뀜
//   - 가짜공기 : 0
// 치즈
//   - 4변 중 2변 이상! 공기와 접촉하면 녹음
//   - 맨 가장자리는 치즈 없음
// => 출력
//    치즈가 모두 녹는데 걸리는 시간
// => 풀이
//    일단 공기 부분을 BFS돌려서
//      - 치즈랑 맞닿는 공기만 airQueue에 넣음
//      - map에는 2로 표시
//    bfs
//      -> airQueue에서 공기를 하나씩 꺼냄
//           -> 공기 기준으로 4방탐색
//               -> 방문 안했는데 가짜공기(0) 있으면 거기 2로 바꾸고 지금 턴에 돌려야하니까 queue에 unshift함
//               -> 방문 안했는데 치즈 있으면, 치즈기준 4방탐색
//                    - 2면 이상 외부공기(2)면
//                      melt에 push하고
//                      여기 공기 될거니까 airQueue에 넣음
//                    - 안녹는 치즈면 그냥
//                      다시 airQueue에 넣음 (다음턴에 다시 봐야하는곳임)
//           -> airQueue 다 확인했으면 melt에 있는 치즈 다 녹임
//              (map에서 2로 바꿈)
//           -> melt를 queue에 넣어서 melt 기준으로 bfs 또돌림
//              (치즈에 둘러싸인 0이었다가 이번에 melt된거 때문에 외부공기 통할수도있음)
//              4방에 0 있으면 2로 바꿔주고 airQueue에 넣음

let R;
let C;
let Map;
let leftCheese = 0;
const dr = [-1, 0, 1, 0]; // 위오아왼
const dc = [0, 1, 0, -1];

const Loc = function(r, c) {
    this.r = r;
    this.c = c;
}

const isIn = (r, c) => {
    return 0<=r && r<R && 0<=c && c<C;
}

const airBfs = (r, c) => {
    let airQueue = [];

    let queue = [];
    let isVisited = new Array(R).fill(null).map(() => new Array(C).fill(false));
    if(r && c) {
        queue.push(new Loc(r, c));
        Map[r][c] = 2;
        isVisited[r][c] = true;
    } else {
        queue.push(new Loc(0, 0));
        Map[0][0] = 2;
        isVisited[0][0] = true;
    }

    while(queue.length > 0) {
        let size = queue.length;
        while(size -- > 0) {
            const cur = queue.shift();

            for(let d = 0; d < 4; d++) {
                const goR = cur.r + dr[d];
                const goC = cur.c + dc[d];

                if(!isIn(goR, goC)) continue;
                if(Map[goR][goC] === 2) continue; // 이미 방문한 외부공기

                // 같은 외부공기인데 아직 방문안함
                if(Map[goR][goC] === 0) {
                    Map[goR][goC] = 2;
                    queue.push(new Loc(goR, goC));
                }

                // 치즈임! 그럼나는 치즈랑 닿는 외부공기!
                else {
                    if(!isVisited[cur.r][cur.c]) {
                        isVisited[cur.r][cur.c] = true;
                        airQueue.push(new Loc(cur.r, cur.c));
                    }
                }
            }
        }
    }

    return airQueue; // 치즈랑 닿은 공기들 위치 배열
}

const meltBfs = (airQueue) => {
    let hour = 0;

    while(airQueue.length > 0) {
        hour ++;

        let willMelt = [];
        let size = airQueue.length;
        while(size -- > 0) {
            const curAir = airQueue.shift();

            for(let d = 0; d < 4; d++) {
                const goR = curAir.r + dr[d];
                const goC = curAir.c + dc[d];

                if(!isIn(goR, goC)) continue;
                if(Map[goR][goC] === 2) continue;

                // 치즈 사이에 구멍이 있었어서 이제야 통하게 된 가짜공기를 만남!
                if(Map[goR][goC] === 0) {
                    Map[goR][goC] = 2;
                    airQueue.unshift(new Loc(goR, goC));
                    size ++;
                }

                // 치즈만남! goR,goC가 치즈였음!
                else if(Map[goR][goC] === 1) {
                    let cntAir = 0;
                    for(let cd = 0; cd < 4; cd++) {
                        const cR = goR + dr[cd];
                        const cC = goC + dc[cd];

                        if(Map[cR][cC] === 2) cntAir++;
                    }

                    if(cntAir >= 2) {
                        Map[goR][goC] = 3; // 곧 녹을 치즈
                        willMelt.push(new Loc(goR, goC));
                    }
                    
                    // 아직 바깥 공기로 남아있어야함 (다음 턴에 다시 치즈 녹여봐야함)
                    else airQueue.push(curAir);
                }
            }
        }

        // console.log('hour ', hour, '-----------');
        // console.log(willMelt.length);
        // console.log(Map.map(row => row.join(' ')).join('\n'));

        // 치즈 녹이자!
        willMelt.forEach(cur => {
            Map[cur.r][cur.c] = 2;
            leftCheese --;
        })

        // 다 녹음!?
        if(leftCheese === 0) return hour;

        // 지금 녹은 치즈가 다음 턴때 외부공기가 됨
        airQueue.push(...willMelt);

        // 새로 외부공기 된 가짜공기 있나? 가짜공기 다 외부공기만들어주기
        willMelt.forEach(cur => {
            for(let d=0; d<4; d++) {
                const goR = cur.r + dr[d];
                const goC = cur.c + dc[d];

                if(Map[goR][goC] === 0) {
                    airQueue.push(new Loc(goR, goC));

                    const newAir = airBfs(goR, goC); // return airQueue (치즈랑 맞닿은 외부공기)
                    airQueue.push(...newAir);
                    // console.log('newAir',newAir);
                    // console.log('airQueue',airQueue)
                }
            }
        })
    }
}

const main = () => {
    const input = require('fs').readFileSync(process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input.txt`).toString().trim().split('\n');

    [R, C] = input.shift().split(' ').map(el => +el);
    Map = input.map(row => row.split(' ').map(el => {
        if(+el === 1) leftCheese ++;
        return +el;
    }));

    const airQueue = airBfs();

    console.log(meltBfs(airQueue));
}

main();