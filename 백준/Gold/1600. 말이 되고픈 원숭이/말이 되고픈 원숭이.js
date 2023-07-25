let K;
let R, C;
let Map;
const drH = [-2, -1, 1, 2, 2, 1, -1, -2]; // 위오위, 위오오, 아오오, 아오아, 아왼아, 아왼왼, 위왼왼, 위왼위
const dcH = [1, 2, 2, 1, -1, -2, -2, -1];
const drM = [-1, 0, 1, 0]; // 위 오 아 왼
const dcM = [0, 1, 0, -1];

function Loc(r, c, k) {
  this.r = r;
  this.c = c;
  this.k = k;
}

const isIn = (r, c) => {
  return 0 <= r && r < R && 0 <= c && c < C;
};

const isArrive = (r, c, move) => {
  if (r === R - 1 && c === C - 1) {
    console.log(move);
    return true;
  }
  return false;
};

const bfs = () => {
  let queue = [];
  let isVisited = Array.from(Array(R), () =>
    Array.from(Array(C), () => new Array(K + 1).fill(false))
  );

  queue.push(new Loc(0, 0, 0));
  isVisited[0][0][0] = true;

  let move = 0;
  while (queue.length > 0) {
    let size = queue.length;
    move++;

    while (size-- > 0) {
      const cur = queue.shift();

      if (cur.k < K) {
        // 말처럼 이동
        for (let d = 0; d < 8; d++) {
          const goR = cur.r + drH[d];
          const goC = cur.c + dcH[d];

          if (!isIn(goR, goC)) continue; // 장외
          if (isArrive(goR, goC, move)) return; // 도착!
          if (Map[goR][goC] === 1) continue; // 장애물
          if (isVisited[goR][goC][cur.k + 1]) continue; // 이미 방문

          isVisited[goR][goC][cur.k + 1] = true;
          queue.push(new Loc(goR, goC, cur.k + 1));
        }
      }

      // 원숭이처럼 이동
      for (let d = 0; d < 4; d++) {
        const goR = cur.r + drM[d];
        const goC = cur.c + dcM[d];

        if (!isIn(goR, goC)) continue; // 장외
        if (isArrive(goR, goC, move)) return; // 도착!
        if (Map[goR][goC] === 1) continue; // 장애물
        if (isVisited[goR][goC][cur.k]) continue; // 이미 방문

        isVisited[goR][goC][cur.k] = true;
        queue.push(new Loc(goR, goC, cur.k));
      }
    }
  }

  console.log(-1);
};

const main = () => {
  const input = require('fs')
    .readFileSync(
      process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input.txt`
    )
    .toString()
    .trim()
    .split('\n');
  K = +input.shift();
  const [c, r] = input.shift().split(' ');
  R = +r;
  C = +c;
  Map = input.map((row) => row.split(' ').map((el) => +el));
  // 입력

  if (R === 1 && C === 1) {
    console.log(0);
  } else {
    bfs();
  }
};

main();