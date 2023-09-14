// 100 * 100 격자 위에
//   N개의 드래곤 커브 (1 <= N <= 20)
//   1*1 정사각형의 네 꼭짓점이 모두 드래곤 커브의 일부인 정사각형 개수 출력
// 드래곤커브
//   0세대 : 시작점에서 시작방향으로 길이 1
//   이후 : 이전 세대까지의 모양을 끝점기준으로 시계방향으로 90도 돌려서 끝점을 이전 세대 끝점에 붙인 것
// 입력
//   x, y, d, g : 시작점(x, y) 시작방향(d) 세대(g)
//   입력으로 주어지는 드래곤커브는 격자 안에만 있음
//   드래곤 커브는 겹칠 수도 있음
// 방향
//   0, 1, 2, 3 : 우 상 좌 하
// => 0세대부터 다음 세대로 갈 때 일정한 규칙을 가짐
//    에시) 0방향부터 시작할 때
//      0세대 : 0
//      1세대 : 0 1
//      2세대 : 0 1 2 1
//      3세대 : 0 1 2 1 2 3 2 1
//      4세대 : 0 1 2 1 2 3 2 1 2 3 4(0) 3 2 3 2 1
//    -> 이전세대를 거꾸로 타고 가면서 +1 한 값을 갖는다!
// => 주의)
//      (x, y) -> (c, r)
//      좌표를 맵으로 그릴땐 -> 길이 1을 갖는 선분 하나는 맵에서 두 칸을 차지하는 것처럼 그림

let N;
let Map;
const SIZE = 100;
const dr = [0, -1, 0, 1]; // 우 상 좌 하
const dc = [1, 0, -1, 0];
const vr = [0, 0, 1, 1]; // 나 오 오아 아
const vc = [0, 1, 1, 0];

const main = () => {
    const input = require('fs').readFileSync(process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input.txt`).toString().trim().split('\n');

    N = +input.shift();

    // 맵에 드래곤커브 그리기
    Map = new Array(SIZE + 1).fill(null).map(() => new Array(SIZE + 1).fill(false));
    input.forEach(n => {
        const [C, R, D, G] = n.split(' ').map(el => +el);

        let dir = [D];

        let r = R + dr[D];
        let c = C + dc[D];
        Map[R][C] = Map[r][c] = true;

        for(let g = 1; g <= G; g++) {
            for(let i = dir.length - 1; i >= 0; i--) {
                let d = dir[i] + 1;
                if(d === 4) d = 0;
                dir.push(d);

                r += dr[d];
                c += dc[d];
                Map[r][c] = true;
            }
        }
    })
    // console.log(Map.map(row => row.join(' ')).join('\n'));

    // 쭉 돌면서 정사각형 네꼭짓점
    let cnt = 0;
    for(let r = 0; r < SIZE; r++) {
        for(let c = 0; c < SIZE; c++) {
            let isSquare = true;
            for(let v = 0; v < 4; v++) {
                if(!Map[r + vr[v]][c + vc[v]]) {
                    isSquare = false;
                    break;
                }
            }

            if(isSquare) cnt++;
        }
    }

    console.log(cnt);
}

main();