// => 입력
//    두 문자열 S와 T
// S를 T로 바꾸자
// 규칙
//   - 문자열의 뒤에 A를 추가
//   - 문자열의 뒤에 B를 추가하고 문자열을 뒤집음
// => 출력
//    S를 T로 바꿀 수 있으면 1, 없으면 0
// => 풀이
//    2^30 = 10억 넘음
//    그냥 구현인데 백트래킹.,?
//    T에서 B가 연달아 나온 횟수, A가 연달아 나온 횟수 구해놓고
//    그거에 따라 지금 S에 A를 붙일지 B를 붙일지 선택?

let S;
let T;
let sLen;
let fin = false;

const makeT = (t, tLen) => {
  if(fin) return;
  if(sLen === tLen) {
    if(S === t) {
      fin = true;
    }
    return;
  }

  // 문자열 맨 뒤에서 'A' 제거
  if(t.charAt(tLen - 1) === 'A') {
    makeT(t.slice(0, tLen - 1), tLen - 1);
  }
    
  // 문자열 뒤집고 맨 뒤에서 'B' 제거
  if(t.charAt(0) === 'B') {
    let temp = '';
    for(let i=1; i<tLen; i++) {
      temp = t.charAt(i) + temp;
    }
    makeT(temp, tLen - 1);
  }
}

const main = () => {
  const input = require('fs').readFileSync('/dev/stdin').toString().trim().split('\n');

  S = input.shift();
  T = input.shift();

  sLen = S.length;
  let tLen = T.length;
  makeT(T, tLen);

  if(fin) console.log(1);
  else console.log(0);
}

main();