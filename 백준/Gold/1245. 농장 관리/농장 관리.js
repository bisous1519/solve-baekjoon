let R, C;
let Map;
let cnt = 0;
let isVisited;
const dr = [-1, -1, 0, 1, 1, 1, 0, -1]; // 상 상우 우 우하 하 하좌 좌 좌상
const dc = [0, 1, 1, 1, 0, -1, -1, -1];

const isIn = (r, c) => {
  return 0<=r && r<R && 0<=c && c<C;
}

const bfs = (r, c) => {
  let queue = [];
  queue.push([r, c]);
  isVisited[r][c] = true;
  
  let isBong = true;
  while(queue.length > 0) {
    let size = queue.length;
    while(size -- > 0) {
      const [curR, curC] = queue.shift();
      
      for(let d=0; d<8; d++) {
        const goR = curR + dr[d];
        const goC = curC + dc[d];
        
        if(!isIn(goR, goC)) continue;
        if(Map[goR][goC] > Map[curR][curC]) {
          // 그럼 지금건 봉우리가 아님
          isBong = false;
          continue;
        }
        if(isVisited[goR][goC]) continue;
        if(Map[goR][goC] < Map[curR][curC]) continue;

        // 같음!
        queue.push([goR, goC]);
        isVisited[goR][goC] = true;
        
      }
    }
  }

  if(isBong) cnt++;
}

const main = () => {
  const input = require('fs').readFileSync('/dev/stdin').toString().trim().split('\n');

  [R, C] = input.shift().split(' ').map(el => +el);
  Map = input.map(row => row.split(' ').map(el => +el));
  // console.log(Map.map(row => row.join(' ')).join('\n'))

  isVisited = new Array(R).fill(null).map(() => new Array(C).fill(false));

  for(let r=0; r<R; r++) {
    for(let c=0; c<C; c++) {
      if(!isVisited[r][c]) {
        bfs(r, c);
      }
    }
  }

  console.log(cnt);
}

main();