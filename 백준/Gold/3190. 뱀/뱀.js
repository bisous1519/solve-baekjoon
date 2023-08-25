let N; // 맵크기
let K; // 사과 개수
let Map; // boolean
let L; // 방향전환 횟수
let X; // 몇초후에
let C; // 방향전환
const dr = [0, -1, 0, 1]; // 오 위 왼 아 --> L 일 때의 순서
const dc = [1, 0, -1, 0];

const Loc = function (r, c) {
  this.r = r;
  this.c = c;
};

const isIn = (r, c) => {
  return 1 <= r && r <= N && 1 <= c && c <= N;
};

const main = () => {
  const input = require('fs')
    .readFileSync(
      process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input.txt`
    )
    .toString()
    .trim()
    .split('\n');
  N = +input.shift();
  K = +input.shift();

  // 사과 입력받기
  Map = new Array(N + 1).fill(null).map(() => new Array(N + 1).fill(false));
  for (let k = 0; k < K; k++) {
    const [r, c] = input
      .shift()
      .split(' ')
      .map((el) => +el);
    Map[r][c] = true;
  }

  // 초기화
  let sec = 0;
  let dir = 0; // 처음 방향은 오른쪽
  let snake = [];
  snake.push(new Loc(1, 1)); // 첫 위치는 (1,1)
  L = +input.shift();

  // 뱀 출발!
  outer: for (let l = 0; l <= L; l++) {
    if (l < L) {
      const [x, c] = input.shift().split(' ');
      X = +x;
      C = c;
    } else {
      // 마지막 입력받고서도 방향전환없이 계속 직진해야됨
      X = 0;
    }

    // x초 까지 이동하고 x초 되면 할일 후에 방향전환
    while (sec != X) {
      // 초 지나감
      sec++;

      // 머리 늘려서 한 칸 전진 할건데!
      const pre = snake[0]; // 머리가 있던 좌표
      let goR = pre.r + dr[dir]; // 이동할 좌표
      let goC = pre.c + dc[dir];

      // 벽 넘어가거나 내 몸과 닿으면 게임 끝
      if (!isIn(goR, goC)) break outer; // 벽 넘어감

      // 내 몸과 닿는지 확인 (꼬리 따라가기 전에 닿는것도 게임 끝)
      for (let s of snake) {
        if (s.r === goR && s.c === goC) break outer;
      }

      // 일단 앞머리 전진!
      snake.unshift(new Loc(goR, goC));

      // 사과가 있었으면 먹고! 없었으면 꼬리 한 칸 따라가고!
      if (Map[goR][goC]) {
        Map[goR][goC] = false;
      } else {
        snake.pop();
      }
    }

    // 방향전환
    if (C === 'L') {
      dir = dir + 1 === 4 ? 0 : dir + 1;
    } else {
      dir = dir - 1 < 0 ? 3 : dir - 1;
    }
  } // outer

  console.log(sec);
};

main();