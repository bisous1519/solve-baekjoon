// 2개의 매장 지으려고 함
// N개 건물, M개 도로
//   - 건물 1번 ~ N번
//   - i번째 도로는 Ai, Bi 를 1시간에 양방향으로 이동
// 2개 건물 골라서 치킨집!
//   - 모든 건물에서의 접근성의 합을 최소화
//   - X건물의 접근성 : X에서 가장 가까운 치킨집까지 왕복하는 최단 시간
// => 출력
//    최적의 위치가 될 수 있는 건물 2개의 번호 (건물 번호 중 작은게 더 작을수록, 같으면 큰번호가 더 작을수록)
//    모든 건물에서 가장 가까운 치킨집까지 왕복하는 최단 시간의 총합
// => 풀이
//    플로이드워셜로 모든 정점에서 다른 모든 정점까지의 최단거리 구하기
//    건물 두개 골라서 접근성 구하기

let N, M;
let graph;

const main = () => {
  const input = require('fs').readFileSync('/dev/stdin').toString().trim().split('\n');

  [N, M] = input.shift().split(' ').map(el => +el);
  
  // 플로이드워셜 초기세팅
  graph = new Array(N+1).fill(null).map(() => new Array(N+1).fill(Number.MAX_SAFE_INTEGER));
  for(let n=1; n<=N; n++) {
    graph[n][n] = 0;
  }
  input.forEach(row => {
    const [a, b] = row.split(' ');
    graph[a][b] = 1;
    graph[b][a] = 1;
  })

  // 플로이드워셜!
  for(let i=1; i<=N; i++) {
    for(let j=1; j<=N; j++) {
      if(i === j) continue;

      for(let k=1; k<=N; k++) {
        if(i === k || j === k) continue;

        if(graph[j][k] > graph[j][i] + graph[i][k]) {
          graph[j][k] = graph[j][i] + graph[i][k];
        }
      }
    }
  }

  // 접근성 최소인 두 곳 찾기
  let minA = 1;
  let minB = 1;
  let min = Number.MAX_SAFE_INTEGER;
  // a, b건물 고르고
  for(let a=1; a<=N; a++) {
    for(let b=1; b<=N; b++) {
      if(a === b) continue;

      // n에서 최소거리
      let sum = 0;
      for(let n=1; n<=N; n++) {
        if(n === a || n === b) continue;
        sum += Math.min(graph[n][a], graph[n][b]) * 2;
      }

      if(min > sum) {
        minA = a;
        minB = b;
        min = sum;
      }
    }
  }

  console.log(minA, minB, min);
}

main();