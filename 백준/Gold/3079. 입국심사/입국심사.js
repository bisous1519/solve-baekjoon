// 입국심사대 N개, 친구들 M명
// 시간을 이분탐색!
//   - 이분탐색을 통해 mid 시간이면 M명이 모두 심사를 받을 수 있는지 확인

let N, M;
let time;

const main = () => {
  const input = require('fs').readFileSync('/dev/stdin').toString().trim().split('\n');

  [N, M] = input.shift().split(' ').map(BigInt);
  time = input.map(el => BigInt(el));

  let left = time.reduce((a, b) => a < b ? a : b);
  let right = time.reduce((a, b) => a > b ? a : b) * M;
  
  let min = right;
  while(left <= right) {
    let mid = BigInt((left + right) / 2n);

    let pass = 0n;
    for(let n=0; n<N; n++) {
      pass += BigInt(mid / time[n]);
      if(pass >= M) break;
    }

    // mid 시간 동안에 모두 pass 가능
    if(pass >= M) {
      right = mid - 1n;
      min = min < mid ? min : mid;
    }
    // 다 심사 못함
    else {
      left = mid + 1n;
    }
  }

  console.log(Number(min));
}

main();