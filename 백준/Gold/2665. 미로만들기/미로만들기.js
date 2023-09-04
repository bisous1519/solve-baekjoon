// 4방탐색 BFS 돌면서
// 검은 벽을 만나면 얘를 뚫고 갈 경우와 안가는 경우 두가지로 분기
// queue.push(new Loc(r, c, cnt))
// 방문배열
//   isVisited[r][c] 를 전부 -1로 초기화
//   몇개의 검은 벽을 뚫고 왔는지 저장해서
//   isVisited[r][c] === cnt 인데 지금 더 적은 벽을 뚫고 온 경우면 (cnt가 더 작으면) cnt 다시 저장하고 queue에 넣음

let N;
let Map;
const dr = [-1, 0, 1, 0]; // 상 우 하 좌
const dc = [0, 1, 0, -1];

const Loc = function (r, c, cnt) {
  this.r = r;
  this.c = c;
  this.cnt = cnt;
};

const isIn = (r, c) => {
  return 0 <= r && r < N && 0 <= c && c < N;
};

const bfs = () => {
  let queue = [];
  queue.push(new Loc(0, 0, 0));

  let isVisited = new Array(N).fill(null).map(() => new Array(N).fill(-1));
  isVisited[0][0] = 0;

  while (queue.length > 0) {
    let size = queue.length;
    while (size-- > 0) {
      const cur = queue.shift();
      //   console.log('---------------');
      //   console.log(cur);

      for (let d = 0; d < 4; d++) {
        const goR = cur.r + dr[d];
        const goC = cur.c + dc[d];

        if (!isIn(goR, goC)) continue;

        // 흰 방인 경우
        if (Map[goR][goC] === 1) {
          if (isVisited[goR][goC] === -1 || isVisited[goR][goC] > cur.cnt) {
            isVisited[goR][goC] = cur.cnt;
            queue.push(new Loc(goR, goC, isVisited[goR][goC]));
            // console.log('흰방 push', goR, goC, isVisited[goR][goC]);
            // console.log(isVisited.map((row) => row.join(' ')).join('\n'));
          }
        }
        // 까만 방인 경우
        else {
          if (isVisited[goR][goC] === -1 || isVisited[goR][goC] > cur.cnt + 1) {
            isVisited[goR][goC] = cur.cnt + 1;
            queue.push(new Loc(goR, goC, isVisited[goR][goC]));
            // console.log('까만방 push', goR, goC, isVisited[goR][goC]);
            // console.log(isVisited.map((row) => row.join(' ')).join('\n'));
          }
        }
      }
    }
  }

  //   console.log('---------');
  //   console.log(isVisited.map((row) => row.join(' ')).join('\n'));

  return isVisited[N - 1][N - 1];
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
  Map = input.map((row) => row.split('').map((el) => +el));

  console.log(bfs());
};

main();