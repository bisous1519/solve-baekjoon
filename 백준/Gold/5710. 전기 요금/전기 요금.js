// 전기요금 표가 있어
// A, B가 주어짐
//   - A : 나와 이웃의 총 사용량! 에 대한 요금
//   - B : 이웃 전기 요금과의 차이 (절댓값)
//   => A : (a사용량 + b사용량)의 요금 === totalWatt의 요금
//      B : |a사용량요금 - b사용량요금|
// => 출력
//    내가 내야하는 전기요금
// => 풀이
//    A를 통해 나와 이웃의 총 사용량 totalWatt를 구함
//    나는 무조건 이웃보다 전기 적게쓰고 금액도 적게 냄!
//      => 내가 쓴 사용량 범위 : 0와트 ~ totalWatt/2와트
//    0 ~ totalWatt/2 사이에서 이분탐색
//      => B를 만족하는 경우를 찾음!
//      -> midWatt가 내가 쓴 사용량인 경우,
//         -> midWatt에 대한 요금 (나), totalWatt-midWatt에 대한 요금 (이웃) 구함
//         -> 두 요금 차가 B를 만족하면 끝

let A, B;
let totalWatt;
let answer = '';

const calWatt = (cost) => {
  if(cost <= 200) return Math.floor(cost / 2);
  if(cost <= 29900) return Math.floor((cost - 200) / 3 + 100);
  if(cost <= 4979900) return Math.floor((cost - 29900) / 5 + 10000);
  return Math.floor((cost - 4979900) / 7 + 1000000);
}

const calCost = (watt) => {
  if(watt <= 100) return watt * 2;
  if(watt <= 10000) return 100 * 2 + (watt - 100) * 3;
  if(watt <= 1000000) return 100 * 2 + 9900 * 3 + (watt - 10000) * 5;
  return 100 * 2 + 9900 * 3 + 990000 * 5 + (watt - 1000000) * 7;
}

const main = () => {
  const input = require('fs').readFileSync('/dev/stdin').toString().trim().split('\n');

  input.forEach(row => {
    [A, B] = row.split(' ').map(el => +el);
    
    if(A !== 0 && B !== 0) {
      // A를 통해 총 사용량 totalWatt를 구함
      totalWatt = calWatt(A);

      // 0 ~ totalWatt/2 사이에서 이분탐색해서 B만족하는 값 구함
      let left = 0;
      let right = totalWatt/2;
      while(left <= right) {
        const midWatt = Math.floor((left + right) / 2); // 내 사용량

        const myCost = calCost(midWatt);
        const yourCost = calCost(totalWatt - midWatt);

        const diff = Math.abs(myCost - yourCost);

        if(diff === B) {
          answer += myCost + '\n';
          break;
          
        } else if(diff > B) {
          left = midWatt + 1;
          
        } else {
          right = midWatt - 1;
        }
      }
    }
  })

  console.log(answer);
}

main();