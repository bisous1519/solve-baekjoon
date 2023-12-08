// 선 플레이어 : 홀수번째
// 후 플레이어 : 짝수번쨰
// 점 n개
//   - 세 점이 일직선 위에 있는 경우는 없음
//   - 두 점을 선택해서 선분 긋기
//   - 이전에 그린거 다시 그릴 수 없고, 교차는 가능
//   - 처음 사이클을 완성하는 순간 게임종료
// 사이클
//   - 한 끝점에서 출발해, 모든 선분을 한번씩만 지나서 출발점으로 되돌아올 수 있음
// => 출력
//    사이클이 처음 만들어진 차례의 번호를 출력
//    또는, 종료되지 않았으면 0 출력
// => 풀이
//    유니온파인드
//    두 노드의 부모를 확인해서 부모가 다르면 그냥 연결하면되고
//    부모가 같다면 -> 연결하면 사이클임..?

let N, M;
let parents;

const find = (a) => {
  if(parents[a] === a) return a;
  return parents[a] = find(parents[a]);
}

const union = (a, b) => {
  const rootA = find(a);
  const rootB = find(b);

  if(rootA <= rootB) {
    parents[rootB] = rootA;
  } else {
    parents[rootA] = rootB;
  }
}

const main = () => {
  const input = require('fs').readFileSync('/dev/stdin').toString().trim().split('\n');

  [N, M] = input.shift().split(' ').map(el => +el);

  // 부모 배열 초기화하고 노드 연결 시작
  parents = new Array(N).fill(null).map((_, idx) => idx);
  let fin = false;
  input.forEach((row, nth) => {
    if(!fin) {
      const [a, b] = row.split(' ').map(el => +el);
  
      // 얘네 연결시 사이클 발생!!
      if(find(a) === find(b)) {
        console.log(nth + 1);
        fin = true;
      }
  
      // 아니면 그냥 연결
      else {
        union(a, b);
      }
    }
  });

  if(!fin) console.log(0);
}

main();