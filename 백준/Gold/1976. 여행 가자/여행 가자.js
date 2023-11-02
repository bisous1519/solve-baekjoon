// N개 도시 (1 ~ N번)
// 임의 두 도시 사이에 길이 있을수도 없을수도
// => 입력
//    N*N 인접행렬
//    여행계획
// => 출력
//    여행을 할 수 있는지
// => 풀이
//    여행 계획에 속해있는 모든 도시를 갈 수 있는지를 확인
//    bfs
//    여행 출발지점부터 시작해서 bfs 다 돌림
//    여행 계획에 있는 모든 곳이 visited 됐으면 YES

let N;
let M;
let Map;
let Plan;

const bfs = () => {
  let queue = [];
  queue.push(Plan[0]);

  let isVisited = new Array(N).fill(false);
  isVisited[Plan[0]] = true;

  while(queue.length > 0) {
    let size = queue.length;
    while(size -- > 0) {
      const cur = queue.shift();

      for(let n=0; n<N; n++) {
        if(isVisited[n]) continue;
        if(Map[cur][n]) {
          isVisited[n] = true;
          queue.push(n);
        }
      }
    }
  }

  return isVisited;
}

const main = () => {
  const input = require('fs').readFileSync('/dev/stdin').toString().trim().split('\n');

  N = +input.shift();
  M = +input.shift();
  Plan = input.pop().split(' ').map(el => (+el) - 1);
  Map = input.map(row => row.split(' ').map(el => +el));

  const visited = bfs();
  for(const m of Plan) {
    if(!visited[m]) {
      console.log("NO");
      return;
    }
  }
  console.log("YES");
}

main();