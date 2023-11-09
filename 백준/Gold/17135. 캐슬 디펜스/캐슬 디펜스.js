// Map N*M
//  - 각 칸에 있는 적은 최대 하나
//  - N+1행의 모든 칸에는 성
// 궁수 3명
//  - 성이 있는 칸엥 배치
//  - 하나의 칸에 최대 하나
//  - 각 턴에 궁수는 적 하나를 공격 (모든 궁수 동시에)
//     - 거리가 D 이하인 적 중 가장 가까운 적 (여럿이면 가장 왼쪽)
//  - 공격당한 적은 게임에서 제외됨
// 궁수 공격 -> 적 이동
//  - 아래로 한 칸
//  - 성이 있는 칸으로 이동하면 제외됨
//  - 모든 적인 제외되면 게임 끝
// => 출력
//    궁수의 공격으로 제거할 수 있는 적의 최대 수 계산
// => 풀이
//    15C3 로 궁수 위치 선정
//      -> 각 궁수에서 bfs로 (딥스 D 이하, 왼쪽부터) 가장 먼저 만나는 적
//           => 죽일예정
//      -> 죽이고 남은 적 한칸 전진
//    시간복잡도 : 15C3 * 궁수3명bfs * 최대 N

let N, M, D;
let originMap;
let Map;
let Enemy = 0;
let max = 0;
const dr = [0, -1, 0]; // 왼위오
const dc = [-1, 0, 1];

const Loc = function(r, c, d) {
  this.r = r;
  this.c = c;
  this.d = d;
}

const isIn = (r, c) => {
  return 0<=r && r<N && 0<=c && c<M;
}

const bfs = (loc) => {
  let queue = [];
  queue.push(loc);
  let isVisited = new Array(N).fill(null).map(() => new Array(M).fill(false));

  while(queue.length > 0) {
    let size = queue.length;
    while(size -- > 0) {
      const cur = queue.shift();

      for(let d=0; d<3; d++) {
        const goR = cur.r + dr[d];
        const goC = cur.c + dc[d];
        if(!isIn(goR, goC)) continue;
        if(isVisited[goR][goC]) continue;

        if(Map[goR][goC] === 1) return new Loc(goR, goC, 0);
          
        isVisited[goR][goC] = true;
        if(cur.d + 1 < D) {
          queue.push(new Loc(goR, goC, cur.d + 1));
        }
      }
    }
  }
  
  return false;
}

const attack = (army) => {
  Map = originMap.map(row => [...row]);
  let remain = Enemy;
  let killed = 0;

  while(remain > 0) {
    let kill = [];

    // 각 궁수마다 죽일 적의 위치 저장
    army.forEach(m => {
      const loc = bfs(new Loc(N, m, 0));
      if(loc) kill.push(loc);
    })

    // 적 죽이고
    if(kill.length > 0) {
      kill.forEach(loc => {
        if(Map[loc.r][loc.c] === 1) {
          Map[loc.r][loc.c] = 0;
          killed ++;
          remain --;
        }
      })
    }

    // 적이 앞으로 한 칸 전진
    let arr = Map.pop();
    const cnt = arr.filter(el => el === 1).length;
    remain -= cnt; // 성 자리까지 간 적!
    Map.unshift(new Array(M).fill(0));
  }

  max = Math.max(max, killed);
}

const combi = (nth, start, isSelected) => {
  if(nth === 3) {
    attack(isSelected);
    
    return;
  }
  
  for(let i=start; i<M; i++) {
    isSelected[nth] = i;
    combi(nth + 1, i + 1, [...isSelected]);
  }
}

const main = () => {
  const input = require('fs').readFileSync(process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input.txt`).toString().trim().split('\n');

  [N, M, D] = input.shift().split(' ').map(el => +el);
  originMap = input.map(row => row.split(' ').map(el => {
    if(+el === 1) Enemy ++;
    return +el
  }));

  combi(0, 0, new Array(3));

  console.log(max);
}

main();