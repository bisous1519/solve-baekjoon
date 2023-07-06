let N;
let L;
let R;
let Map = [];
let Day = 0;
let isVisited;
let dr = [-1, 1, 0, 0]; // 상하좌우
let dc = [0, 0, -1, 1];

function Loc(r, c) {
  this.r = r;
  this.c = c;
}

const isIn = (r, c) => {
  return 0 <= r && r < N && 0 <= c && c < N;
};

const bfs = (r, c) => {
  let queue = [];
  let unionList = [];

  isVisited[r][c] = true;
  queue.push(new Loc(r, c));

  // 연합인 곳들 인구 누적
  let sum = 0;
  while (queue.length > 0) {
    let size = queue.length;
    while (size-- > 0) {
      const cur = queue[0];
      queue.shift();
      unionList.push(cur);

      sum += Map[cur.r][cur.c];

      for (let d = 0; d < 4; d++) {
        const goR = cur.r + dr[d];
        const goC = cur.c + dc[d];

        if (!isIn(goR, goC)) continue; // map 밖이면 패스
        if (isVisited[goR][goC]) continue; // 방문한곳이면 패스

        // 인구수 차이 조건 맞으면 연합
        const diff = Math.abs(Map[cur.r][cur.c] - Map[goR][goC]);
        if (L <= diff && diff <= R) {
          isVisited[goR][goC] = true;
          queue.push(new Loc(goR, goC));
        }
      }
    }
  }

  // 연합인 곳들 인구 재분배
  if (unionList.length > 1) {
    const people = Math.floor(sum / unionList.length);
    for (let loc of unionList) {
      Map[loc.r][loc.c] = people;
    }
    // console.log('이동후', Map.map((row) => row.join(' ')).join('\n'));
    // 인구 이동이 있었음
    return true;
  }
  // 인구 이동이 없었음
  return false;
};

const main = () => {
  const input = require('fs')
    .readFileSync('/dev/stdin')
    .toString()
    .trim()
    .split('\n');

  let [n, l, r] = input[0].split(' ').map((num) => +num);
  N = n;
  L = l;
  R = r;
  input.shift();

  input.map((row) => Map.push(row.split(' ').map((num) => +num)));

  // 하루가 시작되었읍니다
  while (true) {
    // 방문배열 하루마다 초기화
    isVisited = Array.from(new Array(N), () => new Array(N).fill(false));
    // console.log(isVisited.map((row) => row.join(' ')).join('\n'));

    // 오늘 하루 이동이 있었나?
    let move = false;

    for (let r = 0; r < N; r++) {
      for (let c = 0; c < N; c++) {
        if (!isVisited[r][c]) {
          if (bfs(r, c)) {
            // 이동이 있었다면 true로 바꿔줌
            move = true;
          }
        }
      }
    }

    // 한번도 이동이 없었으면 종료
    if (!move) break;

    // 이동 했고 다음날
    Day++;
  }

  console.log(Day);
};

main();