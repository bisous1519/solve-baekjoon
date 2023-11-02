// N 개의 영단어
// 가장 비슷한 두 단어를 구하자
//   - 두 단어의 접두사 길이로 측정
//     : 앞 부분에서 공통으로 나타나는 부분문자열
//     -> 두 단어 앞에서 M개 글자가 같을 때, M이 최대인 경우!
// => 풀이
//    입력받은 문자열을 정렬 (*****하면 안되는구나!)
//    이중 for문 돌리면서
//      -> (두 단어가 같은경우 패스) 맨 앞부터 공통 문자열 길이 세줌
//      -> 최대길이면 갱신

let N;
let words = [];
let wordsLen;

const count = (w1, w2) => {
  let len = Math.min(w1.length, w2.length);

  let cnt = 0;
  for(let i=0; i<len ;i++) {
    if(w1.charAt(i) !== w2.charAt(i)) break;
    cnt ++;
  }

  return cnt;
}

const main = () => {
  const input = require('fs').readFileSync(process.platform === 'linux' ? '/dev/stdin' : `${__dirnam}/input.txt`).toString().trim().split('\n');

  N = +input.shift();
  words = [...input];

  let max = -1;
  let words1 = '';
  let words2 = '';
  wordsLen = words.length;
  for(let i=0; i<wordsLen; i++) {
    for(let j=i+1; j<wordsLen; j++) {
      if(words[i] === words[j]) continue;
      
      const cnt = count(words[i], words[j]);

      if(cnt > max) {
        max = cnt;
        words1 = words[i];
        words2 = words[j];
      }
    }
  }
  
  console.log(words1);
  console.log(words2);
}

main();