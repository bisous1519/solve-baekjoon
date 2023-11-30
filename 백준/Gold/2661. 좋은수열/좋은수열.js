// N자리수의 수열을 만드는 과정중에
// 숫자 하나씩 늘어날 때마다 좋은 수 맞는지 확인해서 백트래킹

let N;
let fin = false;

const isGood = (str) => {
  let left = str.length - 2;
  let right;
  let len = 1;
  while(left >= 0) {
    right = left + len;

    if(str.substr(left, len) === str.substr(right, len)) return false;

    left -= 2;
    len ++;
  }

  return true;
}

const makeStr = (len, str) => {
  if(fin) return;
  
  if(len === N) {
    console.log(str);
    fin = true;
    return;
  }

  for(let i=1; i<=3; i++) {
    const newStr = str + i;
    if(isGood(newStr)) {
      makeStr(len + 1, newStr);
    }
  }
}

const main = () => {
  const input = require('fs').readFileSync('/dev/stdin').toString().trim();

  N = +input;

  // 수열 만들기
  makeStr(0, '');
}

main();