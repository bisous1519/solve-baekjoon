let Str;
let Boom;

const main = () => {
  const input = require('fs')
    .readFileSync(
      process.platform === 'linux' ? '/dev/stdin' : `${__dirname}/input.txt`
    )
    .toString()
    .trim()
    .split('\n');
  Str = input[0].split('');
  Boom = input[1].split('');
  //입력

  // 주어진 문자열을 스택에 하나씩 넣으면서 boom과 비교
  let stack = [];
  const lastEl = Boom[Boom.length - 1];
  const strLen = Str.length;
  const boomLen = Boom.length;
  for (let strIdx = 0; strIdx < strLen; strIdx++) {
    const cur = Str[strIdx];
    stack.push(cur);

    // 방금 stack에 새로 넣은 문자가 boom의 마지막 문자랑 같음
    if (cur === lastEl && stack.length >= Boom.length) {
      let canBoom = true;
      for (
        let boomIdx = Boom.length - 1, stackIdx = stack.length - 1;
        boomIdx >= 0;
        boomIdx--, stackIdx--
      ) {
        if (Boom[boomIdx] !== stack[stackIdx]) {
          canBoom = false;
          break;
        }
      }

      // 폭발 문자열 찾음!
      if (canBoom) {
        // stack = stack.slice(0, Boom.length * -1);
        for (let i = 0; i < boomLen; i++) {
          stack.pop();
        }
      }
    }
  }

  // 출력
  if (stack.length > 0) {
    console.log(stack.join(''));
  } else {
    console.log('FRULA');
  }
};

main();