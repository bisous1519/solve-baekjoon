// 왼위, 오위, 왼아, 오아
//   -> 모두 0이거나 1이면 그 숫자 문자열로 리턴
//   -> 하나라도 다른 숫자면 ()로 묶어서 문자열로 리턴

let N;
let map;
const dr = [0, 0, 1, 1]; // 나 오 아 아오
const dc = [0, 1, 0, 1];

const pressure = (sR, sC, n) => {
  const half = Math.floor(n / 2);
  
  if(half > 0) {
    let str = '';
    str += pressure(sR, sC, half);
    str += pressure(sR, sC + half, half);
    str += pressure(sR + half, sC, half);
    str += pressure(sR + half, sC + half, half);

    let sum = 0;
    for(let i=0; i<4; i++) {
      sum += +(str.charAt(i));
    }

    if(sum === 0 || sum === 4) {
      // 모두 0 이거나 1
      return str.charAt(0);
    }
    else {
      // 예시. (0011)
      return `(${str})`;
    }
  }

  else {
    return map[sR][sC];
  }
}

const main = () => {
  const input = require('fs').readFileSync('/dev/stdin').toString().trim().split('\n');

  N = +input.shift();
  map = input.map(row => row.split(''));

  console.log(pressure(0, 0, N));
}

main();