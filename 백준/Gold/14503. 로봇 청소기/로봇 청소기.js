// 1. 현재 칸 청소 안돼있으면 현재 칸 청소
// 2. 동서남북이 다 청소 돼있으면
//       a. 방향 유지한채로 한칸 후진 가능
//           -> 한칸 후진하고 1로 돌아감
//       b. 후진 불가
//           -> 작동 멈춤
// 3. 동서남북 청소 안한 칸 있음
//       a. 왼쪽으로 회전
//           -> 회전했을 때 앞 칸이 빈칸이면 전진하고 1로 돌아감

let N, M;
let Map;
const dr = [-1, 0, 1, 0]; // 위오아왼
const dc = [0, 1, 0, -1];

const main = () => {
    const input = require('fs').readFileSync(process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input.txt`).toString().trim().split('\n');

    [N, M] = input.shift().split(' ').map(el => +el);
    let [r, c, dir] = input.shift().split(' ').map(el => +el);
    Map = input.map(row => row.split(' ').map(el => +el));
    
    // 청소시작
    // 벽: 1, 청소필요: 0, 청소됨: 2
    let cnt = 0;
    while(true) {
        // 청소필요
        if(Map[r][c] === 0) {
            Map[r][c] = 2;
            cnt ++;
        }
        // console.log(r, c, dir);
        // console.log(Map.map(row => row.join(',')).join('\n'));

        // 동서남북
        let isBreak = false;
        for(let i=0, d=dir; i<4; i++) {
            d--;
            if(d === -1) d = 3;
            let goR = r + dr[d];
            let goC = c + dc[d];
            // console.log(goR, goC)

            // 청소 필요한 칸
            if(Map[goR][goC] === 0) {
                r = goR;
                c = goC;
                dir = d;
                isBreak = true;
                break;
            }
        }

        // 동서남북 청소가 다 됨
        if(!isBreak) {
            let d = 1;
            if(dir === 0) d = 2;
            else if(dir === 1) d = 3;
            else if(dir === 2) d = 0;

            // 후진이 가능 한지
            let goR = r + dr[d];
            let goC = c + dc[d];
            if(Map[goR][goC] !== 1) {
                r = goR;
                c = goC;
            } else break;
        }
    }

    console.log(cnt);
}

main();