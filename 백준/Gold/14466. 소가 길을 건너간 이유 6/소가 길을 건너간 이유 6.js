let N; // 맵 N*N
let K; // 소 K마리
let R; // 길 R개
let Map;
let CowMap;
let Cows = [];
const [UP, RIGHT, DOWN, LEFT] = [0, 1, 2, 3];
const dr = [-1, 0, 1, 0]; // 상 우 하 좌
const dc = [0, 1, 0, -1];

const Cow = function (k, r, c) {
  this.k = k;
  this.r = r;
  this.c = c;
};

const Loc = function (r, c) {
  this.r = r;
  this.c = c;
};

const isIn = (r, c) => {
  return 1 <= r && r <= N && 1 <= c && c <= N;
};

const findOutDir = (r1, c1, r2, c2) => {
  if (r1 - r2 === 0) {
    if (c1 - c2 === -1) return RIGHT;
    else if (c1 - c2 === 1) return LEFT;
  } else if (r1 - r2 === -1) return DOWN;
  else return UP;
};

const bfs = (k) => {
  const startCow = Cows[k];

  let queue = [];
  queue.push(new Loc(startCow.r, startCow.c));

  let isVisited = new Array(N + 1)
    .fill(null)
    .map(() => new Array(N + 1).fill(false));
  isVisited[startCow.r][startCow.c] = true;

  let cnt = 0; // 만나는 소 세기 (나보다 큰 번호 소만! 중복 안되게하기위해)
  while (queue.length > 0) {
    let size = queue.length;
    while (size-- > 0) {
      const cur = queue.shift();

      for (let d = 0; d < 4; d++) {
        const goR = cur.r + dr[d];
        const goC = cur.c + dc[d];

        if (!isIn(goR, goC)) continue; // 장외
        if (!Map[cur.r][cur.c][d]) continue; // 길 건너야 함
        if (isVisited[goR][goC]) continue; // 이미 방문함

        // 만약에 여기 소가있어!
        if (CowMap[goR][goC] !== 0 && startCow.k < CowMap[goR][goC]) {
          cnt++;
        }

        isVisited[goR][goC] = true;
        queue.push(new Loc(goR, goC));
      }
    }
  }

  return cnt;
};

const main = () => {
  const input = require('fs')
    .readFileSync(
      process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input.txt`
    )
    .toString()
    .trim()
    .split('\n');

  [N, K, R] = input
    .shift()
    .split(' ')
    .map((el) => +el);

  // Map 초기화
  // 소가 갈 수 있는 길만 true => R개 길만 false
  Map = new Array(N + 1)
    .fill(null)
    .map(() => new Array(N + 1).fill(null).map(() => new Array(4).fill(true)));
  for (let r = 0; r < R; r++) {
    const [r1, c1, r2, c2] = input
      .shift()
      .split(' ')
      .map((el) => +el);

    Map[r1][c1][findOutDir(r1, c1, r2, c2)] = false;
    Map[r2][c2][findOutDir(r2, c2, r1, c1)] = false;
  }

  // 소 입력받고 CowMap 배열에 앉히기
  CowMap = new Array(N + 1).fill(null).map(() => new Array(N + 1).fill(0));
  for (let k = 1; k <= K; k++) {
    const [r, c] = input
      .shift()
      .split(' ')
      .map((el) => +el);

    Cows.push(new Cow(k, r, c));
    CowMap[r][c] = k;
  }

  // 1번 소부터 bfs
  // (마지막 소는 안해도 됨! 짜피 나보다 큰 소 없음!)
  let sum = 0;
  for (let k = 1; k <= K - 1; k++) {
    // 나보다 큰 번호 소 중에 내가 못 만난 소 카운트해서 누적
    const cnt = bfs(k - 1);
    sum += K - k - cnt;
  }

  console.log(sum);
};

main();