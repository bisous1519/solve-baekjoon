// 축적된 탈피 에너지가 최대가 되도록 하기
// 왼쪽에서 오른쪽으로 한 칸 씩 이동하면서
//   - 누적해서 만족도를 얻음
//   - 누적 만족도가 K 이상이 되면 멈춤
//       -> 만족도가 0이 되고 다시 시작
//       -> K를 초과했다면 초과한 만큼 탈피 에너지 축적
// => 출력
//    축적된 탈피 에너지의 최댓값
// => 풀이
//    각 만족도를 선택 or 안선택 두가지씩 하면서 가기
//    n이 20이라 가능
//    지금까지의 최대 탈피 에너지 값이랑 비교해서 백트래킹 할 수 있는부분 없나?

let N, K;
let tree;
let maxEnergy = 0;

const eat = (nth, yummy, energy) => {
  if(nth === N) {
    maxEnergy = Math.max(maxEnergy, energy);
    return;
  }

  // 이번거 먹는다
  const ifYum = yummy + tree[nth];
  if(ifYum >= K) {
    eat(nth + 1, 0, energy + (ifYum - K));
  } else {
    eat(nth + 1, ifYum, energy);
  }
  
  // 안먹는다
  eat(nth + 1, 0, energy);
}

const main = () => {
  const input = require('fs').readFileSync('/dev/stdin').toString().trim().split('\n');

  [N, K] = input.shift().split(' ').map(el => +el);
  tree = input.shift().split(' ').map(el => +el);

  eat(0, 0, 0);

  console.log(maxEnergy);
}

main();