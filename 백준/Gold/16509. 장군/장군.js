let Map;
let King;
let Sang;
const R = 10;
const C = 9;
const dr = [2, 2, 3, 3, -2, -2, -3, -3];
const dc = [-3, 3, -2, 2, -3, 3, -2, 2];
const path = [
  [-1, 1],
  [-1, -1],
  [-1, 1],
  [-1, -1],
  [1, 1],
  [1, -1],
  [1, 1],
  [1, -1],
];

const Loc = function (r, c) {
  this.r = r;
  this.c = c;
};

const isIn = (r, c) => {
  return 0 <= r && r < R && 0 <= c && c < C;
};

const bfs = () => {
  let queue = [];
  queue.push(new Loc(Sang.r, Sang.c));
  let isVisited = new Array(R).fill(null).map(() => new Array(C).fill(false));
  isVisited[Sang.r][Sang.c] = true;

  let cnt = 0;
  while (queue.length > 0) {
    let size = queue.length;
    cnt++;
    while (size-- > 0) {
      const cur = queue.shift();

      for (let d = 0; d < 8; d++) {
        let targetR = cur.r + dr[d];
        let targetC = cur.c + dc[d];
        let canGo = true;

        // 도착지점부터 출발지 사이 경로까지 확인하기 위해
        for (let step = 0; step < 3; step++) {
          let goR = targetR + path[d][0] * step;
          let goC = targetC + path[d][1] * step;

          // 장외
          if (!isIn(goR, goC)) {
            canGo = false;
            break;
          }

          // 가는 경로중에 장군있음
          if (step !== 0 && Map[goR][goC] === 1) {
            canGo = false;
            break;
          }
        }

        // 가!
        if (canGo && !isVisited[targetR][targetC]) {
          if (targetR === King.r && targetC === King.c) {
            return cnt;
          }

          isVisited[targetR][targetC] = true;
          queue.push(new Loc(targetR, targetC));
        }
      }
    }
  }

  return -1;
};

const main = () => {
  const input = require('fs')
    .readFileSync(
      process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input.txt`
    )
    .toString()
    .trim()
    .split('\n');
  const [sR, sC] = input.shift().split(' ');
  const [kR, kC] = input.shift().split(' ');
  Sang = new Loc(+sR, +sC);
  King = new Loc(+kR, +kC);
  // 입력

  Map = new Array(R).fill(null).map(() => new Array(C).fill(0));
  Map[King.r][King.c] = 1;

  console.log(bfs());
};

main();