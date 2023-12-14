let str;
let maxLen = 0;

const main = () => {
  const input = require('fs').readFileSync('/dev/stdin').toString().trim();

  str = input;

  for(let i=0; i<str.length; i++) {
    let subStr = str.slice(i);
    let subLen = subStr.length;
    let max = 0;
    let pi = new Array(subLen).fill(0);

    for(let sufIdx=1, preIdx=0; sufIdx<subLen; sufIdx++) {
      // preIdx: 접두사 인덱스
      // sufIdx: 접미사 인덱스

      while(preIdx > 0 && subStr.charAt(sufIdx) !== subStr.charAt(preIdx)) {
        preIdx = pi[preIdx - 1];
      }

      if(subStr.charAt(sufIdx) === subStr.charAt(preIdx)) {
        max = Math.max(max, pi[sufIdx] = ++preIdx);
      }
    }

    maxLen = Math.max(maxLen, max);
  }

  console.log(maxLen);
}

main();